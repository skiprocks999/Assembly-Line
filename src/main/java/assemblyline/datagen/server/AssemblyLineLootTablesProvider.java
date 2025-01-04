package assemblyline.datagen.server;

import java.util.List;

import assemblyline.References;
import assemblyline.common.block.subtype.SubtypeAssemblyMachine;
import assemblyline.registers.AssemblyLineTiles;
import assemblyline.registers.AssemblyLineBlocks;
import electrodynamics.datagen.server.ElectrodynamicsLootTablesProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;

public class AssemblyLineLootTablesProvider extends ElectrodynamicsLootTablesProvider {

    public AssemblyLineLootTablesProvider(HolderLookup.Provider provider) {
        super(References.ID, provider);
    }

    @Override
    protected void generate() {

        addSimpleBlock(AssemblyLineBlocks.BLOCK_DETECTOR);

        addMachineTable(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.crate), AssemblyLineTiles.TILE_CRATE, true, false, false, false, false);
        addMachineTable(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.cratemedium), AssemblyLineTiles.TILE_CRATE, true, false, false, false, false);
        addMachineTable(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.cratelarge), AssemblyLineTiles.TILE_CRATE, true, false, false, false, false);

        addMachineTable(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.autocrafter), AssemblyLineTiles.TILE_AUTOCRAFTER, true, false, false, true, false);
        addMachineTable(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.blockbreaker), AssemblyLineTiles.TILE_BLOCKBREAKER, true, false, false, true, false);
        addMachineTable(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.blockplacer), AssemblyLineTiles.TILE_BLOCKPLACER, true, false, false, true, false);
        addMachineTable(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.rancher), AssemblyLineTiles.TILE_RANCHER, true, false, false, true, false);
        addMachineTable(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.mobgrinder), AssemblyLineTiles.TILE_MOBGRINDER, true, false, false, true, false);
        addMachineTable(AssemblyLineBlocks.BLOCKS_ASSEMBLYMACHINES.getValue(SubtypeAssemblyMachine.farmer), AssemblyLineTiles.TILE_FARMER, true, false, false, true, false);

    }

    @Override
    public List<Block> getExcludedBlocks() {
        return List.of(AssemblyLineBlocks.BLOCK_CONVEYORBELT.get(), AssemblyLineBlocks.BLOCK_SORTERBELT.get());
    }

}
