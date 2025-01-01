package assemblyline.common.tile.belt;

import java.util.ArrayList;

import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.HolderLookup;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import org.joml.Vector3f;

import assemblyline.common.settings.Constants;
import assemblyline.registers.AssemblyLineTiles;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentInventory.InventoryBuilder;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.object.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileConveyorBelt extends GenericTile {

    public static final int INVENTORY_INDEX = 0;

    public final Property<Integer> currentSpread = property(new Property<>(PropertyTypes.INTEGER, "currentSpread", 0));
    public final Property<Boolean> running = property(new Property<>(PropertyTypes.BOOLEAN, "running", false));
    // public final Property<Boolean> hasPlaceToDrop = property(new Property<>(PropertyTypes.Boolean, "hasplacetodrop", true));
    public final Property<Boolean> isQueueReady = property(new Property<>(PropertyTypes.BOOLEAN, "isQueueReady", false));
    public final Property<Boolean> waiting = property(new Property<>(PropertyTypes.BOOLEAN, "waiting", false));
    public final Property<Location> conveyorObject = property(new Property<>(PropertyTypes.LOCATION, "conveyorObject", new Location(0, 0, 0)));
    public final Property<Integer> conveyorType = property(new Property<>(PropertyTypes.INTEGER, "conveyorType", ConveyorType.Horizontal.ordinal()));

    public int wait = 0;
    public ArrayList<TileConveyorBelt> inQueue = new ArrayList<>();

    public final Property<Boolean> isPusher = property(new Property<>(PropertyTypes.BOOLEAN, "pusher", false));
    public final Property<Boolean> isPuller = property(new Property<>(PropertyTypes.BOOLEAN, "puller", false));

    public TileConveyorBelt(BlockPos worldPosition, BlockState blockState) {
        super(AssemblyLineTiles.TILE_BELT.get(), worldPosition, blockState);
        addComponent(new ComponentTickable(this).tickCommon(this::tickCommon));
        addComponent(new ComponentPacketHandler(this));
        addComponent(new ComponentInventory(this, InventoryBuilder.newInv().forceSize(1)));
        addComponent(new ComponentElectrodynamic(this, false, true).setInputDirections(BlockEntityUtils.MachineDirection.BOTTOM, BlockEntityUtils.MachineDirection.LEFT, BlockEntityUtils.MachineDirection.RIGHT).maxJoules(Constants.CONVEYORBELT_USAGE * 100));
    }

    @Override
    public void onEntityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof ItemEntity item && entity.tickCount > 5) {
            item.setItem(addItemOnBelt(item.getItem()));
        }
    }

    @Override
    public void onBlockDestroyed() {
        Containers.dropContents(level, getBlockPos(), (ComponentInventory) getComponent(IComponentType.Inventory));
    }

    public ComponentInventory getInventory() {
        return getComponent(IComponentType.Inventory);
    }

    public ItemStack getStackOnBelt() {
        return getInventory().getItem(INVENTORY_INDEX);
    }

    public void setInvToEmpty() {
        setItemOnBelt(ItemStack.EMPTY);
    }

    public void setItemOnBelt(ItemStack stack) {
        getInventory().setItem(INVENTORY_INDEX, stack);
    }

    public ItemStack addItemOnBelt(ItemStack add) {
        ItemStack onBelt = getStackOnBelt();
        if (onBelt.isEmpty()) {
            ConveyorType type = ConveyorType.values()[conveyorType.get()];
            conveyorObject.set(new Location(0.5f + worldPosition.getX(), worldPosition.getY() + (type == ConveyorType.SlopedDown ? -4.0f / 16.0f : type == ConveyorType.SlopedUp ? 8.0f / 16.0f : 0), 0.5f + worldPosition.getZ()));
        }
        if (!add.isEmpty()) {
            ComponentInventory inventory = getComponent(IComponentType.Inventory);
            ItemStack returner = new InvWrapper(inventory).insertItem(0, add, false);
            if (returner.getCount() != add.getCount()) {
                return returner;
            }
        }
        return add;
    }

    public void addItemOnBelt(ItemStack add, Location object) {
        if (!add.isEmpty()) {
            ComponentInventory inventory = getInventory();
            new InvWrapper(inventory).insertItem(0, add, false);
            conveyorObject.set(new Location(object));
            if (ConveyorType.values()[conveyorType.get()] == ConveyorType.Vertical) {
                Vector3f vec = getDirectionAsVector();
                conveyorObject.set(conveyorObject.get().add(-vec.x(), -vec.y(), -vec.z()));
            }
        }
    }

    protected void tickCommon(ComponentTickable tickable) {
        ComponentElectrodynamic electro = getComponent(IComponentType.Electrodynamic);
        ItemStack stackOnBelt = getStackOnBelt();
        isQueueReady.set(stackOnBelt.isEmpty());
        running.set(currentSpread.get() > 0 && isQueueReady.get());
        BlockEntity nextBlockEntity = getNextEntity();
        Direction direction = getFacing();
        if (nextBlockEntity != null && !(nextBlockEntity instanceof TileConveyorBelt)) {
            isPusher.set(level.getCapability(Capabilities.ItemHandler.BLOCK, nextBlockEntity.getBlockPos(), nextBlockEntity.getBlockState(), nextBlockEntity, direction) != null);
            //isPusher.set(nextBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, direction).isPresent());
        } else {
            isPusher.set(false);
        }
        if (currentSpread.get() > 0) {
            attemptMove();
        }
        BlockEntity lastBlockEntity = level.getBlockEntity(worldPosition.offset(direction.getNormal()));
        if (lastBlockEntity != null && !(lastBlockEntity instanceof TileConveyorBelt)) {
            //LazyOptional<IItemHandler> handlerOptional = lastBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, direction);
            isPuller.set(level.getCapability(Capabilities.ItemHandler.BLOCK, lastBlockEntity.getBlockPos(), lastBlockEntity.getBlockState(), lastBlockEntity, direction) != null);
        } else {
            isPuller.set(false);
        }
        if (isQueueReady.get()) {
            if (!inQueue.isEmpty()) {
                while (true) {
                    TileConveyorBelt queue = inQueue.get(0);
                    if (!queue.isRemoved() && queue.waiting.get()) {
                        break;
                    }
                    inQueue.remove(0);
                    if (inQueue.isEmpty()) {
                        break;
                    }
                }
            } else if (lastBlockEntity != null && isPuller.get()) {
                IItemHandler handler = level.getCapability(Capabilities.ItemHandler.BLOCK, lastBlockEntity.getBlockPos(), lastBlockEntity.getBlockState(), lastBlockEntity, direction.getOpposite());
                //LazyOptional<IItemHandler> cap = lastBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, direction.getOpposite());
                if (handler != null) {
                    for (int slot = 0; slot < handler.getSlots(); slot++) {
                        ItemStack returned = handler.extractItem(slot, 64, false);
                        if (!returned.isEmpty()) {
                            addItemOnBelt(returned);
                            break;
                        }
                    }
                }
            }
        }
        if (!level.isClientSide) {
            checkForSpread();
            if (currentSpread.get() == 0 || currentSpread.get() == 16) {
                if (electro.getJoulesStored() < Constants.CONVEYORBELT_USAGE) {
                    currentSpread.set(0);
                } else {
                    electro.joules(electro.getJoulesStored() - Constants.CONVEYORBELT_USAGE);
                    currentSpread.set(16);
                }
            }
        }
    }

    public Vector3f getObjectLocal() {
        return new Vector3f((float) (conveyorObject.get().x() - worldPosition.getX()), (float) (conveyorObject.get().y() - worldPosition.getY()), (float) (conveyorObject.get().z() - worldPosition.getZ()));
    }

    public Vector3f getDirectionAsVector() {
        Direction direction = getFacing().getOpposite();
        return new Vector3f(direction.getStepX(), direction.getStepY(), direction.getStepZ());
    }

    public boolean shouldTransfer() {
        TileConveyorBelt belt = getNextConveyor();
        BlockPos pos = conveyorObject.get().toBlockPos();
        Vector3f local = getObjectLocal();
        Vector3f direction = getDirectionAsVector();

        float coordComponent = local.dot(direction);
        ConveyorType type = ConveyorType.values()[conveyorType.get()];
        if (type != ConveyorType.Horizontal) {
            return type == ConveyorType.SlopedDown ? conveyorObject.get().y() <= worldPosition.getY() - 1 : conveyorObject.get().y() >= worldPosition.getY() + 1;
        }

        float value = belt != null && (belt.inQueue.isEmpty() || belt.inQueue.get(0) == this) && belt.isQueueReady.get() ? ConveyorType.values()[belt.conveyorType.get()] == ConveyorType.SlopedUp || ConveyorType.values()[conveyorType.get()] == ConveyorType.Vertical ? 1 : 1.25f : 1;

        if (direction.x() + direction.y() + direction.z() > 0) {
            return !pos.equals(worldPosition) && coordComponent >= value;
        }
        return !pos.equals(worldPosition) && coordComponent >= value - 1;
    }

    public void attemptMove() {
        Vector3f move = getDirectionAsVector();
        ItemStack stackOnBelt = getStackOnBelt();
        if (!stackOnBelt.isEmpty()) {
            boolean shouldTransfer = shouldTransfer();
            BlockEntity nextBlockEntity = getNextEntity();
            if (nextBlockEntity instanceof TileConveyorBelt belt) {
                if (shouldTransfer) {
                    if (!belt.inQueue.contains(this)) {
                        belt.inQueue.add(this);
                    }
                    if (belt.inQueue.get(0) == this && belt.isQueueReady.get()) {
                        waiting.set(false);
                        belt.inQueue.remove(0);
                        belt.addItemOnBelt(getStackOnBelt(), conveyorObject.get());
                        setInvToEmpty();
                    } else {
                        waiting.set(true);
                    }
                }
            } else if (nextBlockEntity != null) {
                if (shouldTransfer) {
                    Direction direction = getFacing();
                    IItemHandler handler = level.getCapability(Capabilities.ItemHandler.BLOCK, nextBlockEntity.getBlockPos(), nextBlockEntity.getBlockState(), nextBlockEntity, direction);
                    //LazyOptional<IItemHandler> handlerOptional = nextBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER, direction);
                    if (handler != null) {
                        if (wait == 0) {
                            if (putItemsIntoInventory(handler) == 0) {
                                wait = 20;
                            } else {

                            }
                        } else {
                            wait--;
                        }
                    } else {
                        dropItem(stackOnBelt, move);
                        setInvToEmpty();
                    }
                }
            } else {
                dropItem(stackOnBelt, move);
                setInvToEmpty();
            }
            if (!shouldTransfer) {
                move.mul(1 / 16.0f);
                conveyorObject.set(conveyorObject.get().add(move.x(), move.y(), move.z()));
                ConveyorType type = ConveyorType.values()[conveyorType.get()];
                if (type != ConveyorType.Horizontal) {
                    conveyorObject.set(conveyorObject.get().add(0, 1 / 16.0f * (type == ConveyorType.SlopedDown ? -1 : 1), 0));
                }
            }
        }
        isQueueReady.set(stackOnBelt.isEmpty());
        running.set(currentSpread.get() > 0 && (!waiting.get() || isQueueReady.get()));
    }

    public void dropItem(ItemStack stackOnBelt, Vector3f move) {
        ItemEntity entity = new ItemEntity(level, worldPosition.getX() + 0.5 + move.x() / 2.0f, worldPosition.getY() + 0.4, worldPosition.getZ() + 0.5 + move.z() / 2.0f, stackOnBelt);
        entity.setDeltaMovement(move.x() / 12.0, 1.5 / 16.0, move.z() / 12.0);
        entity.setPickUpDelay(20);
        level.addFreshEntity(entity);
    }

    private int putItemsIntoInventory(IItemHandler handler) {

        int amtTaken = 0;

        ItemStack conveyerStack = getStackOnBelt();

        ItemStack remainder;

        for (int targetIndex = 0; targetIndex < handler.getSlots(); targetIndex++) {

            remainder = handler.insertItem(targetIndex, conveyerStack, false);

            int taken = conveyerStack.getCount() - remainder.getCount();

            if (taken <= 0) {

                continue;

            }

            amtTaken += taken;

            conveyerStack = conveyerStack.copy();

            conveyerStack.shrink(taken);

            if (conveyerStack.isEmpty()) {
                break;
            }

        }

        conveyerStack.shrink(amtTaken);

        setItemOnBelt(conveyerStack);

        return amtTaken;
    }

    public BlockPos getNextPos() {
        Direction direction = getFacing().getOpposite();
        return switch (ConveyorType.values()[conveyorType.get()]) {
            case Horizontal -> worldPosition.relative(direction);
            case SlopedDown -> worldPosition.relative(direction).below();
            case SlopedUp -> worldPosition.relative(direction).above();
            case Vertical -> level.getBlockEntity(worldPosition.relative(Direction.UP)) instanceof TileConveyorBelt belt && ConveyorType.values()[belt.conveyorType.get()] == ConveyorType.Vertical ? worldPosition.relative(Direction.UP) : worldPosition.relative(direction).above();
            default -> null;
        };
    }

    protected BlockEntity getNextEntity() {
        BlockEntity nextBlockEntity = null;
        BlockPos nextBlockPos = getNextPos();
        if (nextBlockPos != null) {
            nextBlockEntity = level.getBlockEntity(nextBlockPos);
        }
        return nextBlockEntity;
    }

    protected TileConveyorBelt getNextConveyor() {
        return getNextEntity() instanceof TileConveyorBelt belt ? belt : null;
    }

    public void checkForSpread() {
        int lastMax = currentSpread.get();
        int max = 0;
        for (BlockPos po : TileConveyorBelt.offsets) {
            BlockEntity at = level.getBlockEntity(worldPosition.offset(po));
            if (at instanceof TileConveyorBelt belt) {
                int their = belt.currentSpread.get();
                if (their - 1 > max) {
                    max = their - 1;
                }
            } else if (at instanceof TileSorterBelt sbelt) {
                int their = sbelt.currentSpread;
                if (their - 1 > max) {
                    max = their - 1;
                }
            }
        }
        currentSpread.set(max);
        if (lastMax > currentSpread.get()) {
            currentSpread.set(0);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putInt("conveyorwait", wait);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        wait = compound.getInt("conveyorwait");
    }

    public static ArrayList<BlockPos> offsets = new ArrayList<>();

    static {
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.EAST));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.WEST));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.NORTH));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.SOUTH));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.EAST));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.WEST));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.NORTH));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.SOUTH));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.DOWN).relative(Direction.EAST));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.DOWN).relative(Direction.WEST));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.DOWN).relative(Direction.NORTH));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.DOWN).relative(Direction.SOUTH));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.UP).relative(Direction.EAST));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.UP).relative(Direction.WEST));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.UP).relative(Direction.NORTH));
        offsets.add(new BlockPos(0, 0, 0).relative(Direction.UP).relative(Direction.SOUTH));
    }

    public enum ConveyorType {
        Horizontal,
        SlopedUp,
        SlopedDown,
        Vertical;
    }
}
