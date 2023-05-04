package broojel.fpstractor.mixin;

import broojel.fpstractor.FPSTractor;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class HudMixin {
	private static final Identifier UNCANNY0 = new Identifier("fpstractor", "textures/misc/uncanny0.png");
	private static final Identifier UNCANNY1 = new Identifier("fpstractor", "textures/misc/uncanny1.png");
	private static final Identifier UNCANNY2 = new Identifier("fpstractor", "textures/misc/uncanny2.png");
	private static final Identifier UNCANNY3 = new Identifier("fpstractor", "textures/misc/uncanny3.png");
	private static final Identifier UNCANNY4 = new Identifier("fpstractor", "textures/misc/uncanny4.png");
	private static final Identifier UNCANNY5 = new Identifier("fpstractor", "textures/misc/uncanny5.png");
	private static final Identifier UNCANNY6 = new Identifier("fpstractor", "textures/misc/uncanny6.png");
	private static final Identifier UNCANNY7 = new Identifier("fpstractor", "textures/misc/uncanny7.png");
	private static final Identifier UNCANNY8 = new Identifier("fpstractor", "textures/misc/uncanny8.png");
	private static final Identifier UNCANNY9 = new Identifier("fpstractor", "textures/misc/uncanny9.png");
	@Inject(at = @At("TAIL"), method = "render")
	public void render(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
		MinecraftClient client = MinecraftClient.getInstance();
		int fps = ((ClientMixin) client).getCurrentFps();

		double scaleFactor = client.getWindow().getScaleFactor();

		float imagePosX = client.getWindow().getScaledWidth() - 8 * (float)scaleFactor - 8;

		float textPosX =  imagePosX - 8 - client.textRenderer.getWidth(Integer.toString(fps)) * 3f;

		float textPosY = 8;



		int textColor = 0xFFFFFFFF;

		Identifier texture = UNCANNY0;
		if (fps < 60 && fps >= 50) {
			texture = UNCANNY1;
		} else if (fps < 50 && fps >= 40) {
			texture = UNCANNY2;
		} else if (fps < 40 && fps >= 30) {
			texture = UNCANNY3;
		} else if (fps < 30 && fps >= 25) {
			texture = UNCANNY4;
		} else if (fps < 25 && fps >= 20) {
			texture = UNCANNY5;
		} else if (fps < 20 && fps >= 15) {
			texture = UNCANNY6;
		} else if (fps < 15 && fps >= 10) {
			texture = UNCANNY7;
		} else if (fps < 10 && fps >= 5) {
			texture = UNCANNY8;
		} else if (fps < 5) {
			texture = UNCANNY9;
		}

		this.renderText(matrixStack, client.textRenderer, Integer.toString(fps), textPosX, textPosY, textColor, 3f);

		RenderSystem.setShaderTexture(0, texture);
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		this.renderImage(matrixStack, imagePosX, textPosY, (float)scaleFactor);
	}

	private void renderText(MatrixStack matrixStack, TextRenderer textRenderer, String text, float x, float y, int color, float scale) {
		matrixStack.push();
		matrixStack.translate(x, y, 0);
		matrixStack.scale(scale, scale, 1f);
		matrixStack.translate(-x, -y, 0);
		textRenderer.drawWithShadow(matrixStack, text, x, y, color);
		matrixStack.pop();
	}

	private void renderImage(MatrixStack matrixStack, float x, float y, float scale) {

		DrawableHelper.drawTexture(matrixStack, (int) x, (int) y, (int) (scale * 8f), (int) (scale * 8f), 0, 0, 8, 8, 8, 8);

	}
}
