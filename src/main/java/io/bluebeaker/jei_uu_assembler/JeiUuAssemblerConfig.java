package io.bluebeaker.jei_uu_assembler;

import net.minecraftforge.common.config.Config;

@Config(modid = JeiUuAssemblerMod.MODID)
public class JeiUuAssemblerConfig {
    @Config.RequiresMcRestart
    public static boolean uu_assembler = true;
    @Config.RequiresMcRestart
    public static boolean mass_fabricator = true;
    @Config.RequiresMcRestart
    public static boolean geo_generator = true;
    @Config.RequiresMcRestart
    public static boolean semifluid_generator = true;
    @Config.RequiresMcRestart
    public static boolean fluid_heater = true;
    @Config.RequiresMcRestart
    public static boolean liquid_heat_exchanger = true;
    @Config.RequiresMcRestart
    public static boolean stirling_kinetic_generator = true;
    @Config.RequiresMcRestart
    public static boolean steam_boiler = true;
    @Config.RequiresMcRestart
    public static boolean steam_turbine = true;
    @Config.RequiresMcRestart
    public static boolean fermenter = true;
    @Config.RequiresMcRestart
    public static boolean crops = true;
    @Config.RangeInt(min = 1)
    public static int cropDropSamples = 1000;
}
