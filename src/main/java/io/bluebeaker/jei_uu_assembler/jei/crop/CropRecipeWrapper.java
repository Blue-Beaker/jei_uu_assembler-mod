package io.bluebeaker.jei_uu_assembler.jei.crop;

import ic2.api.crops.CropCard;
import io.bluebeaker.jei_uu_assembler.utils.EnergyUnit;
import io.bluebeaker.jei_uu_assembler.utils.RenderUtils;
import io.bluebeaker.jei_uu_assembler.utils.Utils;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CropRecipeWrapper implements IRecipeWrapper {

    protected final IJeiHelpers jeiHelpers;
    protected final ItemStack inputStack;
    protected final CropCard card;
    protected final List<ItemStack> outputs = new ArrayList<>();

    public CropRecipeWrapper(IJeiHelpers jeiHelpers, CropCard card, ItemStack input, List<ItemStack> outputs) {
        this.jeiHelpers = jeiHelpers;
        this.inputStack = input;
        this.card=card;
        this.outputs.addAll(outputs);
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setInput(VanillaTypes.ITEM, inputStack);
        iIngredients.setOutputs(VanillaTypes.ITEM,outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int xPos = recipeWidth/2;
        int yPos = recipeHeight/2 - minecraft.fontRenderer.FONT_HEIGHT;
        RenderUtils.drawTextAlignedMiddle(Utils.localize(card.getUnlocalizedName()), xPos, yPos, Color.gray.getRGB());
    }

    public String getPowerUnit(){
        return EnergyUnit.HU.name;
    }
}
