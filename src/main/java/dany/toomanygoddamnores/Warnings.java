package dany.toomanygoddamnores;

import java.io.File;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Warnings
{
	public static File configDir;
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void openMainMenu(GuiOpenEvent e)
	{
		if (e.gui instanceof GuiMainMenu && Config.replacements.isEmpty())
		{
			File file = new File(configDir, Refs.MOD_ID + "_data");
			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				}
				catch (Throwable t)
				{
					TooManyDanyOres.logger.warn("Unable to create a data file!");
					TooManyDanyOres.logger.catching(t);
				}
				
				e.gui = new GuiStartupWarning((GuiMainMenu)e.gui);
				
				MinecraftForge.EVENT_BUS.unregister(this);
			}
		}
	}
}