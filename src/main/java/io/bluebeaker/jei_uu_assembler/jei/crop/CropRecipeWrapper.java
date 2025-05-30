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
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static io.bluebeaker.jei_uu_assembler.jei.crop.CropRecipeCategory.ITEM2_X;
import static io.bluebeaker.jei_uu_assembler.jei.crop.CropRecipeCategory.ITEM2_Y;

public class CropRecipeWrapper implements IRecipeWrapper {

    protected final IJeiHelpers jeiHelpers;
    protected final ItemStack seedBag;
    protected final CropCard card;
    protected ItemStack seedStack;
    protected final List<ItemStack> outputs = new ArrayList<>();
    protected List<Float> chances;

    public CropRecipeWrapper(IJeiHelpers jeiHelpers, CropCard card, ItemStack input, List<ItemStack> outputs, @Nullable List<Float> chances) {
        this.jeiHelpers = jeiHelpers;
        this.seedBag = input;
        this.card=card;
        this.chances=chances;
        this.outputs.addAll(outputs);
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        if(seedStack==null){
            iIngredients.setInput(VanillaTypes.ITEM, seedBag);
        }else {
            ArrayList<ItemStack> inputs = new ArrayList<>();
            inputs.add(seedBag);
            inputs.add(seedStack);
            iIngredients.setInputs(VanillaTypes.ITEM,inputs);
        }
        List<ItemStack> outputList = new ArrayList<>(outputs);
        outputList.add(seedBag);
        iIngredients.setOutputs(VanillaTypes.ITEM,outputList);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        int xPos = recipeWidth/2;
        int yPos = 1;
        RenderUtils.drawTextAlignedMiddle(
                RenderUtils.cutStringToWidth(
                        Utils.localize(card.getUnlocalizedName()),
                        recipeWidth), xPos, yPos, Color.gray.getRGB());
        if(chances!=null){
            for (int i = 0, chancesSize = chances.size(); i < chancesSize; i++) {
                Float chance = chances.get(i);
                if(chance!=1.0F)
                    RenderUtils.drawTextAlignedMiddle(String.format("%.0f%%",chance*100),
                            ITEM2_X +9-outputs.size()*9+i*18, ITEM2_Y -9,Color.gray.getRGB());
            }
        }

    }

    public String getPowerUnit(){
        return EnergyUnit.HU.name;
    }

    public ItemStack getSeedStack() {
        return seedStack;
    }

    public void setSeedStack(ItemStack newStack) {
        this.seedStack = newStack.copy();
    }
}
