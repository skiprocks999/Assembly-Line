package assemblyline.registers;

import assemblyline.References;
import electrodynamics.prefab.utilities.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class AssemblyLineAttachmentTypes {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, References.ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<BlockPos>> GRINDER_KILLED_MOB = ATTACHMENT_TYPES.register("grinderkilledmob", () -> AttachmentType.builder(() -> BlockEntityUtils.OUT_OF_REACH).serialize(BlockPos.CODEC).build());
}
