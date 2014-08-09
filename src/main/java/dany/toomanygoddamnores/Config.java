package dany.toomanygoddamnores;

import java.io.File;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public class Config
{
	private static Configuration config;
	public static ConfigCategory replacements;
	public static ConfigCategory exclusions;
	
	public static void initConfiguration(File file)
	{
		config = new Configuration(file);
	}
	
	public static void reloadConfiguration()
	{
		config.load();
		
		replacements = config.getCategory("replacements");
		exclusions = config.getCategory("exclusions");
		
		config.save();
	}
	
	public static Configuration config()
	{
		return config;
	}
}