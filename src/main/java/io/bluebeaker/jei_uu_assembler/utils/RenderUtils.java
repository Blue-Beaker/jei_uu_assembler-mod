package io.bluebeaker.jei_uu_assembler.utils;

import net.minecraft.client.Minecraft;

public class RenderUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void drawTextAlignedRight(String text,int x,int y,int color){
        mc.fontRenderer.drawString(text, x-mc.fontRenderer.getStringWidth(text), y, color);
    }
}
