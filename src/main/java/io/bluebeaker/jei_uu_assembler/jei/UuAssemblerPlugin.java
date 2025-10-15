package io.bluebeaker.jei_uu_assembler.jei;

import ic2.core.block.heatgenerator.gui.GuiFluidHeatGenerator;
import ic2.core.block.kineticgenerator.gui.GuiStirlingKineticGenerator;
import ic2.core.block.machine.gui.GuiFermenter;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.BlockName;
import ic2.core.ref.ItemName;
import ic2.core.ref.TeBlock;
import ic2.jeiIntegration.transferhandlers.TransferHandlerBatchCrafter;
import io.bluebeaker.jei_uu_assembler.JeiUuAssemblerConfig;
import io.bluebeaker.jei_uu_assembler.JeiUuAssemblerMod;
import io.bluebeaker.jei_uu_assembler.jei.crop.CropRecipeCategory;
import io.bluebeaker.jei_uu_assembler.jei.crop.CropSubtypeInterpreter;
import io.bluebeaker.jei_uu_assembler.jei.generator.*;
import io.bluebeaker.jei_uu_assembler.jei.liquid_heat.*;
import io.bluebeaker.jei_uu_assembler.jei.massfab.MassFabRecipeCategory;
import io.bluebeaker.jei_uu_assembler.jei.uu.UURecipeCategory;
import io.bluebeaker.jei_uu_assembler.jei.uu.UURecipeMaker;
import io.bluebeaker.jei_uu_assembler.utils.IC2Utils;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;


@JEIPlugin
public class UuAssemblerPlugin implements IModPlugin {

  private static IJeiRuntime jeiRuntime = null;
  public static IModRegistry modRegistry;

  @Override
  public void registerCategories(IRecipeCategoryRegistration registry) {
    IJeiHelpers jeiHelpers = registry.getJeiHelpers();
    IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
    if(JeiUuAssemblerConfig.uu_assembler)
      registry.addRecipeCategories(new UURecipeCategory(guiHelper));

    if(JeiUuAssemblerConfig.generator)
      registry.addRecipeCategories(new GeneratorCategory(guiHelper));
    if(JeiUuAssemblerConfig.solid_heater)
      registry.addRecipeCategories(new SolidHeaterCategory(guiHelper));
    if(JeiUuAssemblerConfig.geo_generator)
      registry.addRecipeCategories(new GeoGeneratorCategory(guiHelper));
    if(JeiUuAssemblerConfig.semifluid_generator)
      registry.addRecipeCategories(new SemiFluidGeneratorCategory(guiHelper));
    if(JeiUuAssemblerConfig.fluid_heater)
      registry.addRecipeCategories(new FluidHeaterCategory(guiHelper));
    if(JeiUuAssemblerConfig.stirling_kinetic_generator)
      registry.addRecipeCategories(new LiquidHeatUp(guiHelper));
    if(JeiUuAssemblerConfig.liquid_heat_exchanger)
      registry.addRecipeCategories(new LiquidCoolDown(guiHelper));
    if(JeiUuAssemblerConfig.steam_boiler)
      registry.addRecipeCategories(new SteamBoilerCategory(guiHelper));
    if(JeiUuAssemblerConfig.steam_turbine)
      registry.addRecipeCategories(new SteamTurbineCategory(guiHelper));
    if(JeiUuAssemblerConfig.fermenter)
      registry.addRecipeCategories(new FermenterCategory(guiHelper));
    if(JeiUuAssemblerConfig.mass_fabricator)
      registry.addRecipeCategories(new MassFabRecipeCategory(guiHelper));

    if(JeiUuAssemblerConfig.crops)
      registry.addRecipeCategories(new CropRecipeCategory(guiHelper));
  }

  @Override
  public void register(IModRegistry registry) {
    modRegistry=registry;
    IJeiHelpers jeiHelpers = registry.getJeiHelpers();

    JeiUuAssemblerMod.logInfo("Started loading recipes...");
    if(JeiUuAssemblerConfig.uu_assembler){
      registry.addRecipes(UURecipeMaker.getUuRecipe(), UURecipeCategory.UID);
      registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.uu_assembly_bench), UURecipeCategory.UID);
      registry.getRecipeTransferRegistry().addRecipeTransferHandler(new TransferHandlerBatchCrafter(), UURecipeCategory.UID);
    }
    if(JeiUuAssemblerConfig.generator || JeiUuAssemblerConfig.solid_heater){
      Int2ObjectMap<List<ItemStack>> fuels = IC2Utils.getFuelValuesGenerator(registry);
      if(JeiUuAssemblerConfig.generator){
        registry.addRecipes(GeneratorCategory.getRecipes(fuels),GeneratorCategory.UID);
        registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.generator), GeneratorCategory.UID);
      }
      if(JeiUuAssemblerConfig.solid_heater){
        registry.addRecipes(SolidHeaterCategory.getRecipes(fuels),SolidHeaterCategory.UID);
        registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.solid_heat_generator), SolidHeaterCategory.UID);
      }
    }
    if (JeiUuAssemblerConfig.geo_generator) {
      registry.addRecipes(GeoGeneratorCategory.getRecipes(jeiHelpers),GeoGeneratorCategory.UID);
      registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.geo_generator), GeoGeneratorCategory.UID);
    }
    if (JeiUuAssemblerConfig.semifluid_generator) {
      registry.addRecipes(SemiFluidGeneratorCategory.getRecipes(jeiHelpers), SemiFluidGeneratorCategory.UID);
      registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.semifluid_generator), SemiFluidGeneratorCategory.UID);
    }
    if (JeiUuAssemblerConfig.fluid_heater) {
      registry.addRecipes(FluidHeaterCategory.getRecipes(jeiHelpers),FluidHeaterCategory.UID);
      registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.fluid_heat_generator), FluidHeaterCategory.UID);
      registry.addRecipeClickArea(GuiFluidHeatGenerator.class,32,28,25,15,FluidHeaterCategory.UID);
    }
    if (JeiUuAssemblerConfig.stirling_kinetic_generator) {
      registry.addRecipes(LiquidHeatUp.getRecipes(jeiHelpers), LiquidHeatUp.UID);
      registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.stirling_kinetic_generator), LiquidHeatUp.UID);
    }
    if (JeiUuAssemblerConfig.liquid_heat_exchanger) {
      registry.addRecipes(LiquidCoolDown.getRecipes(jeiHelpers), LiquidCoolDown.UID);
      registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.liquid_heat_exchanger), LiquidCoolDown.UID);
      registry.addRecipeCatalyst(ItemName.crafting.getItemStack(CraftingItemType.heat_conductor), LiquidCoolDown.UID);
    }
    if(JeiUuAssemblerConfig.steam_boiler){
      registry.addRecipes(SteamBoilerCategory.getRecipes(jeiHelpers), SteamBoilerCategory.UID);
      registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.steam_generator), SteamBoilerCategory.UID);
    }
    if(JeiUuAssemblerConfig.steam_turbine){
      registry.addRecipes(SteamTurbineCategory.getRecipes(jeiHelpers), SteamTurbineCategory.UID);
      registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.steam_kinetic_generator), SteamTurbineCategory.UID);
      registry.addRecipeCatalyst(ItemName.crafting.getItemStack(CraftingItemType.steam_turbine), SteamTurbineCategory.UID);
    }
    if(JeiUuAssemblerConfig.fermenter){
      registry.addRecipes(FermenterCategory.getRecipes(jeiHelpers), FermenterCategory.UID);
      registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.fermenter), FermenterCategory.UID);
      registry.addRecipeClickArea(GuiFermenter.class,83,25,38,20,FermenterCategory.UID);
    }
    if(JeiUuAssemblerConfig.mass_fabricator) {
      registry.addRecipes(MassFabRecipeCategory.getRecipes(jeiHelpers), MassFabRecipeCategory.UID);
      registry.addRecipeCatalyst(BlockName.te.getItemStack(TeBlock.mass_fabricator), MassFabRecipeCategory.UID);
    }

    if(JeiUuAssemblerConfig.crops){
      registry.addRecipes(CropRecipeCategory.getRecipes(jeiHelpers),CropRecipeCategory.UID);
      registry.addRecipeCatalyst(ItemName.crop_stick.getItemStack(), CropRecipeCategory.UID);
    }
    JeiUuAssemblerMod.logInfo("Loaded all recipes!");
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
  public void registerSubtypes(ISubtypeRegistry subtypeRegistry) {
    if(JeiUuAssemblerConfig.crops) {
      subtypeRegistry.registerSubtypeInterpreter(ItemName.crop_seed_bag.getInstance(), new CropSubtypeInterpreter());
    }
    IModPlugin.super.registerSubtypes(subtypeRegistry);
  }

  @Override
  public void registerIngredients(IModIngredientRegistration ingredientRegistration) {
  }

}