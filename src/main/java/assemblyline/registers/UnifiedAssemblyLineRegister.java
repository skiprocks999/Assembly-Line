package assemblyline.registers;

import assemblyline.common.block.subtype.SubtypeAssemblyMachine;
import assemblyline.prefab.utils.AssemblyTextUtils;
import electrodynamics.common.blockitem.types.BlockItemDescriptable;
import electrodynamics.prefab.utilities.ElectroTextUtils;
import net.neoforged.bus.api.IEventBus;

public class UnifiedAssemblyLineRegister {

	static {

		// MACHINES
		BlockItemDescriptable.addDescription(AssemblyLineBlocks.BLOCK_CONVEYORBELT, ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(AssemblyLineBlocks.BLOCK_SORTERBELT, ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getHolder(SubtypeAssemblyMachine.autocrafter), ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getHolder(SubtypeAssemblyMachine.blockplacer), ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getHolder(SubtypeAssemblyMachine.blockbreaker), ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getHolder(SubtypeAssemblyMachine.rancher), ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getHolder(SubtypeAssemblyMachine.mobgrinder), ElectroTextUtils.voltageTooltip(120));
		BlockItemDescriptable.addDescription(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getHolder(SubtypeAssemblyMachine.farmer), ElectroTextUtils.voltageTooltip(120));

		// Misc
		BlockItemDescriptable.addDescription(AssemblyLineBlocks.BLOCK_DETECTOR, AssemblyTextUtils.tooltip("detector"));
		BlockItemDescriptable.addDescription(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getHolder(SubtypeAssemblyMachine.crate), AssemblyTextUtils.tooltip("crate"));
		BlockItemDescriptable.addDescription(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getHolder(SubtypeAssemblyMachine.cratemedium), AssemblyTextUtils.tooltip("cratemedium"));
		BlockItemDescriptable.addDescription(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getHolder(SubtypeAssemblyMachine.cratelarge), AssemblyTextUtils.tooltip("cratelarge"));
	}

	public static void register(IEventBus bus) {
		AssemblyLineAttachmentTypes.ATTACHMENT_TYPES.register(bus);
		AssemblyLineBlocks.BLOCKS.register(bus);
		AssemblyLineItems.ITEMS.register(bus);
		AssemblyLineTiles.BLOCK_ENTITY_TYPES.register(bus);
		AssemblyLineMenuTypes.MENU_TYPES.register(bus);
		AssemblyLineCreativeTabs.CREATIVE_TABS.register(bus);
	}
}
