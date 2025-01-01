package assemblyline.datagen.client;

import assemblyline.References;
import assemblyline.common.block.subtype.SubtypeAssemblyMachine;
import assemblyline.registers.AssemblyLineBlocks;
import electrodynamics.datagen.client.ElectrodynamicsItemModelsProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class AssemblyLineItemModelsProvider extends ElectrodynamicsItemModelsProvider {

	public AssemblyLineItemModelsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, existingFileHelper, References.ID);
	}

	@Override
	protected void registerModels() {

		simpleBlockItem(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.blockbreaker), existingBlock(blockLoc("blockbreaker")));
		simpleBlockItem(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.mobgrinder), existingBlock(blockLoc("mobgrinder")));

	}

}
