package io.bluebeaker.jecustom;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file

@Mod(modid = JecustomMod.MODID, name = JecustomMod.NAME, version = JecustomMod.VERSION)
public class JecustomMod
{
    public static final String MODID = "jecustom";
    public static final String NAME = "Just Enough Custom";
    public static final String VERSION = "1.1";

    private static Logger logger;

    public JecustomMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
