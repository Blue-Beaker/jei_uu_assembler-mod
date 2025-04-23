package io.bluebeaker.jei_uu_assembler.jei.crop;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.core.crop.IC2Crops;
import ic2.core.crop.cropcard.CropRedWheat;
import ic2.core.item.ItemCropSeed;
import ic2.core.ref.ItemName;
import io.bluebeaker.jei_uu_assembler.JeiUuAssemblerConfig;
import io.bluebeaker.jei_uu_assembler.JeiUuAssemblerMod;
import io.bluebeaker.jei_uu_assembler.jei.generic.GenericRecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.*;

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
        long time = System.currentTimeMillis();
        for (CropCard crop : Crops.instance.getCrops()) {
            try {
                if(crop instanceof CropRedWheat){
                    List<ItemStack> output = new ArrayList<>();
                    output.add(new ItemStack(Items.REDSTONE));
                    output.add(new ItemStack(Items.WHEAT));
                    recipes.add(new CropRecipeWrapper(jeiHelpers, crop, ItemCropSeed.generateItemStackFromValues(crop,0,0,0,3),output, null));
                    continue;
                }

                DummyCropTile dummyCropTile = new DummyCropTile(crop);
                dummyCropTile.setCurrentSize(crop.getOptimalHarvestSize(dummyCropTile));
                Map<ItemStack, Float> outputCounter = simulateHarvest(dummyCropTile);

                List<ItemStack> output = new ArrayList<>();
                List<Float> chances = new ArrayList<>();
                for (Map.Entry<ItemStack, Float> entry : outputCounter.entrySet()) {
                    ItemStack stack1 = entry.getKey().copy();
                    output.add(stack1);
                    chances.add(entry.getValue());
                }

                recipes.add(new CropRecipeWrapper(jeiHelpers, crop, ItemCropSeed.generateItemStackFromValues(crop,0,0,0,3),output,chances));
            } catch (Exception e) {
                JeiUuAssemblerMod.getLogger().error("Error when getting seed recipes:",e);
            }
        }

        JeiUuAssemblerMod.getLogger().info("Crop harvest simulation took {}ms", System.currentTimeMillis() - time);
        return recipes;
    }

    private static Map<ItemStack, Float> simulateHarvest(DummyCropTile dummyCropTile) {
        Map<ItemStack,Integer> outputCounter = new HashMap<>();
        for (int i = 0; i < JeiUuAssemblerConfig.cropDropSamples; i++) {
            List<ItemStack> outputs = dummyCropTile.performHarvest();
            for (ItemStack stack : outputs) {
                if(stack==null || stack.isEmpty()) continue;
                boolean found=false;
                for (ItemStack stack1 : outputCounter.keySet()) {
                    if(ItemStack.areItemStacksEqual(stack1,stack)){
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
        Map<ItemStack,Float> chances = new HashMap<>();
        outputCounter.forEach((k,v)->{
            chances.put(k,(float)v/JeiUuAssemblerConfig.cropDropSamples);
        });
        return chances;
    }
}
