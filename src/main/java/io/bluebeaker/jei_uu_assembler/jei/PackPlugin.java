package io.bluebeaker.jei_uu_assembler.jei;

import javax.annotation.Nonnull;

import ic2.core.ref.BlockName;
import ic2.core.ref.TeBlock;
import io.bluebeaker.jei_uu_assembler.JeiUuAssemblerMod;
import io.bluebeaker.jei_uu_assembler.jei.uu.UURecipeCategory;
import io.bluebeaker.jei_uu_assembler.jei.uu.UURecipeMaker;
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
  public static IModRegistry modRegistry;
  @Override
  public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    IJeiHelpers jeiHelpers = registry.getJeiHelpers();
    IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
    registry.addRecipeCategories(new UURecipeCategory(guiHelper));
  }

  @Override
  public void register(IModRegistry registry) {
    modRegistry=registry;
    JeiUuAssemblerMod.logInfo("Started loading UU recipes...");
    registry.addRecipes(UURecipeMaker.getUuRecipe(), UURecipeCategory.UID);
		registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.uu_assembly_bench), UURecipeCategory.UID);
    JeiUuAssemblerMod.logInfo("Loaded UU recipes!");
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