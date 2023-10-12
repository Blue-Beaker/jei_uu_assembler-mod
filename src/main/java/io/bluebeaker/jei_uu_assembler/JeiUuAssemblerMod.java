package io.bluebeaker.jei_uu_assembler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file

@Mod(modid = JeiUuAssemblerMod.MODID, name = JeiUuAssemblerMod.NAME, version = JeiUuAssemblerMod.VERSION)
public class JeiUuAssemblerMod
{
    public static final String MODID = "jei_uu_assembler";
    public static final String NAME = "Just Enough Custom";
    public static final String VERSION = "1.1";

    private static Logger logger;

    public JeiUuAssemblerMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
