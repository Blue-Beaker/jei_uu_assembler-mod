package io.bluebeaker.jei_uu_assembler.jei.generator;

import ic2.core.init.MainConfig;
import ic2.core.ref.TeBlock;
import ic2.core.util.ConfigUtil;
import io.bluebeaker.jei_uu_assembler.utils.IC2Drawables;
import io.bluebeaker.jei_uu_assembler.jei.generic.FuelRecipeWrapper;
import io.bluebeaker.jei_uu_assembler.jei.generic.GenericRecipeCategory;
import io.bluebeaker.jei_uu_assembler.utils.RenderUtils;
import io.bluebeaker.jei_uu_assembler.utils.Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GeneratorCategory extends GenericRecipeCategory<GeneratorCategory.Wrapper> {
    public static final String UID = "jei_uu_assembler.generator";
    protected final IDrawableStatic bgHeatBar;
    protected final IDrawableAnimated heatBar;

    public GeneratorCategory(IGuiHelper guiHelper) {
        super(guiHelper,116,32);
        this.bgHeatBar = guiHelper.createDrawable(IC2Drawables.GUI_PATH, 97, 80, 14, 14);
        this.heatBar = guiHelper.drawableBuilder(IC2Drawables.GUI_PATH, 112, 80, 14, 14).buildAnimated(200, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, Wrapper wrapper, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();
        this.addItemSlot(guiItemStackGroup,0,8,GUI_HEIGHT/2);
        guiItemStackGroup.set(iIngredients);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        this.bgHeatBar.draw(minecraft, 10, GUI_HEIGHT/2-16);
        this.heatBar.draw(minecraft, 10, GUI_HEIGHT/2-16);
    }

    @Override
    public String getTranslationKey() {
        return Utils.getTranslationKeyFromTeBlock(TeBlock.generator);
    }

    @Override
    public String getUid() {
        return UID;
    }

    public static List<Wrapper> getRecipes(Int2ObjectMap<List<ItemStack>> fuels){
        List<Wrapper> recipes = new ArrayList<>(fuels.size());

        fuels.int2ObjectEntrySet().stream().sorted(Comparator.comparingInt(Int2ObjectMap.Entry::getIntKey)).forEach((entry) -> recipes.add(new Wrapper(entry.getValue(), entry.getIntKey())));
        return recipes;
    }

    public static class Wrapper extends FuelRecipeWrapper {
        public static int Generation = Math.round(10.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/generator"));

        public Wrapper(List<ItemStack> input, int duration) {
            super(input, Generation, duration);
        }
    }
}
