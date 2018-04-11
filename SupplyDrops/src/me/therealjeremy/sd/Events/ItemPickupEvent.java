package me.therealjeremy.sd.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import me.therealjeremy.sd.Main;
import me.therealjeremy.sd.Objects.SupplyDrop;

public class ItemPickupEvent implements Listener{
	
	private Main plugin;
	
	public ItemPickupEvent(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void itemPickupEvent(PlayerPickupItemEvent e) {
		Item item = e.getItem();
		if (plugin.getSupplyDrops().get(item.getUniqueId()) != null) {
			e.setCancelled(true);
			item.remove();
			SupplyDrop supplyDrop = plugin.getSupplyDrops().get(item.getUniqueId());
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), supplyDrop.getCommand().replace("{player}", e.getPlayer().getName()));
			e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', supplyDrop.getMessage()));
		}
	}

}
