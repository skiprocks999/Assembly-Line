package assemblyline.common.event;

import java.util.ArrayList;
import java.util.List;

import assemblyline.References;
import assemblyline.common.tile.TileMobGrinder;
import assemblyline.registers.AssemblyLineAttachmentTypes;
import electrodynamics.prefab.tile.components.IComponentType;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import electrodynamics.prefab.utilities.InventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

@EventBusSubscriber(modid = References.ID, bus = EventBusSubscriber.Bus.GAME)
public class EventHandler {

    @SubscribeEvent
    public static void captureDroppedItems(LivingDropsEvent event) {

        Entity entity = event.getEntity();

        BlockPos pos = entity.getData(AssemblyLineAttachmentTypes.GRINDER_KILLED_MOB);

        if (pos.equals(BlockEntityUtils.OUT_OF_REACH)) {
            return;
        }

        if (entity.level().getBlockEntity(pos) instanceof TileMobGrinder grinder) {

            List<ItemStack> droppedItems = new ArrayList<>();

            event.getDrops().forEach(h -> droppedItems.add(h.getItem()));

            ComponentInventory inv = grinder.getComponent(IComponentType.Inventory);

            InventoryUtils.addItemsToInventory(inv, droppedItems, inv.getOutputStartIndex(), inv.getOutputContents().size());

            event.setCanceled(true);
        }

    }

}
