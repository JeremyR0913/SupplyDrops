package me.therealjeremy.sd.Events;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import me.therealjeremy.sd.Main;
import me.therealjeremy.sd.Objects.SupplyDrop;

public class ChestOpenEvent implements Listener {

	private Main plugin;

	public ChestOpenEvent(Main plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void chestOpenEvent(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null) {
			return;
		}
		if (e.getClickedBlock().getType() == Material.CHEST) {
			Location location1 = e.getClickedBlock().getLocation();
			int count = 0;
			for (Location location2 : plugin.getLocations()) {
				if (location1.getBlockX() == location2.getBlockX() && location1.getBlockY() == location2.getBlockY()
						&& location1.getBlockZ() == location2.getBlockZ()) {
					e.setCancelled(true);
					location2.getBlock().setType(Material.AIR);
					SupplyDrop supplyDrop = plugin.getRewards().get(new Random().nextInt(plugin.getRewards().size()));
					ItemStack itemStack = supplyDrop.getItem();
					Item item = location2.getWorld().dropItem(location2, itemStack);
					item.setCustomName(new Random().nextInt(10000) + "");
					plugin.getSupplyDrops().put(item.getUniqueId(), supplyDrop);
					Color color = supplyDrop.getFireworkColor();
					Firework firework = (Firework) location2.getWorld().spawnEntity(location2, EntityType.FIREWORK);
					FireworkMeta fireworkMeta = firework.getFireworkMeta();
					FireworkEffect effect = FireworkEffect.builder().with(Type.BALL).withColor(color).build();
					fireworkMeta.addEffect(effect);
					firework.setFireworkMeta(fireworkMeta);
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						
						@Override
						public void run() {
							firework.detonate();
						}
					}, 2L);
					plugin.getLocations().remove(count);
					break;
				}
				count++;
			}
		}
	}

}
