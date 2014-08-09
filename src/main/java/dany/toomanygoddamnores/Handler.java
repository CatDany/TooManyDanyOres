package dany.toomanygoddamnores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;

public class Handler
{
	@SubscribeEvent
	public void blockDrops(BlockEvent.HarvestDropsEvent e)
	{
		if (Config.replacements != null && e.block != null)
		{
			ItemStack oreStack = new ItemStack(e.block, 1, e.blockMetadata);
			if (oreStack.getItem() != null)
			{
				int[] oreIds = OreDictionary.getOreIDs(oreStack);
				for (int oreId : oreIds)
				{
					if (oreId >= 0)
					{
						String oreName = OreDictionary.getOreName(oreId);
						if (!StringUtils.isNullOrEmpty(oreName) && Config.replacements.containsKey(oreName))
						{
							String replacementName = Config.replacements.get(oreName).getString();
							Property exclusionRaw = Config.exclusions.get(oreName);
							List<String> exclusionNames = null;
							if (exclusionRaw != null)
							{
								exclusionNames = Arrays.asList(exclusionRaw.getStringList());
							}
							else
							{
								exclusionNames = new ArrayList<String>();
							}
							UniqueIdentifier ore = GameRegistry.findUniqueIdentifierFor(oreStack.getItem());
							if (ore == null || exclusionNames.contains(ore.modId + ":" + ore.name))
							{
								return;
							}
							ItemStack replacementStack = null;
							for (ItemStack s : OreDictionary.getOres(oreName))
							{
								UniqueIdentifier item = GameRegistry.findUniqueIdentifierFor(s.getItem());
								if (item != null && !exclusionNames.contains(item.modId + ":" + item.name) && item.modId.contains(replacementName))
								{
									replacementStack = s.copy();
									break;
								}
							}
							if (replacementStack != null)
							{
								for (int j = 0; j < e.drops.size(); j++)
								{
									if (e.drops.get(j) != null && ((ItemStack)e.drops.get(j)).isItemEqual(oreStack))
									{
										e.drops.set(j, replacementStack);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void itemPickedUp(EntityItemPickupEvent e)
	{
		ItemStack oreStack = e.item.getEntityItem();
		ItemStack replacementStack = handleStack(oreStack);
		if (replacementStack != null)
		{
			e.item.setEntityItemStack(replacementStack);
		}
	}
	
	private static final HashMap<EntityPlayer, Integer> ticksBeforeMergingMap = new HashMap<EntityPlayer, Integer>();
	
	@SubscribeEvent
	public void playerTick(LivingEvent.LivingUpdateEvent e)
	{
		if (!e.entityLiving.worldObj.isRemote && e.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)e.entityLiving;
			if (!ticksBeforeMergingMap.containsKey(player))
			{
				ticksBeforeMergingMap.put(player, 0);
			}
			int ticksBeforeMerging = ticksBeforeMergingMap.get(player);
			if (ticksBeforeMerging == 0)
			{
				for (int i = 0; i < player.inventory.getSizeInventory(); i++)
				{
					ItemStack oreStack = player.inventory.getStackInSlot(i);
					ItemStack replacementStack = handleStack(oreStack);
					if (replacementStack != null)
					{
						player.inventory.setInventorySlotContents(i, replacementStack);
					}
				}
				ticksBeforeMerging = Config.mergeItemsEvery;
			}
			else
			{
				ticksBeforeMerging--;
			}
			ticksBeforeMergingMap.put(player, ticksBeforeMerging);
		}
	}
	
	private ItemStack handleStack(ItemStack oreStack)
	{
		if (oreStack != null && oreStack.getItem() != null)
		{
			int[] oreIds = OreDictionary.getOreIDs(oreStack);
			for (int oreId : oreIds)
			{
				if (oreId >= 0)
				{
					String oreName = OreDictionary.getOreName(oreId);
					if (!StringUtils.isNullOrEmpty(oreName) && Config.itemReplacements.containsKey(oreName))
					{
						String replacementName = Config.itemReplacements.get(oreName).getString();
						Property exclusionRaw = Config.exclusions.get(oreName);
						List<String> exclusionNames = null;
						if (exclusionRaw != null)
						{
							exclusionNames = Arrays.asList(exclusionRaw.getStringList());
						}
						else
						{
							exclusionNames = new ArrayList<String>();
						}
						UniqueIdentifier ore = GameRegistry.findUniqueIdentifierFor(oreStack.getItem());
						if (ore == null || exclusionNames.contains(ore.modId + ":" + ore.name))
						{
							return null;
						}
						ItemStack replacementStack = null;
						for (ItemStack s : OreDictionary.getOres(oreName))
						{
							UniqueIdentifier item = GameRegistry.findUniqueIdentifierFor(s.getItem());
							if (item != null && !exclusionNames.contains(item.modId + ":" + item.name) && item.modId.contains(replacementName))
							{
								replacementStack = s.copy();
								break;
							}
						}
						if (replacementStack != null)
						{
							replacementStack.stackSize = oreStack.stackSize;
							return replacementStack;
						}
					}
				}
			}
		}
		return null;
	}
}