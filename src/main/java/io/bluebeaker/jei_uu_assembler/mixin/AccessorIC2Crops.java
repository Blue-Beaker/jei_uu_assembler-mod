package io.bluebeaker.jei_uu_assembler.mixin;

import ic2.api.crops.BaseSeed;
import ic2.core.crop.IC2Crops;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = IC2Crops.class,remap = false)
public interface AccessorIC2Crops {
    @Accessor
    Map<ItemStack, BaseSeed> getBaseSeeds();
}
