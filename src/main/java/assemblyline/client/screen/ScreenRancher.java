package assemblyline.client.screen;

import assemblyline.client.render.event.levelstage.HandlerHarvesterLines;
import assemblyline.client.screen.generic.GenericOutlineAreaScreen;
import assemblyline.common.inventory.container.ContainerRancher;
import assemblyline.common.settings.Constants;
import assemblyline.common.tile.TileRancher;
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

import java.util.ArrayList;
import java.util.List;

public class ScreenRancher extends GenericOutlineAreaScreen<ContainerRancher> {

    public ScreenRancher(ContainerRancher container, Inventory inv, Component title) {
        super(container, inv, title);

        addComponent(new ScreenComponentCountdown(this::getTooltip, () -> {
            TileRancher rancher = menu.getSafeHost();
            if (rancher != null) {
                return 1.0 - (double) rancher.ticksSinceCheck.get() / (double) rancher.currentWaitTime.get();
            }
            return 0.0;
        }, 10, 50));
        addComponent(new ScreenComponentElectricInfo(this::getElectricInformation, -AbstractScreenComponentInfo.SIZE + 1, 2));
        addComponent(new ScreenComponentButton<>(10, 20, 60, 20).setLabel(() -> {
            TileRancher harvester = menu.getSafeHost();
            if (harvester != null) {
                return HandlerHarvesterLines.containsLines(harvester.getBlockPos()) ? AssemblyTextUtils.gui("hidearea") : AssemblyTextUtils.gui("renderarea");
            }
            return Component.empty();
        }).setOnPress(button -> toggleRendering()));
    }

    private List<? extends FormattedCharSequence> getElectricInformation() {
        ArrayList<FormattedCharSequence> list = new ArrayList<>();
        TileRancher harvester = menu.getSafeHost();
        if (harvester != null) {
            ComponentElectrodynamic electro = harvester.getComponent(IComponentType.Electrodynamic);
            list.add(AssemblyTextUtils.gui("machine.usage", ChatFormatter.getChatDisplayShort(Constants.RANCHER_USAGE * harvester.powerUsageMultiplier.get() * 20, DisplayUnit.WATT)).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.DARK_GRAY).getVisualOrderText());
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
        TileRancher harvester = menu.getSafeHost();
        if (harvester != null) {
            tips.add(AssemblyTextUtils.tooltip("cooldown", harvester.currentWaitTime.get() - harvester.ticksSinceCheck.get()).withStyle(ChatFormatting.GRAY).getVisualOrderText());
        }

        return tips;
    }
}
