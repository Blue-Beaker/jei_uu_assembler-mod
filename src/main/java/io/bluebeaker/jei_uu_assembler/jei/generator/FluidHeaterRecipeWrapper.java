package io.bluebeaker.jei_uu_assembler.jei.generator;

import io.bluebeaker.jei_uu_assembler.utils.EnergyUnit;
import mezz.jei.api.IJeiHelpers;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidHeaterRecipeWrapper extends FluidPowerRecipeWrapper {
    public final int amount;
    public FluidHeaterRecipeWrapper(IJeiHelpers jeiHelpers, Fluid fluid, long heat, int amount) {
        super(jeiHelpers, new FluidStack(fluid,amount), heat, heat * 20L/amount);
        this.amount=amount;
    }

    @Override
    public String getPowerUnit() {
        return EnergyUnit.HU.name;
    }
}
