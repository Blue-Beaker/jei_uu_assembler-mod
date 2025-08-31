package io.bluebeaker.jei_uu_assembler.jei.crop;

import ic2.api.crops.BaseSeed;
import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.core.crop.cropcard.CropEating;
import ic2.core.crop.cropcard.CropRedWheat;
import ic2.core.item.ItemCropSeed;
import ic2.core.ref.ItemName;
import io.bluebeaker.jei_uu_assembler.JeiUuAssemblerConfig;
import io.bluebeaker.jei_uu_assembler.JeiUuAssemblerMod;
import io.bluebeaker.jei_uu_assembler.jei.generic.GenericRecipeCategory;
import io.bluebeaker.jei_uu_assembler.mixin.AccessorIC2Crops;
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

    private static final int WIDTH = 160;
    private static final int HEIGHT = 60;
    public static final int ITEM1_X = WIDTH/2-9;
    public static final int ITEM2_X = WIDTH/2;
    public static final int ITEM1_Y = 11;
    public static final int ITEM2_Y = HEIGHT-20;

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
        this.addItemSlot(guiItemStackGroup,0, ITEM1_X, ITEM1_Y);
        guiItemStackGroup.set(0, wrapper.seedBag);
        // If there is a seed for the crop, add it
        if(wrapper.seedStack!=null){
            this.addItemSlot(guiItemStackGroup,1,ITEM1_X-36, ITEM1_Y);
            guiItemStackGroup.set(1, wrapper.seedStack);
        }
        List<ItemStack> outputs = wrapper.outputs;

        for (int i = 0, outputsSize = outputs.size(); i < outputsSize; i++) {
            ItemStack output = outputs.get(i);
            this.addItemSlot(guiItemStackGroup,i+2, ITEM2_X-outputsSize*9+i*18, ITEM2_Y);
            guiItemStackGroup.set(i+2, output);
        }

    }

    public static List<CropRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers) {
        List<CropRecipeWrapper> recipes = new ArrayList<>();
        long time = System.currentTimeMillis();
        Map<CropCard, ItemStack> seedMap = getSeedMap();

        for (CropCard crop : Crops.instance.getCrops()) {
            try {
                // Use dummy crop tile to simulate drops
                DummyCropTile dummyCropTile = new DummyCropTile(crop);
                int growthPoints=0;
                for (int i=1;i<=crop.getMaxSize();i++){
                    dummyCropTile.setCurrentSize(i);
                    if(crop.canBeHarvested(dummyCropTile)){
                        addRecipeForCrop(jeiHelpers, crop, dummyCropTile, seedMap, recipes,growthPoints);
                    }
                    if(i>=crop.getSizeAfterHarvest(dummyCropTile)){
                        //Special case for Eating Plant
                        if(crop instanceof CropEating){
                            growthPoints+=Workarounds.getGrowthDuration(crop);
                            continue;
                        }
                        growthPoints+=crop.getGrowthDuration(dummyCropTile);
                    }
                }
            } catch (Exception e) {
                JeiUuAssemblerMod.getLogger().error("Error when getting seed recipes:",e);
            }
        }

        JeiUuAssemblerMod.getLogger().info("Crop harvest simulation took {}ms", System.currentTimeMillis() - time);
        return recipes;
    }

    private static void addRecipeForCrop(IJeiHelpers jeiHelpers, CropCard crop, DummyCropTile dummyCropTile, Map<CropCard, ItemStack> seedMap, List<CropRecipeWrapper> recipes,int growthPoints) {
        Map<ItemStack, Float> outputCounter;

        // Special case for red wheat
        if(crop instanceof CropRedWheat){
            outputCounter = new HashMap<>();
            outputCounter.put(new ItemStack(Items.REDSTONE),1.0f);
            outputCounter.put(new ItemStack(Items.WHEAT),1.0f);
        }else{
            outputCounter = simulateHarvest(dummyCropTile);
        }

        List<ItemStack> output = new ArrayList<>();
        List<Float> chances = new ArrayList<>();

        for (Map.Entry<ItemStack, Float> entry : outputCounter.entrySet()) {
            ItemStack stack1 = entry.getKey().copy();
            output.add(stack1);
            chances.add(entry.getValue());
        }
        // Add seed if there is one
        ItemStack seedStack = seedMap.get(crop);
        ItemStack seedBag;

        if(seedStack!=null){
            BaseSeed baseSeed = Crops.instance.getBaseSeed(seedStack);
            seedBag = ItemCropSeed.generateItemStackFromValues(crop, baseSeed.statGrowth, baseSeed.statGain, baseSeed.statResistance, 4);
        }else {
            seedBag = ItemCropSeed.generateItemStackFromValues(crop, 0, 0, 0, 3);
        }

        CropRecipeWrapper recipe = new CropRecipeWrapper(jeiHelpers, crop, seedBag, output, chances, dummyCropTile.getCurrentSize(),growthPoints);

        if(seedStack!=null){
           recipe.setSeedStack(seedStack);
        }

        recipes.add(recipe);
    }

    public static Map<CropCard, ItemStack> getSeedMap(){
        Map<CropCard,ItemStack> map = new HashMap<>();
        Map<ItemStack, BaseSeed> baseSeeds = ((AccessorIC2Crops) Crops.instance).getBaseSeeds();
        for (Map.Entry<ItemStack, BaseSeed> entry : baseSeeds.entrySet()) {
            map.put(entry.getValue().crop, entry.getKey());
        }
        return map;
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
