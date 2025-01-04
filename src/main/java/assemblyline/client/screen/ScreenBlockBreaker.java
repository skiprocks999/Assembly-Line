package assemblyline.client.screen;

import java.util.ArrayList;
import java.util.List;

import assemblyline.client.render.event.levelstage.HandlerHarvesterLines;
import assemblyline.client.screen.generic.GenericOutlineAreaScreen;
import assemblyline.common.inventory.container.ContainerBlockBreaker;
import assemblyline.common.settings.Constants;
import assemblyline.common.tile.TileBlockBreaker;
import assemblyline.prefab.utils.AssemblyTextUtils;
import electrodynamics.api.electricity.formatting.ChatFormatter;
import electrodynamics.api.electricity.formatting.DisplayUnit;
import electrodynamics.prefab.screen.component.button.ScreenComponentButton;
import electrodynamics.prefab.screen.component.types.ScreenComponentCountdown;
import electrodynamics.prefab.screen.component.types.guitab.ScreenComponentElectricInfo;
import electrodynamics.prefab.screen.component.utils.AbstractScreenComponentInfo;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

public class ScreenBlockBreaker extends GenericOutlineAreaScreen<ContainerBlockBreaker> {

	public ScreenBlockBreaker(ContainerBlockBreaker screenContainer, Inventory inv, Component titleIn) {
		super(screenContainer, inv, titleIn);

		addComponent(new ScreenComponentCountdown(this::getTooltip, () -> {
			TileBlockBreaker breaker = menu.getSafeHost();
			if (breaker != null) {
				return (double) breaker.ticksSinceCheck.get() / (double) breaker.currentWaitTime.get();
			}
			return 0.0;
		}, 10, 50));

		addComponent(new ScreenComponentElectricInfo(this::getElectricInformation, -AbstractScreenComponentInfo.SIZE + 1, 2));

		addComponent(new ScreenComponentButton<>(10, 20, 60, 20).setLabel(() -> {
			TileBlockBreaker harvester = menu.getSafeHost();
			if (harvester != null) {
				return HandlerHarvesterLines.containsLines(harvester.getBlockPos()) ? AssemblyTextUtils.gui("hidearea") : AssemblyTextUtils.gui("renderarea");
			}
			return Component.empty();
		}).setOnPress(button -> toggleRendering()));
		
	}

	private List<? extends FormattedCharSequence> getElectricInformation() {
		ArrayList<FormattedCharSequence> list = new ArrayList<>();
		TileBlockBreaker harvester = menu.getSafeHost();
		if (harvester != null) {
			ComponentElectrodynamic electro = harvester.getComponent(IComponentType.Electrodynamic);
			list.add(AssemblyTextUtils.gui("machine.usage", ChatFormatter.getChatDisplayShort(Constants.BLOCKBREAKER_USAGE * 20, DisplayUnit.WATT)).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
			list.add(AssemblyTextUtils.gui("machine.voltage", ChatFormatter.getChatDisplayShort(electro.getVoltage(), DisplayUnit.VOLTAGE)).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
		}
		return list;
	}

	@Override
	public boolean isFlipped() {
		return true;
	}

	protected List<? extends FormattedCharSequence> getTooltip() {
		List<FormattedCharSequence> tips = new ArrayList<>();
		TileBlockBreaker breaker = menu.getSafeHost();

		if (breaker != null) {
			tips.add(AssemblyTextUtils.tooltip("breakingprogress", ChatFormatter.getChatDisplayShort(100.0 * (double) breaker.ticksSinceCheck.get() / (double) breaker.currentWaitTime.get(), DisplayUnit.PERCENTAGE)).withStyle(ChatFormatting.GRAY).getVisualOrderText());
		}

		return tips;

	}

}
