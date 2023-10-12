package io.bluebeaker.jei_uu_assembler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file

@Mod(modid = JeiUuAssemblerMod.MODID, name = JeiUuAssemblerMod.NAME, version = JeiUuAssemblerMod.VERSION)
public class JeiUuAssemblerMod
{
    public static final String MODID = "jei_uu_assembler";
    public static final String NAME = "Just Enough UU Assembler";
    public static final String VERSION = "1.0";

    private static Logger logger;

    public JeiUuAssemblerMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }
    public static void logInfo(String info){
        logger.info(info);
    }
}
