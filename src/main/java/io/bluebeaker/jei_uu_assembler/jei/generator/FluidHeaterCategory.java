package io.bluebeaker.jei_uu_assembler.jei.generator;

import ic2.api.recipe.IFluidHeatManager;
import ic2.api.recipe.Recipes;
import ic2.core.ref.BlockName;
import ic2.core.ref.TeBlock;
import io.bluebeaker.jei_uu_assembler.utils.Utils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.List;

public class FluidHeaterCategory extends FluidPowerRecipeCategory {
    public static final String UID = "jei_uu_assembler.heat_fluid";

    public FluidHeaterCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.icon = guiHelper.createDrawableIngredient(BlockName.te.getItemStack(TeBlock.fluid_heat_generator));
    }

    @Override
    public String getTranslationKey() {
        return Utils.getTranslationKeyFromTeBlock(TeBlock.fluid_heat_generator);
    }

    @Override
    public String getUid() {
        return UID;
    }

    public static List<FluidPowerRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers){
        List<FluidPowerRecipeWrapper> recipes = new ArrayList<>();
        for (Fluid acceptedFluid : Recipes.fluidHeatGenerator.getAcceptedFluids()) {
            IFluidHeatManager.BurnProperty burnProperty = Recipes.fluidHeatGenerator.getBurnProperty(acceptedFluid);
            recipes.add(new FluidHeaterRecipeWrapper(jeiHelpers,acceptedFluid,burnProperty.heat, burnProperty.amount));
        }
        return recipes;
    }
}
