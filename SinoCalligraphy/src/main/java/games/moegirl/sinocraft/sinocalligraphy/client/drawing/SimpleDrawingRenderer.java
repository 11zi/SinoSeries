package games.moegirl.sinocraft.sinocalligraphy.client.drawing;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import games.moegirl.sinocraft.sinocalligraphy.drawing.InkType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.PaperType;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.SimpleDrawing;
import games.moegirl.sinocraft.sinocalligraphy.drawing.simple.traits.IHasPaperType;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FastColor;

import static games.moegirl.sinocraft.sinocalligraphy.client.drawing.RenderTypes.COLOR_256;
import static games.moegirl.sinocraft.sinocalligraphy.client.drawing.RenderTypes.COLOR_LIGHT_256;

public class SimpleDrawingRenderer implements IDrawingRenderer {
    protected SimpleDrawing drawing;

    public SimpleDrawingRenderer(SimpleDrawing drawing) {
        this.drawing = drawing;
    }

    @Override
    public void draw(PoseStack poseStack, int x, int y, int width, int height) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        if (drawing.isEmpty()) {
            GuiComponent.fill(poseStack, x, y, x + width, y + height, getBackgroundColor());
        } else {
            int unitX = width / drawing.getSize();
            int unitY = height / drawing.getSize();
            int x1 = x;
            int x2 = x1 + unitX;
            byte[] draw = drawing.getPixels();
            int index = 0;
            for (int i = 0; i < drawing.getSize(); i++) {
                int y1 = y;
                int y2 = y1 + unitY;
                for (int j = 0; j < drawing.getSize(); j++) {
                    int color = getForegroundColor();
                    int r = FastColor.ARGB32.red(color);
                    int g = FastColor.ARGB32.green(color);
                    int b = FastColor.ARGB32.blue(color);
                    int scale = 16 * (16 - draw[index++]) - 1; // qyl27: For calculating grayscale.
                    int scaledColor = FastColor.ARGB32.color(scale, r, g, b);
                    RenderSystem.setShaderColor(color, color, color, 1.0f);
                    GuiComponent.fill(poseStack, x1, y1, x2, y2, scaledColor);
                    y1 = y2;
                    y2 += unitY;
                }
                x1 = x2;
                x2 += unitX;
            }
        }
    }

    @Override
    public void draw(PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        VertexConsumer vertexConsumer = buffer.getBuffer(COLOR_LIGHT_256);
        if (drawing.isEmpty()) {
            vertexRect(poseStack, vertexConsumer, 0, 0, drawing.getSize(), drawing.getSize(), getBackgroundColor(), packedLight);
        } else {
            drawPixels(poseStack, vertexConsumer, packedLight);
        }
    }

    @Override
    public void draw(PoseStack poseStack, MultiBufferSource buffer) {
        VertexConsumer vertexConsumer = buffer.getBuffer(COLOR_256);
        if (drawing.isEmpty()) {
            vertexRect(poseStack, vertexConsumer, 0, 0, drawing.getSize(), drawing.getSize(), getBackgroundColor());
        } else {
            drawPixels(poseStack, vertexConsumer);
        }
    }

    protected void drawPixels(PoseStack poseStack, VertexConsumer consumer) {
        byte[] pixels = drawing.getPixels();
        for (int x1 = 0; x1 < drawing.getSize(); x1++) {
            int x2 = x1 + 1;
            for (int y1 = 0; y1 < drawing.getSize(); y1++) {
                int geryScale = 16 * (16 - pixels[x1 * drawing.getSize() + y1]) - 1;
                int y2 = y1 + 1;
                vertexRectScaleColor(poseStack, consumer, x1, y1, x2, y2, getForegroundColor(), geryScale);
            }
        }
    }

    protected void drawPixels(PoseStack poseStack, VertexConsumer consumer, int light) {
        byte[] pixels = drawing.getPixels();
        for (int x1 = 0; x1 < drawing.getSize(); x1++) {
            int x2 = x1 + 1;
            for (int y1 = 0; y1 < drawing.getSize(); y1++) {
                int geryScale = 16 * (16 - pixels[x1 * drawing.getSize() + y1]) - 1;
                int y2 = y1 + 1;
                vertexRectScaleColor(poseStack, consumer, x1, y1, x2, y2, getForegroundColor(), geryScale, light);
            }
        }
    }

    protected void vertexRectScaleColor(PoseStack poseStack, VertexConsumer consumer,
                              int x1, int y1, int x2, int y2,
                              int color, int scale) {
        int r = FastColor.ARGB32.red(color);
        int g = FastColor.ARGB32.green(color);
        int b = FastColor.ARGB32.blue(color);
        int scaledColor = FastColor.ARGB32.color(scale, r, g, b);

        vertexRect(poseStack, consumer, x1, y1, x2, y2, scaledColor);
    }

    protected void vertexRectScaleColor(PoseStack poseStack, VertexConsumer consumer,
                                        int x1, int y1, int x2, int y2,
                                        int color, int scale, int light) {
        int r = FastColor.ARGB32.red(color);
        int g = FastColor.ARGB32.green(color);
        int b = FastColor.ARGB32.blue(color);
        int scaledColor = FastColor.ARGB32.color(scale, r, g, b);

        vertexRect(poseStack, consumer, x1, y1, x2, y2, scaledColor, light);
    }

    protected void vertexRect(PoseStack poseStack, VertexConsumer consumer,
                            int x1, int y1, int x2, int y2,
                            int color) {
        consumer.vertex(poseStack.last().pose(), x1, y1, 0).color(color).endVertex();
        consumer.vertex(poseStack.last().pose(), x1, y2, 0).color(color).endVertex();
        consumer.vertex(poseStack.last().pose(), x2, y2, 0).color(color).endVertex();
        consumer.vertex(poseStack.last().pose(), x2, y1, 0).color(color).endVertex();
    }

    protected void vertexRect(PoseStack poseStack, VertexConsumer consumer,
                              int x1, int y1, int x2, int y2,
                              int color, int light) {
        consumer.vertex(poseStack.last().pose(), x1, y1, 0).color(color).uv2(light).endVertex();
        consumer.vertex(poseStack.last().pose(), x1, y2, 0).color(color).uv2(light).endVertex();
        consumer.vertex(poseStack.last().pose(), x2, y2, 0).color(color).uv2(light).endVertex();
        consumer.vertex(poseStack.last().pose(), x2, y1, 0).color(color).uv2(light).endVertex();
    }


    protected int getBackgroundColor() {
        if (drawing != null) {
            return drawing.getPaperType().getColor();
        }

        return PaperType.WHITE.getColor();
    }

    protected int getForegroundColor() {
        if (drawing != null) {
            return drawing.getInkType().getColor();
        }

        return InkType.BLACK.getColor();
    }
}
