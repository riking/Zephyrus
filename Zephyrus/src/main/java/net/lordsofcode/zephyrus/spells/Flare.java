package net.lordsofcode.zephyrus.spells;

import java.util.HashSet;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;
import net.lordsofcode.zephyrus.hooks.PluginHook;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Flare extends Spell {

	public Flare(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "flare";
	}

	@Override
	public String bookText() {
		return "Fires a big fireball on cast!";
	}

	@Override
	public int reqLevel() {
		return 5;
	}

	@Override
	public int manaCost() {
		return 20;
	}

	@Override
	public void run(Player player, String[] args) {
		player.launchProjectile(org.bukkit.entity.Fireball.class);
		if (player.getInventory().contains(Material.FIRE)) {
			Arrow arrow = player.launchProjectile(Arrow.class);
			arrow.setFireTicks(1000);
		}
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.FIREBALL, 32));
		i.add(new ItemStack(Material.BLAZE_POWDER, 16));
		return i;
	}

	@Override
	public boolean canRun(Player player, String[] args) {
		return PluginHook.canBuild(player, player.getTargetBlock(null, 1000));
	}

	@Override
	public String failMessage() {
		return ChatColor.DARK_RED + "You don't have permission for this area";
	}
	
	@Override
	public Spell reqSpell() {
		return new Fireball(plugin);
	}

	@Override
	public SpellType type() {
		return SpellType.FIRE;
	}

}
