package io.bluebeaker.jei_uu_assembler.jei.generator;

import io.bluebeaker.jei_uu_assembler.jei.generic.GenericRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;

public abstract class FluidPowerRecipeCategory extends GenericRecipeCategory<FluidPowerRecipeWrapper> {

    public FluidPowerRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper,116,32);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FluidPowerRecipeWrapper wrapper, IIngredients iIngredients) {
        IGuiFluidStackGroup guiFluidStackGroup = recipeLayout.getFluidStacks();
        this.addFluidSlot(guiFluidStackGroup,0,16,GUI_HEIGHT/2-8);
        guiFluidStackGroup.set(0,wrapper.getFluidStack());
    }


}
