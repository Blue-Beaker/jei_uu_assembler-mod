package io.bluebeaker.jei_uu_assembler.jei.crop;

import mezz.jei.api.ISubtypeRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CropSubtypeInterpreter implements ISubtypeRegistry.ISubtypeInterpreter{
    @Override
    public String apply(ItemStack itemStack) {
        if (!itemStack.hasTagCompound()) {
            return "";
        } else {
            NBTTagCompound tagCompound = itemStack.getTagCompound();
            return tagCompound.getString("owner")+":"+tagCompound.getString("id");
        }
    }
}
