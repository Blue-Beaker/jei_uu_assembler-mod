package io.bluebeaker.jei_uu_assembler.jei.misc;

import ic2.api.recipe.Recipes;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;

import java.util.List;

public class LiquidCoolDown extends LiquidHeatingCategory {

    public static final String UID = "jei_uu_assembler.liquid_cooldown";

    public LiquidCoolDown(IGuiHelper guiHelper) {
        super(guiHelper);
    }

    @Override
    public String getTranslationKey() {
        return "gui.jei_uu_assembler.category.liquid_cooldown";
    }

    @Override
    public String getUid() {
        return UID;
    }

    public static List<FluidHeatingRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers) {
        return LiquidHeatingCategory.getRecipes(jeiHelpers,Recipes.liquidCooldownManager, null);
    }
}
