package minny.zephyrus.commands;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.utils.CommandExceptions;
import minny.zephyrus.utils.ItemUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelUpItem extends CommandExceptions implements CommandExecutor {

	ItemUtil item;

	public LevelUpItem(Zephyrus plugin, ItemUtil i) {
		item = i;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			try {
				if (player.getItemInHand().getItemMeta().getDisplayName()
						.equalsIgnoreCase("�bGem of Lightning") || player.getItemInHand().getItemMeta().getDisplayName()
						.equalsIgnoreCase("�aHoe of Growth") || player.getItemInHand().getItemMeta().getDisplayName()
						.equalsIgnoreCase("�cRod of Fire")){
					int current = item.getItemLevel(player.getItemInHand());
					item.setItemLevel(player.getItemInHand(), current +1);
				}
			} catch (Exception e) {

			}
		} else {
			inGameOnly(sender);
		}
		return false;
	}

}