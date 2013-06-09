package minnymin3.zephyrus.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.events.PlayerCastSpellEvent;
import minnymin3.zephyrus.events.PlayerLevelUpEvent;
import minnymin3.zephyrus.player.LevelManager;
import minnymin3.zephyrus.spells.Spell;
import minnymin3.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class LevelingListener implements Listener {

	LevelManager lvl;
	Zephyrus plugin;

	public LevelingListener(Zephyrus plugin) {
		this.plugin = plugin;
		lvl = new LevelManager(plugin);
	}

	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if (e.getEntity().getKiller() instanceof Player) {
			Player player = e.getEntity().getKiller();
			Entity en = e.getEntity();
			if (en instanceof Monster) {
				lvl.levelProgress(player, 2);
			} else if (en instanceof Player) {
				lvl.levelProgress(player, 4);
			}
		}
	}

	@EventHandler
	public void onCast(PlayerCastSpellEvent e) {
		float f = e.getSpell().getManaCost() / 3;
		lvl.levelProgress(e.getPlayer(), Math.round(f) + 1);
	}

	@EventHandler
	public void onLevelUp(final PlayerLevelUpEvent e) {
		if (plugin.getConfig().getBoolean("Levelup-Spells")) {
			Player player = e.getPlayer();
			List<String> l = new ArrayList<String>();
			for (Spell spell : Zephyrus.spellMap.values()) {
				if (spell.getLevel() <= e.getLevel() && !spell.isLearned(player, spell.name())) {
					List<String> learned = PlayerConfigHandler.getConfig(
							plugin, player).getStringList("learned");
					learned.add(spell.name());
					PlayerConfigHandler.getConfig(plugin, player).set(
							"learned", learned);
					PlayerConfigHandler.saveConfig(plugin, player);
					l.add(spell.name());
				}
			}
			StringBuilder sb = new StringBuilder();
			Iterator<String> it = l.iterator();
			while (it.hasNext()) {
				String str = it.next();
				if (it.hasNext()) {
					sb.append(", " + str);
				} else {
					sb.append(" and " + str);
				}
			}
			String str = sb.toString();
			if (sb.equals("")) {
				player.sendMessage(ChatColor.AQUA + "You have learned no new spells");
			} else {
				player.sendMessage(ChatColor.AQUA + "You have learned" + ChatColor.DARK_AQUA + str.replaceFirst(",", ""));
			}
		}
	}
}
