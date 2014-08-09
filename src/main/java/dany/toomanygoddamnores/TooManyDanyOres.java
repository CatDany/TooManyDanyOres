package dany.toomanygoddamnores;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod
(
	modid = Refs.MOD_ID,
	name = Refs.MOD_NAME,
	version = Refs.VERSION,
	dependencies = Refs.DEPENDENCIES,
	acceptedMinecraftVersions = "1.7.10"
)
public class TooManyDanyOres
{
	@Instance(Refs.MOD_ID)
	public static TooManyDanyOres instance;
	
	public static Logger logger;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		logger = e.getModLog();
		
		Config.initConfiguration(e.getSuggestedConfigurationFile());
		Config.reloadConfiguration();
		
		Warnings.configDir = e.getModConfigurationDirectory();
		
		logger.info("PreInit is done");
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent e)
	{
		MinecraftForge.EVENT_BUS.register(new Handler());
		MinecraftForge.EVENT_BUS.register(new Warnings());
		logger.info("Init is done");
	}
}