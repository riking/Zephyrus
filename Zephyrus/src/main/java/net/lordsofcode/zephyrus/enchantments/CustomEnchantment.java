package net.lordsofcode.zephyrus.enchantments;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.lordsofcode.zephyrus.Zephyrus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public abstract class CustomEnchantment extends Enchantment implements Listener {

	public CustomEnchantment(int id) {
		super(id);
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		} catch (Exception e) {
		}
		try {
			Enchantment.registerEnchantment(this);
		} catch (IllegalArgumentException e) {
		}
		Bukkit.getPluginManager().registerEvents(this, Zephyrus.getInstance());
	}

	public abstract int enchantLevelCost();

	public abstract int chance();
	
	public abstract boolean incompatible(Map<Enchantment, Integer> map);
	
	@EventHandler
	public void onEnchant(EnchantItemEvent e) {
		int level = e.getExpLevelCost() / enchantLevelCost();
		int chance = new Random().nextInt(chance());
		if (chance == 0 && level != 0) {
			if (e.getItem().getType() != Material.BOOK && !incompatible(e.getEnchantsToAdd())) {
				if (level > this.getMaxLevel()) {
					level = this.getMaxLevel();
				}
				e.getEnchantsToAdd().put(this, level);
				ItemMeta m = e.getItem().getItemMeta();
				List<String> lore;
				if (m.hasLore()) {
					lore = m.getLore();
				} else {
					lore = new ArrayList<String>();
				}
				lore.add(ChatColor.GRAY + this.getName() + " " + numeral(level));
				m.setLore(lore);
				e.getItem().setItemMeta(m);
			}
		}
	}

	public String numeral(int i) {
		switch (i) {
		case 1:
			return "I";
		case 2:
			return "II";
		case 3:
			return "III";
		case 4:
			return "IV";
		case 5:
			return "V";
		case 6:
			return "VI";
		case 7:
			return "VII";
		case 8:
			return "VIII";
		case 9:
			return "IX";
		case 10:
			return "X";
		default:
			return "";
		}
	}

}
