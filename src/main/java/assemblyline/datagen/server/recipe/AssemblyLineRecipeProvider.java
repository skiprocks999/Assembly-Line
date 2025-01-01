package assemblyline.datagen.server.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import assemblyline.datagen.server.recipe.vanilla.AssemblyLineCraftingTableRecipes;
import electrodynamics.datagen.utils.recipe.AbstractRecipeGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

public class AssemblyLineRecipeProvider extends RecipeProvider {

	public final List<AbstractRecipeGenerator> GENERATORS = new ArrayList<>();

	private final CompletableFuture<HolderLookup.Provider> lookupProvider;

	public AssemblyLineRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider);
		this.lookupProvider = lookupProvider;
		addRecipes();
	}

	public void addRecipes() {
		GENERATORS.add(new AssemblyLineCraftingTableRecipes());
	}

	@Override
	protected void buildRecipes(RecipeOutput output) {
		for (AbstractRecipeGenerator generator : GENERATORS) {
			generator.addRecipes(output);
		}
	}

}
