package io.bluebeaker.jei_uu_assembler.utils;

import ic2.api.info.Info;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mezz.jei.api.IModRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class IC2Utils {
    public static Map<Integer,List<ItemStack>> getFuelValuesGenerator(IModRegistry registry){
        List<ItemStack> fuelStacks = registry.getIngredientRegistry().getFuels();

        Map<Integer,List<ItemStack>> fuels = new Int2ObjectOpenHashMap<>(fuelStacks.size() / 8);
        for(ItemStack fuelStack : fuelStacks) {
            for(ItemStack subtype : registry.getJeiHelpers().getStackHelper().getSubtypes(fuelStack)) {
                int burnTime = Info.itemInfo.getFuelValue(subtype,false);
                if(burnTime<=0) continue;
                fuels.computeIfAbsent(burnTime/4, (k) -> new ArrayList<>(8)).add(subtype);
            }
        }
        return fuels;
    }
    public static List<Map.Entry<Integer,List<ItemStack>>> getFuelsSorted(IModRegistry registry){
        List<Map.Entry<Integer, List<ItemStack>>> list = new ArrayList<>(getFuelValuesGenerator(registry).entrySet());
        list.sort(Comparator.comparingInt(Map.Entry::getKey));
        return list;
    }
}
