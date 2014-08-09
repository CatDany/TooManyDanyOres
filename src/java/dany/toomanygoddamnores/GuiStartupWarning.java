package dany.toomanygoddamnores;

import java.awt.Desktop;
import java.net.URL;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

public class GuiStartupWarning extends GuiScreen
{
	private final GuiMainMenu gui;
	private GuiButton buttonExamples;
	private GuiButton buttonOk;
	
	public GuiStartupWarning(GuiMainMenu gui)
	{
		this.gui = gui;
	}
	
	@Override
	public void initGui()
	{
		buttonList.clear();
		
		this.buttonExamples = new GuiButton(0, this.width / 2 - 175, this.height - 24, 350, 20, "Config Examples");
		this.buttonOk = new GuiButton(1, this.width / 2 - 175, this.height - 48, 350, 20, "OK");
		
		buttonList.add(buttonExamples);
		buttonList.add(buttonOk);
	}
	
	@Override
	public void drawScreen(int smth, int smthElse, float wowIDunnoWhatIsFloat)
	{
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, "WARNING!", this.width / 2, 84, 0xff0000);
		drawCenteredString(fontRendererObj, "You're using Dany's TooManyGodDamnOres Mod.", this.width / 2, 94, 0xffffff);
		drawCenteredString(fontRendererObj, "But you didn't set up the configuration file.", this.width / 2, 106, 0xffffff);
		drawCenteredString(fontRendererObj, "Do not report any issues!", this.width / 2, 132, 0xffffff);
		
		super.drawScreen(smth, smthElse, wowIDunnoWhatIsFloat);
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == 0)
		{
			try
			{
				URL url = new URL(Refs.CONFIG_EXAMPLES);
				Desktop.getDesktop().browse(url.toURI());
			}
			catch (Throwable t)
			{
				TooManyGodDamnOres.logger.warn("Unable to open website! Try yourself: " + Refs.CONFIG_EXAMPLES);
				buttonExamples.displayString = "<ERROR>";
				buttonExamples.enabled = false;
			}
		}
		else if (button.id == 1)
		{
			mc.displayGuiScreen(gui);
		}
	}
}