package io.bluebeaker.jei_uu_assembler.mixin;

import ic2.core.GuiIC2;
import ic2.core.block.machine.container.ContainerBatchCrafter;
import ic2.core.block.machine.gui.GuiBatchCrafter;
import ic2.core.block.machine.tileentity.TileEntityAssemblyBench;
import ic2.core.gui.RecipeButton;
import io.bluebeaker.jei_uu_assembler.jei.uu.UURecipeCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiBatchCrafter.class,remap = false)
public abstract class MixinGuiBatchCrafter extends GuiIC2<ContainerBatchCrafter> {
    public MixinGuiBatchCrafter(ContainerBatchCrafter container) {
        super(container);
    }

    @Inject(method = "<init>",at = @At("RETURN"))
    public void addRecipeButton(ContainerBatchCrafter container, CallbackInfo ci){
        if(container.base.getClass().equals(TileEntityAssemblyBench.class)){
            this.addElement(new RecipeButton(this,90,35,22,15, new String[]{UURecipeCategory.UID}));
        }else {
            this.addElement(new RecipeButton(this,90,35,22,15, new String[]{"minecraft.crafting"}));
        }
    }
}
