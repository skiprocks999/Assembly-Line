package assemblyline.common.inventory.container;

import assemblyline.common.tile.TileFarmer;
import assemblyline.registers.AssemblyLineMenuTypes;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.prefab.inventory.container.slot.item.SlotGeneric;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotUpgrade;
import electrodynamics.prefab.inventory.container.types.GenericContainerBlockEntity;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.BoneMealItem;

public class ContainerFarmer extends GenericContainerBlockEntity<TileFarmer> {

	public static final SubtypeItemUpgrade[] VALID_UPGRADES = new SubtypeItemUpgrade[] { SubtypeItemUpgrade.advancedspeed, SubtypeItemUpgrade.basicspeed, SubtypeItemUpgrade.itemoutput, SubtypeItemUpgrade.range };

	public ContainerFarmer(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(22), new SimpleContainerData(3));
	}

	public ContainerFarmer(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(AssemblyLineMenuTypes.CONTAINER_FARMER.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		setPlayerInvOffset(58);
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				addSlot(new SlotGeneric(inv, nextIndex(), 85 + j * 18, 17 + i * 18).setIOColor(new Color(0, 240, 255, 255)));
			}
		}
		addSlot(new SlotRestricted(inv, nextIndex(), 153, 17).setRestriction(BoneMealItem.class).setIOColor(new Color(144, 0, 255, 255)));
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				addSlot(new SlotRestricted(inv, nextIndex(), 85 + j * 18, 75 + i * 18).setIOColor(new Color(255, 0, 0, 255)));
			}
		}
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 71, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 91, VALID_UPGRADES));
		addSlot(new SlotUpgrade(inv, nextIndex(), 153, 111, VALID_UPGRADES));
	}

}
