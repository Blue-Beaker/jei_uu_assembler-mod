package io.bluebeaker.jei_uu_assembler.utils;

import ic2.core.block.TeBlockRegistry;
import ic2.core.ref.TeBlock;
import net.minecraft.client.resources.I18n;

public class Utils {
    public static class Translator{
        public static String localize(String text,Object[] params){
            return I18n.format(text, params);
        }
    }
    public static String getTranslationKeyFromTeBlock(TeBlock fluidHeatGenerator){
        return TeBlockRegistry.get(fluidHeatGenerator.getIdentifier()).getItemStack(fluidHeatGenerator).getTranslationKey();
    }
}
