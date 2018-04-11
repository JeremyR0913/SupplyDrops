package me.therealjeremy.sd.Objects;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SupplyDrop {
	
	private String command;
	private String message;
	private ItemStack item;
	private Color fireworkColor;
	
	public SupplyDrop(String command, String message, Material material, Color fireworkColor) {
		this.command = command;
		this.message = message;
		ItemStack item = new ItemStack(material);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
		itemMeta.setDisplayName(new Random().nextInt(100000) + "");
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemMeta);
		this.item = item;
		this.fireworkColor = fireworkColor;
	}
	
	public String getCommand() {
		return command;
	}
	
	public ItemStack getItem() {
		return item;
	}
	
	public Color getFireworkColor() {
		return fireworkColor;
	}
	
	public String getMessage() {
		return message;
	}

}
