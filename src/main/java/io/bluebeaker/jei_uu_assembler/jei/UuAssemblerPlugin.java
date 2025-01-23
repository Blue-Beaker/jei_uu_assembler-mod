package io.bluebeaker.jei_uu_assembler.jei;

import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import ic2.jeiIntegration.transferhandlers.TransferHandlerBatchCrafter;
import io.bluebeaker.jei_uu_assembler.JeiUuAssemblerMod;
import io.bluebeaker.jei_uu_assembler.jei.generator.FluidHeaterCategory;
import io.bluebeaker.jei_uu_assembler.jei.generator.GeoGeneratorCategory;
import io.bluebeaker.jei_uu_assembler.jei.generator.FluidGeneratorCategory;
import io.bluebeaker.jei_uu_assembler.jei.misc.LiquidCoolDown;
import io.bluebeaker.jei_uu_assembler.jei.misc.LiquidHeatUp;
import io.bluebeaker.jei_uu_assembler.jei.uu.UURecipeCategory;
import io.bluebeaker.jei_uu_assembler.jei.uu.UURecipeMaker;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;

import javax.annotation.Nonnull;


@JEIPlugin
public class UuAssemblerPlugin implements IModPlugin {

  private static IJeiRuntime jeiRuntime = null;
  public static IModRegistry modRegistry;

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    IJeiHelpers jeiHelpers = registry.getJeiHelpers();
    IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
    registry.addRecipeCategories(new UURecipeCategory(guiHelper));

    registry.addRecipeCategories(new GeoGeneratorCategory(guiHelper));
    registry.addRecipeCategories(new FluidGeneratorCategory(guiHelper));
    registry.addRecipeCategories(new FluidHeaterCategory(guiHelper));
    registry.addRecipeCategories(new LiquidHeatUp(guiHelper));
    registry.addRecipeCategories(new LiquidCoolDown(guiHelper));
  }

  @Override
  public void register(IModRegistry registry) {
    modRegistry=registry;
    IJeiHelpers jeiHelpers = registry.getJeiHelpers();

    JeiUuAssemblerMod.logInfo("Started loading UU recipes...");
    registry.addRecipes(UURecipeMaker.getUuRecipe(), UURecipeCategory.UID);
    registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.uu_assembly_bench), UURecipeCategory.UID);
    JeiUuAssemblerMod.logInfo("Loaded UU recipes!");
    registry.getRecipeTransferRegistry().addRecipeTransferHandler(new TransferHandlerBatchCrafter(), UURecipeCategory.UID);

    registry.addRecipes(GeoGeneratorCategory.getRecipes(jeiHelpers),GeoGeneratorCategory.UID);
    registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.geo_generator), GeoGeneratorCategory.UID);

    registry.addRecipes(FluidGeneratorCategory.getRecipes(jeiHelpers), FluidGeneratorCategory.UID);
    registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.semifluid_generator), FluidGeneratorCategory.UID);

    registry.addRecipes(FluidHeaterCategory.getRecipes(jeiHelpers),FluidHeaterCategory.UID);
    registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.fluid_heat_generator), FluidHeaterCategory.UID);

    registry.addRecipes(LiquidHeatUp.getRecipes(jeiHelpers), LiquidHeatUp.UID);
    registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.stirling_kinetic_generator), LiquidHeatUp.UID);

    registry.addRecipes(LiquidCoolDown.getRecipes(jeiHelpers), LiquidCoolDown.UID);
    registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.liquid_heat_exchanger), LiquidCoolDown.UID);

  }

  @Override
  public void onRuntimeAvailable(IJeiRuntime jeiRuntimeIn) {
    UuAssemblerPlugin.jeiRuntime = jeiRuntimeIn;
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