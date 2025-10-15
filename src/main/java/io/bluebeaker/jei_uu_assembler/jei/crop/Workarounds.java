package io.bluebeaker.jei_uu_assembler.jei.crop;

import ic2.api.crops.CropCard;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Workarounds {
    public static int eatingPlantGrowthDuration(CropCard card){
        return card.getProperties().getTier() * 100;
    }

    public static Map<ItemStack, Float> redWheatHarvest() {
        HashMap<ItemStack, Float> outputCounter = new HashMap<>();

        ItemStack redstone = new ItemStack(Items.REDSTONE);
        ItemStack wheat = new ItemStack(Items.WHEAT);

        outputCounter.put(redstone,0.5f);
        outputCounter.put(wheat,0.5f);
        return outputCounter;
    }
}
