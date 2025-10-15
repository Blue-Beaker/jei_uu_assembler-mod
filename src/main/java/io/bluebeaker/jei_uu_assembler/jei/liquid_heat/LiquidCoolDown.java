package io.bluebeaker.jei_uu_assembler.jei.liquid_heat;

import ic2.api.recipe.Recipes;
import ic2.core.ref.BlockName;
import ic2.core.ref.TeBlock;
import io.bluebeaker.jei_uu_assembler.utils.Utils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;

import java.util.List;

public class LiquidCoolDown extends FluidHeatConversionCategory {

    public static final String UID = "jei_uu_assembler.liquid_heat_exchanger";

    public LiquidCoolDown(IGuiHelper guiHelper) {
        super(guiHelper);
        this.icon = guiHelper.createDrawableIngredient(BlockName.te.getItemStack(TeBlock.liquid_heat_exchanger));
    }

    @Override
    public String getTranslationKey() {
        return Utils.getTranslationKeyFromTeBlock(TeBlock.liquid_heat_exchanger);
    }

    @Override
    public String getUid() {
        return UID;
    }

    public static List<FluidHeatConversionRecipe> getRecipes(IJeiHelpers jeiHelpers) {
        return FluidHeatConversionCategory.getRecipes(jeiHelpers,Recipes.liquidCooldownManager, null);
    }
}
