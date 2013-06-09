package minnymin3.zephyrus.spells;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import minnymin3.zephyrus.Zephyrus;
import minnymin3.zephyrus.items.SpellTome;
import minnymin3.zephyrus.player.LevelManager;
import minnymin3.zephyrus.utils.ConfigHandler;
import minnymin3.zephyrus.utils.ParticleEffects;
import minnymin3.zephyrus.utils.PlayerConfigHandler;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public abstract class Spell extends LevelManager {

	public Spell(Zephyrus plugin) {
		super(plugin);
		plugin.addSpell(this);
	}

	/**
	 * Defines the name of the spell
	 */
	public abstract String name();

	/**
	 * The text that appears in the SpellTome
	 */
	public abstract String bookText();

	/**
	 * The level required to craft the spell
	 */
	public abstract int reqLevel();

	/**
	 * The mana cost multiplied by the configurable mana multiplier
	 */
	public abstract int manaCost();

	/**
	 * The method called when the spell is cast
	 */
	public abstract void run(Player player, String[] args);

	/**
	 * A Set containing all the items required to craft the spell
	 */
	public abstract Set<ItemStack> spellItems();

	/**
	 * Weather or not the spell can be bound to a wand
	 */
	public boolean canBind() {
		return true;
	}

	/**
	 * A spell that is required for crafting this spell
	 */
	public Spell reqSpell() {
		return null;
	}

	/**
	 * The boolean dictating if the spell can be run
	 */
	public boolean canRun(Player player, String[] args) {
		return true;
	}

	/**
	 * The message that is sent to the player when canRun returns false
	 */
	public String failMessage() {
		return "";
	}

	public Map<String, String> getCfgKeys() {
		return null;
	}
	
	/**
	 * If the spell is learned
	 */
	public boolean isLearned(Player p, String name) {
		PlayerConfigHandler.reloadConfig(plugin, p);
		if (PlayerConfigHandler.getConfig(plugin, p).getStringList("learned")
				.contains(name)) {
			return true;
		}
		return false;
	}

	public void notEnoughMana(Player p) {
		p.sendMessage(ChatColor.DARK_GRAY + "Not enough mana!");
	}

	/**
	 * Checks if the player has permission to cast the spell. Returns false if
	 * OpKnowledge is false in the config
	 * 
	 * @param player
	 *            The target player
	 * @param spell
	 *            The target spell
	 */
	public boolean hasPermission(Player player, Spell spell) {
		if (plugin.getConfig().getBoolean("OpKnowledge")) {
			if (player.hasPermission("zephyrus.cast." + spell.name())) {
				return true;
			}
			if (player.isOp()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The method for destroying a bookshelf and dropping a spelltome
	 */
	public void dropSpell(Block bookshelf, String name, String desc, Player player) {
		Random r = new Random();
		bookshelf.setType(Material.AIR);
		SpellTome tome = new SpellTome(plugin, name, desc);
		Location loc = bookshelf.getLocation();
		loc.setX(loc.getX() + 0.5);
		loc.setZ(loc.getZ() + 0.5);
		loc.getWorld().dropItem(loc.add(0, +1, 0), tome.item())
				.setVelocity(new Vector(0, 0, 0));
		int chance = 1;
		if (LevelManager.getLevel(player) < 7) {
			chance = 1;
		} else if (LevelManager.getLevel(player) < 15) {
			chance = 2;
		} else {
			chance = 3;
		}
		loc.getWorld().dropItem(loc.add(0, +0.5, 0), new ItemStack(Material.BOOK, r.nextInt(chance)));
		try {
			ParticleEffects.sendToLocation(ParticleEffects.ENCHANTMENT_TABLE,
					loc, 0, 0, 0, 1, 30);
			loc.getWorld().playSound(loc, Sound.ORB_PICKUP, 3, 12);
		} catch (Exception e) {}
	}
	
	public int getManaCost() {
		ConfigHandler cfg = new ConfigHandler(plugin, "spells.yml");
		int cost = cfg.getConfig().getInt(this.name() + ".mana");
		return cost;
	}
	
	public int getLevel() {
		ConfigHandler cfg = new ConfigHandler(plugin, "spells.yml");
		int level = cfg.getConfig().getInt(this.name() + ".level");
		return level;
	}
	
	public boolean isEnabled() {
		ConfigHandler cfg = new ConfigHandler(plugin, "spells.yml");
		return cfg.getConfig().getBoolean(this.name() + ".enabled");
	}
	
	public Map<String, Object> getConfigurations() {
		return null;
	}

	public FileConfiguration getConfig() {
		ConfigHandler cfg = new ConfigHandler(plugin, "spells.yml");
		return cfg.getConfig();
	}
	
}
