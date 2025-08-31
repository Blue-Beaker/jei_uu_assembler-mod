package io.bluebeaker.jei_uu_assembler.jei.crop;

import mezz.jei.api.gui.ITooltipCallback;
import net.minecraft.item.ItemStack;

import java.util.*;

public class StringTooltipCallback implements ITooltipCallback<ItemStack>{
    private final Map<Integer,String[]> tooltips = new HashMap<>();
    public void addTooltipToIndex(int i, String[] lines){
        tooltips.put(i,lines);
    }
    @Override
    public void onTooltip(int i, boolean b, ItemStack stack, List<String> list) {
        String[] tip = tooltips.getOrDefault(i, null);
        if(tip==null) return;
        list.addAll(Arrays.asList(tip));
    }
}
