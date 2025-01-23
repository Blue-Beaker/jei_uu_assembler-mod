package io.bluebeaker.jei_uu_assembler.jei.liquid_heat;

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

public class FluidHeatingRecipeWrapper implements IRecipeWrapper {

    protected final IJeiHelpers jeiHelpers;
    protected final FluidStack inputStack;
    protected final FluidStack outputStack;
    public final long energy;

    public FluidHeatingRecipeWrapper(IJeiHelpers jeiHelpers, Fluid input, Fluid output, long energy) {
        this.jeiHelpers = jeiHelpers;
        this.inputStack = new FluidStack(input,1000);
        this.outputStack = new FluidStack(output,1000);
        this.energy=energy;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInput(VanillaTypes.FLUID, inputStack);
        iIngredients.setOutput(VanillaTypes.FLUID,outputStack);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int xPos = recipeWidth/2;
        int yPos = recipeHeight/2 - minecraft.fontRenderer.FONT_HEIGHT;
        RenderUtils.drawTextAlignedMiddle(this.energy+getPowerUnit()+"/mB", xPos, yPos, Color.gray.getRGB());
    }

    public String getPowerUnit(){
        return EnergyUnit.HU.name;
    }
}
