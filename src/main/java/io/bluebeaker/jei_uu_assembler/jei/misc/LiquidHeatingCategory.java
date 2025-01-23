package io.bluebeaker.jei_uu_assembler.jei.misc;

import ic2.api.recipe.ILiquidAcceptManager;
import ic2.api.recipe.ILiquidHeatExchangerManager;
import io.bluebeaker.jei_uu_assembler.jei.generic.GenericRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class LiquidHeatingCategory extends GenericRecipeCategory<FluidHeatingRecipeWrapper> {
    public LiquidHeatingCategory(IGuiHelper guiHelper) {
        super(guiHelper,116,32);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, FluidHeatingRecipeWrapper wrapper, IIngredients iIngredients) {
        IGuiFluidStackGroup guiFluidStackGroup = recipeLayout.getFluidStacks();
        this.addFluidSlot(guiFluidStackGroup,0,8,GUI_HEIGHT/2-8);
        guiFluidStackGroup.set(0,wrapper.inputStack);
        this.addFluidSlot(guiFluidStackGroup,1,this.GUI_WIDTH-24,GUI_HEIGHT/2-8);
        guiFluidStackGroup.set(1,wrapper.outputStack);
    }

    public static List<FluidHeatingRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers, ILiquidHeatExchangerManager manager, @Nullable ILiquidAcceptManager acceptManager){

        List<FluidHeatingRecipeWrapper> recipes = new ArrayList<>();
        for (Fluid inputFluid : manager.getAcceptedFluids()) {
            if(acceptManager!=null && !acceptManager.acceptsFluid(inputFluid))
                continue;
            ILiquidHeatExchangerManager.HeatExchangeProperty property = manager.getHeatExchangeProperty(inputFluid);
            recipes.add(new FluidHeatingRecipeWrapper(jeiHelpers,inputFluid,property.outputFluid,property.huPerMB));
        }

        return recipes;
    }

}
