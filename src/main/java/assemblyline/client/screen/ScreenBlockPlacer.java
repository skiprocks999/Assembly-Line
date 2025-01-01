package assemblyline.client.screen;

import java.util.ArrayList;
import java.util.List;

import assemblyline.client.render.event.levelstage.HandlerHarvesterLines;
import assemblyline.client.screen.generic.GenericOutlineAreaScreen;
import assemblyline.common.inventory.container.ContainerBlockPlacer;
import assemblyline.common.settings.Constants;
import assemblyline.common.tile.TileBlockPlacer;
import assemblyline.prefab.utils.AssemblyTextUtils;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import electrodynamics.prefab.screen.component.types.ScreenComponentCountdown;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.types.wrapper.WrapperInventoryIO;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

public class ScreenBlockPlacer extends GenericOutlineAreaScreen<ContainerBlockPlacer> {
	public ScreenBlockPlacer(ContainerBlockPlacer container, Inventory playerInventory, Component title) {
		super(container, playerInventory, title);

		addComponent(new ScreenComponentCountdown(this::getTooltip, () -> {
			TileBlockPlacer placer = menu.getSafeHost();
			if (placer != null) {
				return 1.0 - (double) placer.ticksSinceCheck.get() / (double) placer.currentWaitTime.get();
			}
			return 0.0;
		}, 10, 50));

		addComponent(new ScreenComponentElectricInfo(this::getElectricInformation, -AbstractScreenComponentInfo.SIZE + 1, 2));

		addComponent(new ScreenComponentButton<>(10, 20, 60, 20).setLabel(() -> {
			TileBlockPlacer harvester = menu.getSafeHost();
			if (harvester != null) {
				return HandlerHarvesterLines.containsLines(harvester.getBlockPos()) ? AssemblyTextUtils.gui("hidearea") : AssemblyTextUtils.gui("renderarea");
			}
			return Component.empty();
		}).setOnPress(button -> toggleRendering()));

		new WrapperInventoryIO(this, -AbstractScreenComponentInfo.SIZE + 1, AbstractScreenComponentInfo.SIZE + 2, 75, 82, 8, 72);
	}

	private List<? extends FormattedCharSequence> getElectricInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileBlockPlacer harvester = menu.getSafeHost();
		if (harvester != null) {
			ComponentElectrodynamic electro = harvester.getComponent(IComponentType.Electrodynamic);
			list.add(AssemblyTextUtils.gui("machine.usage", ChatFormatter.getChatDisplayShort(Constants.BLOCKPLACER_USAGE * 20, DisplayUnit.WATT)).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(AssemblyTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

	@Override
	public boolean isFlipped() {
		return true;
	}

	protected List<? extends FormattedCharSequence> getTooltip() {
		TileBlockPlacer placer = menu.getSafeHost();
		List<FormattedCharSequence> tips = new ArrayList<>();

		if (placer != null) {
			tips.add(AssemblyTextUtils.tooltip("cooldown", placer.currentWaitTime.get() - placer.ticksSinceCheck.get()).withStyle(ChatFormatting.GRAY).getVisualOrderText());
		}

		return tips;
	}

}