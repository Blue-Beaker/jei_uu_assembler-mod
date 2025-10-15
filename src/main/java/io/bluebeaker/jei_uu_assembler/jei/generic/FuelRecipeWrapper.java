package io.bluebeaker.jei_uu_assembler.jei.generic;

import io.bluebeaker.jei_uu_assembler.utils.EnergyUnit;
import io.bluebeaker.jei_uu_assembler.utils.RenderUtils;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class FuelRecipeWrapper implements IRecipeWrapper {
    public final int power;
    public final int duration;
    protected final List<ItemStack> input = new ArrayList<>();

    public FuelRecipeWrapper(List<ItemStack> inputs, int power, int duration) {
        this.input.addAll(inputs);
        this.power = power;
        this.duration = duration;
    }
    public FuelRecipeWrapper(ItemStack input, int power, int duration) {
        this(Collections.singletonList(input),power,duration);
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(input));
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int xPos = recipeWidth / 2;
        int yPos = recipeHeight / 2 - minecraft.fontRenderer.FONT_HEIGHT;

        RenderUtils.drawTextAlignedMiddle(this.power +this.getPowerUnit()+ "/t", xPos, yPos, Color.gray.getRGB());
        yPos += minecraft.fontRenderer.FONT_HEIGHT + 2;
        RenderUtils.drawTextAlignedMiddle(this.duration*this.power+this.getPowerUnit(), xPos, yPos, Color.gray.getRGB());
    }
    public String getPowerUnit() {
        return EnergyUnit.EU.name;
    }
}
