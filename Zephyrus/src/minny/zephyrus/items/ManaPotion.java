package minny.zephyrus.items;

import java.util.ArrayList;
import java.util.List;

import minny.zephyrus.Zephyrus;
import minny.zephyrus.player.LevelManager;
import minny.zephyrus.utils.RecipeUtil;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class ManaPotion extends CustomItem {

	LevelManager lvl;
	RecipeUtil util;

	public ManaPotion(Zephyrus plugin) {
		super(plugin);
		lvl = new LevelManager(plugin);
		util = new RecipeUtil();
	}

	@Override
	public String name() {
		return "�bMana Potion";
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.POTION);
		createItem(i);
		return i;
	}

	@Override
	public void createItem(ItemStack i) {
		setItemName(i, this.name());
		ItemMeta m = i.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add("�7Restors Mana");
		m.setLore(lore);
		i.setItemMeta(m);
		setGlow(i);
	}

	@Override
	public Recipe recipe() {
		ItemStack manaPotion = new ItemStack(Material.POTION);
		createItem(manaPotion);
		ShapedRecipe recipe = new ShapedRecipe(manaPotion);
		recipe.shape("AAA", "ABA", "AAA");
		recipe.setIngredient('B', Material.POTION, 8192);
		recipe.setIngredient('A', Material.GLOWSTONE_DUST);
		return recipe;
	}

	@EventHandler
	public void onManaPotion(PlayerItemConsumeEvent e) {
		if (checkName(e.getItem(), "�bMana Potion")) {
			Player player = e.getPlayer();
			Zephyrus.mana.put(player.getName(),
					LevelManager.getLevel(player) * 100);
		}
	}

	@Override
	public boolean hasLevel() {
		return false;
	}

	@EventHandler
	public void onCraftHandle(PrepareItemCraftEvent e) {
		if (e.getRecipe() == this.recipe()) {
			List<HumanEntity> player = e.getViewers();
			for (HumanEntity en : player) {
				if (!en.hasPermission("zephyrus.craft.mana")) {
					e.getInventory().setResult(null);
				}
			}
		}
	}
}