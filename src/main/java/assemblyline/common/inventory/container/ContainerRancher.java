package assemblyline.common.inventory.container;

import assemblyline.common.tile.TileRancher;
import assemblyline.registers.AssemblyLineMenuTypes;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import electrodynamics.prefab.inventory.container.types.GenericContainerBlockEntity;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerRancher extends GenericContainerBlockEntity<TileRancher> {

    public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] { SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.range };

    public ContainerRancher(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
        super(AssemblyLineMenuTypes.CONTAINER_RANCHER.get(), id, playerinv, inventory, inventorydata);
    }

    public ContainerRancher(int id, Inventory playerinv) {
        this(id, playerinv, new SimpleContainer(12), new SimpleContainerData(3));
    }

    @Override
    public void addInventorySlots(Container inv, Inventory playerinv) {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                addSlot(new SlotRestricted(inv, nextIndex(), 85 + j * 18, 17 + i * 18).setIOColor(new Color(255, 0, 0, 255)));
            }
        }
        addSlot(new SlotUpgrade(inv, nextIndex(), 153, 14, VALID_UPGRADES));
        addSlot(new SlotUpgrade(inv, nextIndex(), 153, 34, VALID_UPGRADES));
        addSlot(new SlotUpgrade(inv, nextIndex(), 153, 54, VALID_UPGRADES));
    }

}
