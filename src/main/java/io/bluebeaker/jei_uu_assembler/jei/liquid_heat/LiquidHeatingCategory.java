package io.bluebeaker.jei_uu_assembler.jei.liquid_heat;

import ic2.api.recipe.ILiquidAcceptManager;
import ic2.api.recipe.ILiquidHeatExchangerManager;
import io.bluebeaker.jei_uu_assembler.Constants;
import io.bluebeaker.jei_uu_assembler.jei.generic.GenericRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class LiquidHeatingCategory extends GenericRecipeCategory<FluidHeatingRecipeWrapper> {
    protected final IDrawableStatic bgArrow;
    protected final IDrawableAnimated arrow;
    public LiquidHeatingCategory(IGuiHelper guiHelper) {
        super(guiHelper,116,32);
        this.bgArrow = guiHelper.createDrawable(Constants.GUI_0, 0, 17, 48, 10);
        this.arrow = guiHelper.drawableBuilder(Constants.GUI_0, 48, 17, 48, 10).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgArrow.draw(minecraft, 34, 16);
        this.arrow.draw(minecraft, 34, 16);
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
