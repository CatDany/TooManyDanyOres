package dany.toomanygoddamnores;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import net.minecraftforge.oredict.OreDictionary;

public class CommandItemInfo extends CommandBase
{
	public static final String COMMAND_NAME = "iteminfo";
	public static final String COMMAND_USAGE = "/iteminfo [id]";
	
	@Override
	public String getCommandName()
	{
		return COMMAND_NAME;
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return COMMAND_USAGE;
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (sender instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)sender;
			ItemStack stack = player.inventory.getCurrentItem();
			if (args.length > 0)
			{
				stack = new ItemStack(Item.getItemById(Integer.parseInt(args[0])));
			}
			if (stack == null)
			{
				sender.addChatMessage(new ChatComponentText("No such item."));
			}
			else
			{
				List<String> oreDictNames = new ArrayList<String>();
				for (int id : OreDictionary.getOreIDs(stack))
				{
					oreDictNames.add(OreDictionary.getOreName(id));
				}
				String textOreDictNames = "";
				for (String oreDictName : oreDictNames)
				{
					textOreDictNames += oreDictName + ". ";
				}
				if (StringUtils.isNullOrEmpty(textOreDictNames))
				{
					textOreDictNames = "<empty>";
				}
				UniqueIdentifier uid = GameRegistry.findUniqueIdentifierFor(stack.getItem());
				String textUid = uid.modId + ":" + uid.name;
				sender.addChatMessage(new ChatComponentText("> OreDict Names: " + textOreDictNames));
				sender.addChatMessage(new ChatComponentText("> Unique Name: " + textUid));
			}
		}
	}
	
	@Override
	public int getRequiredPermissionLevel()
	{
		return 0;
	}
}