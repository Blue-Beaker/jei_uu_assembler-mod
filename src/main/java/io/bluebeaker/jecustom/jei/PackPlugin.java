package io.bluebeaker.jecustom.jei;

import javax.annotation.Nonnull;

import io.bluebeaker.jecustom.jei.uu.UURecipeCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;


@JEIPlugin
public class PackPlugin implements IModPlugin {

  private static IJeiRuntime jeiRuntime = null;
  static IModRegistry modRegistry;
  @Override
  public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    IJeiHelpers jeiHelpers = registry.getJeiHelpers();
    IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
    registry.addRecipeCategories(new UURecipeCategory(guiHelper.createBlankDrawable(16, 16),"category.uuassemblybench"));
  }

  @Override
  public void register(IModRegistry registry) {
  }

  @Override
  public void onRuntimeAvailable(IJeiRuntime jeiRuntimeIn) {
    PackPlugin.jeiRuntime = jeiRuntimeIn;
  }

  public static void setFilterText(@Nonnull String filterText) {
    jeiRuntime.getIngredientFilter().setFilterText(filterText);
  }

  public static String getFilterText() {
    return jeiRuntime.getIngredientFilter().getFilterText();
  }

  public static void showCraftingRecipes() {
  }

  @Override
  public void registerIngredients(IModIngredientRegistration ingredientRegistration) {
  }

}