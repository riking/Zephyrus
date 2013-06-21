package minnymin3.zephyrus.items;

import minnymin3.zephyrus.Zephyrus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Zephyrus
 * 
 * @author minnymin3
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class LifeSuckIron extends CustomItem {

	public LifeSuckIron(Zephyrus plugin) {
		super(plugin);
	}

	@Override
	public String name() {
		return ChatColor.getByChar("a") + "Iron Sword of Life";
	}

	@Override
	public ItemStack item() {
		ItemStack i = new ItemStack(Material.IRON_SWORD);
		setItemName(i, this.name());
		setCustomEnchantment(i, plugin.suck, 1);
		return i;
	}

	public void createUpgradeItem(ItemStack i) {
		setItemName(i, this.name());
		setCustomEnchantment(i, plugin.suck, 2);
	}

	public ItemStack upgradeItem() {
		ItemStack i = new ItemStack(Material.IRON_SWORD);
		createUpgradeItem(i);
		return i;
	}

	@Override
	public Recipe recipe() {
		ItemStack i = item();

		ShapedRecipe recipe = new ShapedRecipe(i);
		recipe.shape("BCB", "BCB", "BAB");
		recipe.setIngredient('C', Material.IRON_INGOT);
		recipe.setIngredient('B', Material.GHAST_TEAR);
		recipe.setIngredient('A', Material.STICK);
		return recipe;
	}

	@Override
	public boolean hasLevel() {
		return false;
	}

	@Override
	public int maxLevel() {
		return 0;
	}
	

	@Override
	public String perm() {
		return "lifesuck";
	}
}
