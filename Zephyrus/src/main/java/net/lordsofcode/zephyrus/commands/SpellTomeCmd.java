package net.lordsofcode.zephyrus.commands;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.items.SpellTome;
import net.lordsofcode.zephyrus.player.LevelManager;
import net.lordsofcode.zephyrus.spells.Spell;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class SpellTomeCmd extends ZephyrusCommand implements CommandExecutor {

	Zephyrus plugin;
	LevelManager lvl;

	public SpellTomeCmd(Zephyrus plugin) {
		this.plugin = plugin;
		lvl = new LevelManager(plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender.hasPermission("zephyrus.spelltome.give") || sender.isOp()) {
			if (args.length < 1) {
				sender.sendMessage("Usage: " + ChatColor.RED
						+ "/spelltome [spell] [player]");
			} else {
				if (args.length < 2) {
					if (sender instanceof Player) {
						if (Zephyrus.spellMap
								.containsKey(args[0].toLowerCase())) {
							Player player = (Player) sender;
							Spell spell = Zephyrus.spellMap.get(args[0]
									.toLowerCase());
							if (spell.isEnabled()) {
								SpellTome tome = new SpellTome(plugin,
										spell.name(), spell.bookText());
								player.getInventory().addItem(tome.item());
								sender.sendMessage("Gave " + player.getName()
										+ " the " + ChatColor.GOLD
										+ spell.name() + " spelltome");
							} else {
								sender.sendMessage(ChatColor.RED + "That spell is disabled!");
							}
						} else {
							sender.sendMessage(ChatColor.DARK_RED
									+ "That spell does not exist");
						}
					} else {
						sender.sendMessage("Usage: " + ChatColor.RED
								+ "/spelltome [spell] [player]");
					}
				} else {
					if (Zephyrus.spellMap.containsKey(args[0].toLowerCase())) {
						if (isOnline(args[1])) {
							Player player = Bukkit.getPlayer(args[1]);
							Spell spell = Zephyrus.spellMap.get(args[0]
									.toLowerCase());
							if (spell.isEnabled()) {
							SpellTome tome = new SpellTome(plugin,
									spell.name(), spell.bookText());
							player.getInventory().addItem(tome.item());
							sender.sendMessage("Gave " + player.getName()
									+ " the " + ChatColor.GOLD + spell.name()
									+ " spelltome");
							} else {
								sender.sendMessage(ChatColor.RED + "That spell is disabled!");
							}
						} else {
							notOnline(sender);
						}
					} else {
						sender.sendMessage(ChatColor.DARK_RED
								+ "That spell does not exist");
					}
				}
			}
		} else {
			needOp(sender);
		}
		return false;
	}

}
