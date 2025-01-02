package assemblyline.common.tile.util;

import assemblyline.client.render.event.levelstage.HandlerHarvesterLines;
import electrodynamics.prefab.properties.Property;
import electrodynamics.prefab.properties.PropertyTypes;
import electrodynamics.prefab.tile.GenericTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

    public AABB getAABB(int width, int length, int height, boolean isFlipped) {

        Direction facing = getFacing();

        if(isFlipped) {
            facing = facing.getOpposite();
        }

        Direction counterClockwise = facing.getCounterClockWise();
        Direction clockwise = facing.getClockWise();

        BlockPos pos = getBlockPos().relative(facing);

        BlockPos start = pos.relative(facing, length - 1).relative(counterClockwise, width / 2).relative(Direction.UP, height - 1);

        BlockPos end = pos.relative(clockwise, width / 2);

        return AABB.encapsulatingFullBlocks(start, end);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (getLevel().isClientSide) {
            HandlerHarvesterLines.removeLines(getBlockPos());
        }
    }

}
