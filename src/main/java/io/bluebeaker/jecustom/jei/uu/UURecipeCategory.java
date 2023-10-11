package io.bluebeaker.jecustom.jei.uu;

import io.bluebeaker.jecustom.jei.JECustomRecipeCategory;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;

public class UURecipeCategory extends JECustomRecipeCategory<UURecipeWrapper> {

    public UURecipeCategory(IDrawable background, String unlocalizedName) {
        super(background, unlocalizedName);
        //TODO Auto-generated constructor stub
    }

    @Override
    public String getUid() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUid'");
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, UURecipeWrapper recipeWrapper, IIngredients ingredients) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRecipe'");
    }
    
}
