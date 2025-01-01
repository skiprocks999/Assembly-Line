package assemblyline.datagen.client;

import assemblyline.References;
import assemblyline.common.block.subtype.SubtypeAssemblyMachine;
import assemblyline.registers.AssemblyLineBlocks;
import electrodynamics.datagen.client.ElectrodynamicsBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class AssemblyLineBlockStateProvider extends ElectrodynamicsBlockStateProvider {

	public AssemblyLineBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, exFileHelper, References.ID);
	}

	@Override
	protected void registerStatesAndModels() {

		simpleColumnBlock(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.crate), blockLoc("crate"), blockLoc("cratetop"), true);
		simpleColumnBlock(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.cratemedium), blockLoc("cratemedium"), blockLoc("cratetop"), true);
		simpleColumnBlock(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.cratelarge), blockLoc("cratelarge"), blockLoc("cratetop"), true);

		horrRotatedBlock(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.autocrafter), existingBlock(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.autocrafter)), true);
		horrRotatedBlock(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.blockbreaker), existingBlock(blockLoc("blockbreakerbase")), 270, 90, false);
		horrRotatedBlock(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.blockplacer), existingBlock(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.blockplacer)), true);
		horrRotatedBlock(AssemblyLineBlocks.BLOCK_CONVEYORBELT, existingBlock(blockLoc("conveyorbelt")), true);
		horrRotatedBlock(AssemblyLineBlocks.BLOCK_DETECTOR, existingBlock(AssemblyLineBlocks.BLOCK_DETECTOR), true);
		horrRotatedBlock(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.farmer), existingBlock(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.farmer)), true);
		horrRotatedBlock(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.mobgrinder), existingBlock(blockLoc("mobgrinderbase")), 270, 90, false);
		horrRotatedBlock(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.rancher), existingBlock(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.rancher)), 270, 90, true);
		horrRotatedLitBlock(AssemblyLineBlocks.BLOCK_SORTERBELT, existingBlock(AssemblyLineBlocks.BLOCK_SORTERBELT), existingBlock(blockLoc("sorterbeltrunning")), true);

	}

}
