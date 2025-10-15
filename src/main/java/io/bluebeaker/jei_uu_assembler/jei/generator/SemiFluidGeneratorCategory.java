package io.bluebeaker.jei_uu_assembler.jei.generator;

import ic2.api.recipe.ISemiFluidFuelManager;
import ic2.api.recipe.Recipes;
import ic2.core.ref.BlockName;
import ic2.core.ref.TeBlock;
import io.bluebeaker.jei_uu_assembler.utils.EnergyUnit;
import io.bluebeaker.jei_uu_assembler.utils.Utils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.List;

public class SemiFluidGeneratorCategory extends FluidPowerRecipeCategory {
    public static final String UID = "jei_uu_assembler.gen_fluid";
    public static final EnergyUnit ENERGY_UNIT = EnergyUnit.EU;
    public SemiFluidGeneratorCategory(IGuiHelper guiHelper) {
        super(guiHelper);
        this.icon = guiHelper.createDrawableIngredient(BlockName.te.getItemStack(TeBlock.semifluid_generator));
    }

    @Override
    public String getTranslationKey() {
        return Utils.getTranslationKeyFromTeBlock(TeBlock.semifluid_generator);
    }

    @Override
    public String getUid() {
        return UID;
    }

    public static List<FluidPowerRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers){
        List<FluidPowerRecipeWrapper> recipes = new ArrayList<>();
        for (Fluid acceptedFluid : Recipes.semiFluidGenerator.getAcceptedFluids()) {
            ISemiFluidFuelManager.FuelProperty fuelProperty = Recipes.semiFluidGenerator.getFuelProperty(acceptedFluid);
            recipes.add(new FluidPowerRecipeWrapper(jeiHelpers,acceptedFluid,fuelProperty.energyPerTick,fuelProperty.energyPerMb));
        }
        return recipes;
    }

}
