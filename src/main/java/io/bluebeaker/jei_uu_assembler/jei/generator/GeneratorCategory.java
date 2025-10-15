package io.bluebeaker.jei_uu_assembler.jei.generator;

import ic2.core.init.MainConfig;
import ic2.core.ref.BlockName;
import ic2.core.ref.TeBlock;
import ic2.core.util.ConfigUtil;
import io.bluebeaker.jei_uu_assembler.jei.generic.FuelRecipeWrapper;
import io.bluebeaker.jei_uu_assembler.utils.Utils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeneratorCategory extends BaseSolidGeneratorCategory<GeneratorCategory.Wrapper> {
    public static final String UID = "jei_uu_assembler.generator";

    public GeneratorCategory(IGuiHelper guiHelper) {
        super(guiHelper,116,32);
        this.icon = guiHelper.createDrawableIngredient(BlockName.te.getItemStack(TeBlock.generator));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, Wrapper wrapper, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        this.addItemSlot(guiItemStackGroup,0,8,GUI_HEIGHT/2);
        guiItemStackGroup.set(iIngredients);
    }

    @Override
    public String getTranslationKey() {
        return Utils.getTranslationKeyFromTeBlock(TeBlock.generator);
    }

    @Override
    public String getUid() {
        return UID;
    }

    public static List<Wrapper> getRecipes(List<Map.Entry<Integer,List<ItemStack>>> fuels){
        List<Wrapper> recipes = new ArrayList<>();
        for (Map.Entry<Integer, List<ItemStack>> fuel : fuels) {
            recipes.add(new Wrapper(fuel.getValue(),fuel.getKey()));
        }
        return recipes;
    }

    public static class Wrapper extends FuelRecipeWrapper {
        public static int Generation = Math.round(10.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/generator"));

        public Wrapper(List<ItemStack> input, int duration) {
            super(input, Generation, duration);
        }
    }
}
