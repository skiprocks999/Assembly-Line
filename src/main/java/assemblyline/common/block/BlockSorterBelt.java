package assemblyline.common.block;

import java.util.Arrays;
import java.util.List;

import assemblyline.common.tile.belt.TileSorterBelt;
import com.mojang.serialization.MapCodec;
import electrodynamics.common.block.states.ElectrodynamicsBlockStates;
import electrodynamics.prefab.block.GenericEntityBlockWaterloggable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockSorterBelt extends GenericEntityBlockWaterloggable {
	private static final VoxelShape SHAPE = Shapes.or(Shapes.box(0, 14.0 / 16.0, 0, 1, 1, 1), Shapes.box(0, 0, 0, 1, 5.0 / 16.0, 1));

	public BlockSorterBelt() {
		super(Blocks.IRON_BLOCK.properties().strength(3.5F).sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion());
		registerDefaultState(stateDefinition.any().setValue(ElectrodynamicsBlockStates.FACING, Direction.NORTH).setValue(ElectrodynamicsBlockStates.LIT, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return worldIn instanceof Level lvl && lvl.isClientSide ? Shapes.block() : SHAPE;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(ElectrodynamicsBlockStates.FACING, context.getHorizontalDirection().getOpposite()).setValue(ElectrodynamicsBlockStates.LIT, false);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(ElectrodynamicsBlockStates.FACING);
		builder.add(ElectrodynamicsBlockStates.LIT);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TileSorterBelt(pos, state);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		return Arrays.asList(new ItemStack(this));
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return null;
	}
}
