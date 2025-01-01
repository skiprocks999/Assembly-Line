package assemblyline;

import assemblyline.client.ClientRegister;
import assemblyline.common.block.AssemblyLineVoxelShapes;
import assemblyline.common.settings.Constants;
import assemblyline.registers.UnifiedAssemblyLineRegister;
import electrodynamics.prefab.configuration.ConfigurationHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(References.ID)
@EventBusSubscriber(modid = References.ID, bus = EventBusSubscriber.Bus.MOD)
public class AssemblyLine {

	public AssemblyLine(IEventBus bus) {
		ConfigurationHandler.registerConfig(Constants.class);
		AssemblyLineVoxelShapes.init();
		UnifiedAssemblyLineRegister.register(bus);
	}

	@SubscribeEvent
	public static void onCommonSetup(FMLCommonSetupEvent event) {

	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onClientSetup(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ClientRegister.setup();
		});
	}

}
