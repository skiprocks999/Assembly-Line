package assemblyline.common.tile.util;

import assemblyline.client.render.event.levelstage.HandlerHarvesterLines;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public abstract class TileOutlineArea extends GenericTile {

    public static final int CHECK_HEIGHT = 5;
    protected static final int DEFAULT_CHECK_WIDTH = 1;
    protected static final int DEFAULT_CHECK_LENGTH = 1;
    protected static final int DEFAULT_CHECK_HEIGHT = 5;
    protected static final int MAX_CHECK_WIDTH = 25;
    protected static final int MAX_CHECK_LENGTH = 25;
    public Property<Integer> width = property(new Property<>(PropertyTypes.INTEGER, "width", DEFAULT_CHECK_WIDTH));
    public Property<Integer> length = property(new Property<>(PropertyTypes.INTEGER, "length", DEFAULT_CHECK_LENGTH));
    public Property<Integer> height = property(new Property<>(PropertyTypes.INTEGER, "height", DEFAULT_CHECK_HEIGHT));
    protected AABB checkArea;

    protected TileOutlineArea(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public AABB getAABB(int width, int length, int height, boolean isFlipped, boolean isClient, TileOutlineArea grinder) {
        BlockPos machinePos = grinder.getBlockPos();
        BlockPos blockInFront = machinePos.relative(isFlipped ? getFacing().getOpposite() : getFacing());
        BlockPos startPos;
        BlockPos endPos;
        int deltaX = blockInFront.getX() - machinePos.getX();
        int deltaZ = blockInFront.getZ() - machinePos.getZ();
        int xShift;
        int zShift;
        int yShift = height - 1;
        if (isFlipped) {
            // voltage south
            if (deltaX == 0) {
                xShift = deltaZ * (width + 2) / 2;
                zShift = deltaZ * length;
                startPos = new BlockPos(blockInFront.getX() + (isClient && deltaZ < 0 ? xShift + 1 : xShift), blockInFront.getY() + yShift, blockInFront.getZ() + (isClient && deltaZ < 0 ? zShift + 1 : zShift));
                endPos = new BlockPos(blockInFront.getX() - (isClient && deltaZ > 0 ? xShift - 1 : xShift), blockInFront.getY(), blockInFront.getZ() - (isClient && deltaZ > 0 ? deltaZ - 1 : deltaZ));
                return AABB.encapsulatingFullBlocks(startPos, endPos);
            }
            if (deltaZ == 0) {
                xShift = deltaX * width;
                zShift = deltaX * (length + 2) / 2;
                startPos = new BlockPos(blockInFront.getX() + (isClient && deltaX < 0 ? xShift + 1 : xShift), blockInFront.getY() + yShift, blockInFront.getZ() + (isClient && deltaX < 0 ? zShift + 1 : zShift));
                endPos = new BlockPos(blockInFront.getX() - (isClient && deltaX > 0 ? 0 : deltaX), blockInFront.getY(), blockInFront.getZ() - (isClient && deltaX > 0 ? zShift - 1 : zShift));
                return AABB.encapsulatingFullBlocks(startPos, endPos);
            }
        } else // voltage north
            // this should work
            if (deltaX == 0) {
                xShift = isClient ? deltaZ * width / 2 : deltaZ * (width + 2) / 2;
                zShift = deltaZ * length;
                startPos = new BlockPos(blockInFront.getX() + xShift + (isClient ? deltaZ : 0), blockInFront.getY() + yShift, blockInFront.getZ() + zShift);
                endPos = new BlockPos(blockInFront.getX() - xShift, blockInFront.getY(), blockInFront.getZ() - (isClient ? 0 : deltaZ));
                return AABB.encapsulatingFullBlocks(startPos, endPos);
            } else if (deltaZ == 0) {
                xShift = deltaX * width;
                zShift = isClient ? deltaX * length / 2 : deltaX * (length + 2) / 2;
                startPos = new BlockPos(blockInFront.getX() + xShift, blockInFront.getY() + yShift, blockInFront.getZ() + zShift + (isClient ? deltaX : 0));
                endPos = new BlockPos(blockInFront.getX() - (isClient ? 0 : deltaX), blockInFront.getY(), blockInFront.getZ() - zShift);
                return AABB.encapsulatingFullBlocks(startPos, endPos);
            }
        return new AABB(0, 0, 0, 0, 0, 0);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (getLevel().isClientSide) {
            HandlerHarvesterLines.removeLines(getBlockPos());
        }
    }

}
