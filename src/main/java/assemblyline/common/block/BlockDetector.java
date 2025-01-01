package assemblyline.common.block;

import assemblyline.common.tile.belt.TileDetector;
import com.mojang.serialization.MapCodec;
import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.block.GenericEntityBlockWaterloggable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class BlockDetector extends GenericEntityBlockWaterloggable {

	public BlockDetector() {
		super(Blocks.IRON_BLOCK.properties().strength(3.5F).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion());
		registerDefaultState(stateDefinition.any().setValue(ElectrodynamicsBlockStates.FACING, Direction.NORTH));
	}

	@Override
	public boolean isSignalSource(BlockState state) {
		return true;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(ElectrodynamicsBlockStates.FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ElectrodynamicsBlockStates.FACING);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileDetector(pos, state);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return null;
	}
}
