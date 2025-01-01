package assemblyline.datagen.server.recipe.vanilla;

import assemblyline.References;
import assemblyline.common.block.subtype.SubtypeAssemblyMachine;
import assemblyline.registers.AssemblyLineItems;
import electrodynamics.common.block.subtype.SubtypeWire;
import electrodynamics.common.tags.ElectrodynamicsTags;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import electrodynamics.datagen.utils.recipe.ShapedCraftingRecipeBuilder;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

public class AssemblyLineCraftingTableRecipes extends AbstractRecipeGenerator {

	@Override
	public void addRecipes(RecipeOutput output) {

		ShapedCraftingRecipeBuilder.start(AssemblyLineItems.ITEMS_ASSEMBLYMACHINE.getValue(SubtypeAssemblyMachine.crate), 1)
				//
				.addPattern("IBI")
				//
				.addPattern("ICI")
				//
				.addPattern("IBI")
				//
				.addKey('I', Tags.Items.INGOTS_IRON)
				//
				.addKey('B', Items.IRON_BARS)
				//
				.addKey('C', Tags.Items.CHESTS)
				//
				.complete(References.ID, "crate_small", output);

		ShapedCraftingRecipeBuilder.start(AssemblyLineItems.ITEMS_ASSEMBLYMACHINE.getValue(SubtypeAssemblyMachine.cratemedium), 1)
				//
				.addPattern("SCS")
				//
				.addKey('S', AssemblyLineItems.ITEMS_ASSEMBLYMACHINE.getValue(SubtypeAssemblyMachine.crate))
				//
				.addKey('C', Tags.Items.CHESTS)
				//
				.complete(References.ID, "crate_medium", output);

		ShapedCraftingRecipeBuilder.start(AssemblyLineItems.ITEMS_ASSEMBLYMACHINE.getValue(SubtypeAssemblyMachine.cratelarge), 1)
				//
				.addPattern("MCM")
				//
				.addKey('M', AssemblyLineItems.ITEMS_ASSEMBLYMACHINE.getValue(SubtypeAssemblyMachine.cratemedium))
				//
				.addKey('C', Tags.Items.CHESTS)
				//
				.complete(References.ID, "crate_large", output);

		addMachines(output);

	}

	public void addMachines(RecipeOutput output) {

		ShapedCraftingRecipeBuilder.start(AssemblyLineItems.ITEMS_ASSEMBLYMACHINE.getValue(SubtypeAssemblyMachine.autocrafter), 1)
				//
				.addPattern("GBG")
				//
				.addPattern("CTC")
				//
				.addPattern("PWP")
				//
				.addKey('G', ElectrodynamicsTags.Items.GEAR_STEEL)
				//
				.addKey('B', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('C', Tags.Items.CHESTS)
				//
				.addKey('T', Items.CRAFTING_TABLE)
				//
				.addKey('P', Items.PISTON)
				//
				.addKey('W', ElectrodynamicsItems.ITEMS_WIRE.getValue(SubtypeWire.copper))
				//
				.complete(References.ID, "autocrafter", output);

		ShapedCraftingRecipeBuilder.start(AssemblyLineItems.ITEM_CONVEYORBELT.get(), 12)
				//
				.addPattern("SSS")
				//
				.addPattern("WMW")
				//
				.addKey('S', ElectrodynamicsTags.Items.INGOT_STEEL)
				//
				.addKey('W', ItemTags.PLANKS)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "conveyorbelt", output);

		ShapedCraftingRecipeBuilder.start(AssemblyLineItems.ITEMS_ASSEMBLYMACHINE.getValue(SubtypeAssemblyMachine.blockbreaker), 1)
				//
				.addPattern("CPC")
				//
				.addPattern("COC")
				//
				.addPattern("CMC")
				//
				.addKey('C', Tags.Items.COBBLESTONES)
				//
				.addKey('P', Items.IRON_PICKAXE)
				//
				.addKey('O', Items.OBSERVER)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "blockbreaker", output);

		ShapedCraftingRecipeBuilder.start(AssemblyLineItems.ITEMS_ASSEMBLYMACHINE.getValue(SubtypeAssemblyMachine.blockplacer), 1)
				//
				.addPattern("CPC")
				//
				.addPattern("COC")
				//
				.addPattern("CMC")
				//
				.addKey('C', Tags.Items.COBBLESTONES)
				//
				.addKey('P', Items.PISTON)
				//
				.addKey('O', Items.OBSERVER)
				//
				.addKey('M', ElectrodynamicsItems.ITEM_MOTOR.get())
				//
				.complete(References.ID, "blockplacer", output);

		ShapedCraftingRecipeBuilder.start(AssemblyLineItems.ITEM_DETECTOR.get(), 1)
				//
				.addPattern("IEI")
				//
				.addPattern("ICI")
				//
				.addPattern("I I")
				//
				.addKey('I', ElectrodynamicsTags.Items.INGOT_STEEL)
				//
				.addKey('E', Tags.Items.ENDER_PEARLS)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.complete(References.ID, "detector", output);

		ShapedCraftingRecipeBuilder.start(AssemblyLineItems.ITEMS_ASSEMBLYMACHINE.getValue(SubtypeAssemblyMachine.farmer), 1)
				//
				.addPattern("PSP")
				//
				.addPattern("ACH")
				//
				.addPattern("PWP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('S', Items.SHEARS)
				//
				.addKey('A', Items.IRON_AXE)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('H', Items.IRON_HOE)
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.complete(References.ID, "farmer", output);

		ShapedCraftingRecipeBuilder.start(AssemblyLineItems.ITEMS_ASSEMBLYMACHINE.getValue(SubtypeAssemblyMachine.mobgrinder), 1)
				//
				.addPattern("PSP")
				//
				.addPattern("SCS")
				//
				.addPattern("PWP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('S', Items.IRON_SWORD)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.complete(References.ID, "mobgrinder", output);

		ShapedCraftingRecipeBuilder.start(AssemblyLineItems.ITEMS_ASSEMBLYMACHINE.getValue(SubtypeAssemblyMachine.rancher), 1)
				//
				.addPattern("PSP")
				//
				.addPattern("SCS")
				//
				.addPattern("PWP")
				//
				.addKey('P', ElectrodynamicsTags.Items.PLATE_STEEL)
				//
				.addKey('S', Items.SHEARS)
				//
				.addKey('C', ElectrodynamicsTags.Items.CIRCUITS_BASIC)
				//
				.addKey('W', ElectrodynamicsTags.Items.INSULATED_COPPER_WIRES)
				//
				.complete(References.ID, "rancher", output);

		ShapedCraftingRecipeBuilder.start(AssemblyLineItems.ITEM_SORTERBELT.get(), 1)
				//
				.addPattern("WWW")
				//
				.addPattern("HCH")
				//
				.addKey('W', ItemTags.PLANKS)
				//
				.addKey('H', Items.HOPPER)
				//
				.addKey('C', AssemblyLineItems.ITEM_CONVEYORBELT.get())
				//
				.complete(References.ID, "sorterbelt", output);

	}

}
