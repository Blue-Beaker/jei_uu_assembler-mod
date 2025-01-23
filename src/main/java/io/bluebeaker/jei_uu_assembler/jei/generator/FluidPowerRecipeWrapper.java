package io.bluebeaker.jei_uu_assembler.jei.generator;

import io.bluebeaker.jei_uu_assembler.utils.EnergyUnit;
import io.bluebeaker.jei_uu_assembler.utils.RenderUtils;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;

public class FluidPowerRecipeWrapper implements IRecipeWrapper {

    private final IJeiHelpers jeiHelpers;
    private final FluidStack fluidStack;
    public final long power;
    public final long energy;

    public FluidPowerRecipeWrapper(IJeiHelpers jeiHelpers, Fluid fluid, long power, long energy) {
        this.jeiHelpers = jeiHelpers;
        this.fluidStack = new FluidStack(fluid,1000);
        this.power=power;
        this.energy=energy;
    }

    public FluidStack getFluidStack() {
        return fluidStack;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInput(VanillaTypes.FLUID,fluidStack);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int xPos = recipeWidth - 8;
        int yPos = recipeHeight/2 - minecraft.fontRenderer.FONT_HEIGHT;

        RenderUtils.drawTextAlignedRight(this.power+getPowerUnit()+"/t", xPos, yPos, Color.gray.getRGB());
        yPos += minecraft.fontRenderer.FONT_HEIGHT + 2;

        RenderUtils.drawTextAlignedRight(this.energy+getPowerUnit()+"/mB", xPos, yPos, Color.gray.getRGB());
    }

    public String getPowerUnit(){
        return EnergyUnit.EU.name;
    }
}