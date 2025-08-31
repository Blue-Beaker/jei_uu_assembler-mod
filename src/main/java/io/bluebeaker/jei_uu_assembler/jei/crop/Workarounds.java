package io.bluebeaker.jei_uu_assembler.jei.crop;

import ic2.api.crops.CropCard;

public class Workarounds {
    public static int getGrowthDuration(CropCard card){
        return card.getProperties().getTier() * 100;
    }
}
