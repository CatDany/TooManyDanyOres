package dany.toomanygoddamnores;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

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
		registerEventHandler(new Handler(), true, false);
		registerEventHandler(new Warnings(), true, false);
		
		logger.info("Init is done");
	}
	
	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent e)
	{
		e.registerServerCommand(new CommandItemInfo());
		
		logger.info("ServerStarting is done");
	}
	
	private void registerEventHandler(Object instance, boolean forge, boolean fml)
	{
		if (forge)
		{
			MinecraftForge.EVENT_BUS.register(instance);
		}
		if (fml)
		{
			FMLCommonHandler.instance().bus().register(instance);
		}
	}
}