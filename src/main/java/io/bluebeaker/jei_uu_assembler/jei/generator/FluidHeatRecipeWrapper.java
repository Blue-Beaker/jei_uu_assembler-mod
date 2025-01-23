package io.bluebeaker.jei_uu_assembler.jei.generator;

import io.bluebeaker.jei_uu_assembler.utils.EnergyUnit;
import mezz.jei.api.IJeiHelpers;
import net.minecraftforge.fluids.Fluid;

public class FluidHeatRecipeWrapper extends FluidPowerRecipeWrapper {
    public FluidHeatRecipeWrapper(IJeiHelpers jeiHelpers, Fluid fluid, long power, long energy) {
        super(jeiHelpers, fluid, power, energy);
    }

    @Override
    public String getPowerUnit() {
        return EnergyUnit.HU.name;
    }
}
