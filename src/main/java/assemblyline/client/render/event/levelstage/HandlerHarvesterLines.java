package assemblyline.client.render.event.levelstage;

import java.util.HashMap;
import java.util.Map.Entry;

import net.minecraft.client.DeltaTracker;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import electrodynamics.client.render.event.levelstage.AbstractLevelStageHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HandlerHarvesterLines extends AbstractLevelStageHandler {

	public static final HandlerHarvesterLines INSTANCE = new HandlerHarvesterLines();

	private final HashMap<BlockPos, AABB> outlines = new HashMap<>();

	@Override
	public void render(Camera camera, Frustum frustum, LevelRenderer levelRenderer, PoseStack stack, Matrix4f matrix4f, Minecraft minecraft, int renderTick, DeltaTracker deltaTracker) {

		MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
		VertexConsumer builder = buffer.getBuffer(RenderType.LINES);
		Vec3 camPos = camera.getPosition();

		stack.pushPose();
		stack.translate(-camPos.x, -camPos.y, -camPos.z);

		for (Entry<BlockPos, AABB> en : outlines.entrySet()) {
			AABB box = en.getValue().deflate(0.001);
			LevelRenderer.renderLineBox(stack, builder, box, 1.0F, 1.0F, 1.0F, 1.0F);
		}

		buffer.endBatch(RenderType.LINES);
		stack.popPose();

	}

	@Override
	public boolean shouldRender(RenderLevelStageEvent.Stage stage) {
		return stage == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS;
	}

	@Override
	public void clear() {
		outlines.clear();
	}

	public static boolean containsLines(BlockPos pos) {
		return INSTANCE.outlines.containsKey(pos);
	}

	public static void addLines(BlockPos pos, AABB lines) {
		INSTANCE.outlines.put(pos, lines);
	}

	public static void removeLines(BlockPos pos) {
		INSTANCE.outlines.remove(pos);
	}

}
