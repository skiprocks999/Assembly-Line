package assemblyline.client.render.event.levelstage;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import net.minecraft.client.DeltaTracker;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;

import electrodynamics.client.render.event.levelstage.AbstractLevelStageHandler;
import electrodynamics.prefab.utilities.math.Color;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HandlerFarmerLines extends AbstractLevelStageHandler {

	public static final HandlerFarmerLines INSTANCE = new HandlerFarmerLines();

	private final HashMap<BlockPos, Pair<Color[], List<AABB>>> farmerLines = new HashMap<>();

	@Override
	public void render(Camera camera, Frustum frustum, LevelRenderer levelRenderer, PoseStack stack, Matrix4f matrix4f, Minecraft minecraft, int renderTick, DeltaTracker deltaTracker) {

		stack.pushPose();

		MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
		VertexConsumer builder = buffer.getBuffer(RenderType.LINES);
		Vec3 camPos = camera.getPosition();

		stack.translate(-camPos.x, -camPos.y, -camPos.z);

		for (Entry<BlockPos, Pair<Color[], List<AABB>>> en : farmerLines.entrySet()) {
			Color[] rgbaValues = en.getValue().getFirst();
			List<AABB> lines = en.getValue().getSecond();
			for (int i = 0; i < lines.size(); i++) {
				AABB box = lines.get(i).deflate(0.01);
				float[] rgba = rgbaValues[i].colorFloatArr();

				LevelRenderer.renderLineBox(stack, builder, box, rgba[0], rgba[1], rgba[2], rgba[3]);

			}
		}

		stack.popPose();

	}

	@Override
	public boolean shouldRender(RenderLevelStageEvent.Stage stage) {
		return stage == RenderLevelStageEvent.Stage.AFTER_TRIPWIRE_BLOCKS;
	}

	@Override
	public void clear() {
		farmerLines.clear();
	}

	public static boolean isBeingRendered(BlockPos pos) {
		return INSTANCE.farmerLines.containsKey(pos);
	}

	public static void remove(BlockPos pos) {
		INSTANCE.farmerLines.remove(pos);
	}

	public static void addRenderData(BlockPos pos, Pair<Color[], List<AABB>> data) {
		INSTANCE.farmerLines.put(pos, data);
	}

}
