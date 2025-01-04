package assemblyline.datagen.server;

import java.util.concurrent.CompletableFuture;

import assemblyline.References;
import assemblyline.registers.AssemblyLineBlocks;
import electrodynamics.common.block.BlockMachine;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class AssemblyLineBlockTagsProvider extends BlockTagsProvider {

    public AssemblyLineBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, References.ID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider provider) {

        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                //
                AssemblyLineBlocks.BLOCK_CONVEYORBELT.get(),
                //
                AssemblyLineBlocks.BLOCK_SORTERBELT.get(),
                //
                AssemblyLineBlocks.BLOCK_DETECTOR.get()
                //
        ).add(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getAllValuesArray(new BlockMachine[0]));

        tag(BlockTags.NEEDS_STONE_TOOL).add(
                //
                AssemblyLineBlocks.BLOCK_CONVEYORBELT.get(),
                //
                AssemblyLineBlocks.BLOCK_SORTERBELT.get(),
                //
                AssemblyLineBlocks.BLOCK_DETECTOR.get()
                //
        ).add(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getAllValuesArray(new BlockMachine[0]));
    }

}
