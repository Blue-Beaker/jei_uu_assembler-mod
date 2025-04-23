package io.bluebeaker.jei_uu_assembler.jei.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.core.item.ItemCropSeed;
import ic2.core.ref.ItemName;
import io.bluebeaker.jei_uu_assembler.JeiUuAssemblerMod;
import io.bluebeaker.jei_uu_assembler.jei.generic.GenericRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CropRecipeCategory extends GenericRecipeCategory<CropRecipeWrapper> {

    public static final String UID = "jei_uu_assembler.crop";;
    public static final ItemStack uu = ItemName.crop_stick.getItemStack();

    private static final int WIDTH = 116;
    private static final int HEIGHT = 54;
    public static final int ITEM_X = 8;
    public static final int ITEM_Y = 1;
    public static final int LINE2_Y = 34;

    public CropRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper,WIDTH,HEIGHT);
    }

    @Override
    public String getTranslationKey() {
        return "gui.jei_uu_assembler.category.crop";
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CropRecipeWrapper wrapper, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        this.addItemSlot(guiItemStackGroup,0,ITEM_X, ITEM_Y);
        guiItemStackGroup.set(0, wrapper.inputStack);
        List<ItemStack> outputs = wrapper.outputs;
        for (int i = 0, outputsSize = outputs.size(); i < outputsSize; i++) {
            ItemStack output = outputs.get(i);
            this.addItemSlot(guiItemStackGroup,i+1, ITEM_X +i*18, LINE2_Y);
            guiItemStackGroup.set(i+1, output);
        }

    }

    public static List<CropRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers) {
        List<CropRecipeWrapper> recipes = new ArrayList<>();
        for (CropCard crop : Crops.instance.getCrops()) {
            try {
                DummyCropTile dummyCropTile = new DummyCropTile(crop);
                dummyCropTile.setCurrentSize(crop.getOptimalHarvestSize(dummyCropTile));

                Map<ItemStack,Integer> outputCounter = new HashMap<>();
                for (int i = 0; i < 100; i++) {
                    List<ItemStack> outputs = dummyCropTile.performHarvest();
                    for (ItemStack stack : outputs) {
                        boolean found=false;
                        for (ItemStack stack1 : outputCounter.keySet()) {
                            if(stack1.isItemEqual(stack)){
                                found=true;
                                outputCounter.put(stack1,outputCounter.get(stack1)+1);
                                break;
                            }
                        }
                        if(!found){
                            outputCounter.put(stack,1);
                        }
                    }
                }

                List<ItemStack> output = new ArrayList<>();
                List<Integer> chances = new ArrayList<>();
                for (ItemStack stack : outputCounter.keySet()) {
                    ItemStack stack1 = stack.copy();
                    output.add(stack1);
                    chances.add(outputCounter.get(stack));
                }

                recipes.add(new CropRecipeWrapper(jeiHelpers, crop, ItemCropSeed.generateItemStackFromValues(crop,0,0,0,3),output,chances));
            } catch (Exception e) {
                JeiUuAssemblerMod.getLogger().error("Error when getting seed recipes:",e);
            }
        }

        return recipes;
    }
}
