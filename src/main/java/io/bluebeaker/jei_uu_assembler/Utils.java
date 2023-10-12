package io.bluebeaker.jei_uu_assembler;

import net.minecraft.client.resources.I18n;

public class Utils {
    public static class Translator{
        public static String localize(String text,Object[] params){
            return I18n.format(text, params);
        }
    }
}
