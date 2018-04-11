package me.therealjeremy.sd.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiveFlareCMD implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("supplydrops.giveflare")) {
			return false;
		}
		if (args.length != 2) {
			return false;
		}
		Player receiver = (Player) Bukkit.getOfflinePlayer(args[0]);
		int amount = Integer.parseInt(args[1]);
		ItemStack item = new ItemStack(Material.BLAZE_ROD, amount, (byte) 0);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6Supply Drop &fFlare"));
		List<String> lore = new ArrayList<>();
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7*click to throw*"));
		itemMeta.setLore(lore);
		itemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, false);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemMeta);
		receiver.getInventory().addItem(item);
		return false;
	}
}
