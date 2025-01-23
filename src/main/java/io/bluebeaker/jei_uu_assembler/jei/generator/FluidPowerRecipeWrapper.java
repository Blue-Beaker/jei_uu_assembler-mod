package io.bluebeaker.jei_uu_assembler.jei.generator;

import io.bluebeaker.jei_uu_assembler.utils.EnergyUnit;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

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
        int xPos = 50;
        int yPos = 16;

        minecraft.fontRenderer.drawString(this.power+getPowerUnit()+"/t", xPos, yPos, 0xff000000);
        yPos += minecraft.fontRenderer.FONT_HEIGHT + 2;
        minecraft.fontRenderer.drawString(this.energy+getPowerUnit()+"/mB", xPos, yPos, 0xff000000);
    }

    public String getPowerUnit(){
        return EnergyUnit.EU.name;
    }
}