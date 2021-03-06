package net.lordsofcode.zephyrus.spells;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class Confuse extends Spell {

	public Confuse(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return "confuse";
	}

	@Override
	public String bookText() {
		return "Makes all nearby entities fight eachouther!";
	}

	@Override
	public int reqLevel() {
		return 2;
	}

	@Override
	public int manaCost() {
		return 20;
	}

	@Override
	public void run(Player player, String[] args) {
		int r = getConfig().getInt(this.name() + ".radius");
		Monster[] e = getNearbyEntities(player.getLocation(), r);
		for (int i = 0; i < e.length; i++) {
			int index = i + 1;
			if (index >= e.length) {
				index = 0;
			}
			e[i].setTarget(e[index]);
			CraftLivingEntity m = (CraftLivingEntity) e[i];
			CraftLivingEntity tar = (CraftLivingEntity) e[index];
			m.getHandle().setGoalTarget(tar.getHandle());
		}
	}

	@Override
	public Map<String, Object> getConfigurations() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("radius", 8);
		return map;
	}
	
	@Override
	public boolean canRun(Player player, String[] args) {
		try {
			@SuppressWarnings("unused")
			CraftLivingEntity test = new CraftLivingEntity(null, null);
			return true;
		} catch (NoClassDefFoundError err) {
			return false;
		}
	}

	@Override
	public boolean canBind() {
		return true;
	}

	@Override
	public String failMessage() {
		return ChatColor.RED
				+ "Zephyrus is not fully compatible with this version of Bukkit.This spell has been disabled :(";
	}

	@Override
	public Set<ItemStack> spellItems() {
		Set<ItemStack> i = new HashSet<ItemStack>();
		i.add(new ItemStack(Material.BONE));
		i.add(new ItemStack(Material.ROTTEN_FLESH));
		i.add(new ItemStack(Material.SULPHUR));
		i.add(new ItemStack(Material.GOLD_NUGGET));
		return i;
	}

	public static Monster[] getNearbyEntities(Location l, int radius) {
		int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
		HashSet<Monster> radiusEntities = new HashSet<Monster>();
		for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
			for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
				int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
				for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z
						+ (chZ * 16)).getChunk().getEntities()) {
					if (e.getLocation().distance(l) <= radius
							&& e.getLocation().getBlock() != l.getBlock()) {
						if (e instanceof Monster) {
							radiusEntities.add((Monster) e);
						}
					}
				}
			}
		}
		return radiusEntities.toArray(new Monster[radiusEntities.size()]);
	}

	@Override
	public SpellType type() {
		return SpellType.ILLUSION;
	}

}
