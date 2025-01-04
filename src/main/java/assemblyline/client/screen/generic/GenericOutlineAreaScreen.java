package assemblyline.client.screen.generic;

import assemblyline.client.render.event.levelstage.HandlerHarvesterLines;
import assemblyline.common.tile.util.TileOutlineArea;
import electrodynamics.prefab.inventory.container.types.GenericContainerBlockEntity;
import electrodynamics.prefab.screen.GenericScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public abstract class GenericOutlineAreaScreen<T extends GenericContainerBlockEntity<? extends TileOutlineArea>> extends GenericScreen<T> {

    public GenericOutlineAreaScreen(T container, Inventory inv, Component title) {
        super(container, inv, title);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        TileOutlineArea harvester = menu.getSafeHost();
        if (harvester != null && HandlerHarvesterLines.containsLines(harvester.getBlockPos())) {
            HandlerHarvesterLines.removeLines(harvester.getBlockPos());
            updateBox(harvester);
        }
    }

    //convenience method for toggle button
    public void toggleRendering() {
        TileOutlineArea harvester = menu.getSafeHost();
        if (harvester != null) {
            BlockPos pos = harvester.getBlockPos();
            if (HandlerHarvesterLines.containsLines(pos)) {
                HandlerHarvesterLines.removeLines(pos);
            } else {
                updateBox(harvester);
            }
        }
    }

    public void updateBox(TileOutlineArea area) {
        HandlerHarvesterLines.addLines(area.getBlockPos(), area.getAABB(area.width.get(), area.length.get(), area.height.get(), isFlipped()));
    }

    public abstract boolean isFlipped();

}
