package io.bluebeaker.jei_uu_assembler.jei.generator;

import ic2.core.init.MainConfig;
import ic2.core.ref.TeBlock;
import ic2.core.util.ConfigUtil;
import io.bluebeaker.jei_uu_assembler.utils.EnergyUnit;
import io.bluebeaker.jei_uu_assembler.utils.Utils;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Collections;
import java.util.List;

public class GeoGeneratorCategory extends FluidPowerRecipeCategory {
    public static final String UID = "jei_uu_assembler.gen_geo";
    public static final EnergyUnit ENERGY_UNIT = EnergyUnit.EU;
    public GeoGeneratorCategory(IGuiHelper guiHelper) {
        super(guiHelper);
    }

    @Override
    public String getTranslationKey() {
        return Utils.getTranslationKeyFromTeBlock(TeBlock.geo_generator);
    }

    @Override
    public String getUid() {
        return UID;
    }

    public static List<FluidPowerRecipeWrapper> getRecipes(IJeiHelpers jeiHelpers){
        float multiplier = ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/geothermal");
        int gain = Math.round(20.0F * multiplier);

        Fluid acceptedFluid = FluidRegistry.LAVA;

        return Collections.singletonList(new FluidPowerRecipeWrapper(jeiHelpers,acceptedFluid,gain,gain/2));
    }

}
