package assemblyline.common.block.subtype;

import assemblyline.common.block.AssemblyLineVoxelShapes;
import assemblyline.common.tile.*;
import electrodynamics.api.ISubtype;
import electrodynamics.api.multiblock.subnodebased.parent.IMultiblockParentBlock;
import electrodynamics.api.tile.IMachine;
import electrodynamics.api.tile.MachineProperties;
import electrodynamics.common.block.voxelshapes.VoxelShapeProvider;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public enum SubtypeAssemblyMachine implements ISubtype, IMachine {

    crate(true, TileCrate::new),
    cratemedium(true, TileCrate::new),
    cratelarge(true, TileCrate::new),
    autocrafter(true, TileAutocrafter::new, MachineProperties.builder().setShapeProvider(AssemblyLineVoxelShapes.AUTOCRAFTER)),
    blockbreaker(true, TileBlockBreaker::new, MachineProperties.builder().setShapeProvider(AssemblyLineVoxelShapes.BLOCKBREAKER)),
    blockplacer(true, TileBlockPlacer::new, MachineProperties.builder().setShapeProvider(AssemblyLineVoxelShapes.BLOCKPLACER)),
    rancher(true, TileRancher::new, MachineProperties.builder().setShapeProvider(AssemblyLineVoxelShapes.ENERGIZEDRANCHER)),
    mobgrinder(true, TileMobGrinder::new, MachineProperties.builder().setShapeProvider(AssemblyLineVoxelShapes.MOBGRINDER)),
    farmer(true, TileFarmer::new, MachineProperties.builder().setShapeProvider(AssemblyLineVoxelShapes.FARMER));

    private final BlockEntityType.BlockEntitySupplier<BlockEntity> blockEntitySupplier;
    private final boolean showInItemGroup;
    private final MachineProperties properties;

    private SubtypeAssemblyMachine(boolean showInItemGroup, BlockEntityType.BlockEntitySupplier blockEntitySupplier) {
        this(showInItemGroup, blockEntitySupplier, MachineProperties.DEFAULT);
    }

    private SubtypeAssemblyMachine(boolean showInItemGroup, BlockEntityType.BlockEntitySupplier blockEntitySupplier, MachineProperties properties) {
        this.showInItemGroup = showInItemGroup;
        this.blockEntitySupplier = blockEntitySupplier;
        this.properties = properties;
    }

    @Override
    public BlockEntityType.BlockEntitySupplier<BlockEntity> getBlockEntitySupplier() {
        return this.blockEntitySupplier;
    }

    @Override
    public int getLitBrightness() {
        return this.properties.litBrightness;
    }

    @Override
    public RenderShape getRenderShape() {
        return this.properties.renderShape;
    }

    @Override
    public boolean isMultiblock() {
        return this.properties.isMultiblock;
    }

    @Override
    public boolean propegatesLightDown() {
        return this.properties.propegatesLightDown;
    }

    @Override
    public String tag() {
        return this.name();
    }

    @Override
    public String forgeTag() {
        return this.tag();
    }

    @Override
    public boolean isItem() {
        return false;
    }

    @Override
    public boolean isPlayerStorable() {
        return false;
    }

    @Override
    public IMultiblockParentBlock.SubnodeWrapper getSubnodes() {
        return this.properties.wrapper;
    }

    @Override
    public VoxelShapeProvider getVoxelShapeProvider() {
        return this.properties.provider;
    }

    @Override
    public boolean usesLit() {
        return properties.usesLit;
    }

    public boolean showInItemGroup() {
        return this.showInItemGroup;
    }
}
