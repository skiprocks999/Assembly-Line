package assemblyline.client.screen;

import assemblyline.common.inventory.container.ContainerAutocrafter;
import assemblyline.common.settings.Constants;
import electrodynamics.prefab.screen.GenericScreen;
import electrodynamics.prefab.screen.component.ScreenComponentGeneric;
import electrodynamics.prefab.screen.component.types.ScreenComponentProgress.ProgressTextures;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ScreenAutocrafter extends GenericScreen<ContainerAutocrafter> {

	public ScreenAutocrafter(ContainerAutocrafter container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);

		addComponent(new ScreenComponentGeneric(ProgressTextures.ARROW_RIGHT_OFF, 80, 34));
		addComponent(new ScreenComponentElectricInfo(-AbstractScreenComponentInfo.SIZE + 1, 2).wattage(Constants.AUTOCRAFTER_USAGE));
		new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}

}