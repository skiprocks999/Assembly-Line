package assemblyline.client.screen;

import assemblyline.common.inventory.container.ContainerSorterBelt;
import assemblyline.common.settings.Constants;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenSorterBelt extends GenericScreen<ContainerSorterBelt> {
	public ScreenSorterBelt(ContainerSorterBelt container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);

		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(Constants.SORTERBELT_USAGE * 20));
	}
}