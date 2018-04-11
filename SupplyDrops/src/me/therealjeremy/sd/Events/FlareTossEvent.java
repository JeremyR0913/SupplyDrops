package me.therealjeremy.sd.Events;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import me.therealjeremy.sd.Main;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class FlareTossEvent implements Listener {

	private Main plugin;

	public FlareTossEvent(Main plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void flareTossEvent(PlayerInteractEvent e) {
		if (e.getPlayer().getItemInHand() == null) {
			return;
		}
		ItemStack itemStack = e.getPlayer().getItemInHand();
		if (itemStack.getType().equals(Material.BLAZE_ROD)) {
			if (itemStack.getItemMeta().getLore() == null || itemStack.getItemMeta().getLore().toArray()[0] == null) {
				return;
			} else {
				String lore = ChatColor.stripColor(itemStack.getItemMeta().getLore().toArray()[0].toString());
				if (lore.equals("*click to throw*")) {
					e.setCancelled(true);
					itemStack.setAmount(itemStack.getAmount() - 1);
					e.getPlayer().setItemInHand(itemStack);
					Location location = e.getPlayer().getLocation();
					location.add(0, 2, 0);
					// Entity entity = location.getWorld().spawnEntity(location,
					// EntityType.DROPPED_ITEM);
					Item item = location.getWorld().dropItem(location, new ItemStack(Material.BLAZE_ROD));
					item.setCustomName(new Random().nextInt(10000) + "");
					item.setPickupDelay(10000000);
					double pitchRadians = Math.toRadians(location.getPitch());
					double yawRadians = Math.toRadians(location.getYaw());
					double a = 1;
					double sinPitch = Math.sin(pitchRadians) * a;
					double cosPitch = Math.cos(pitchRadians) * a;
					double sinYaw = Math.sin(yawRadians) * a;
					double cosYaw = Math.cos(yawRadians) * a;
					Vector v = new Vector(-cosPitch * sinYaw, -sinPitch, cosPitch * cosYaw);
					item.setVelocity(v);
					UUID id = item.getUniqueId();
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

						@Override
						public void run() {
							for (Entity entity : location.getWorld().getEntities()) {
								if (entity.getUniqueId().equals(id)) {
									if (entity.isOnGround()) {
										Location location2 = entity.getLocation();
										FallingBlock fallingBlock = location2.getWorld().spawnFallingBlock(
												location2.add(0, 5, 0), Material.NOTE_BLOCK, (byte) 0);
										fallingBlock.setDropItem(false);
										fallingBlock.setHurtEntities(false);
										Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

											@Override
											public void run() {
												location2.add(0, -5, 0);
												while (!(location2.getBlock().getType() == Material.AIR
														|| location2.getBlock().getType() == Material.NOTE_BLOCK)) {
													location2.add(0, 1, 0);
												}
												location2.getBlock().setType(Material.CHEST);
												plugin.getLocations().add(new Location(location2.getWorld(),
														location2.getBlockX() + 0.5, location2.getBlockY() + 0.5,
														location2.getBlockZ() + 0.5));
											}
										}, 20);
										break;
									}
								}
							}
						}
					}, 200L);
					BukkitTask task2 = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

						@Override
						public void run() {
							for (Entity entity : location.getWorld().getEntities()) {
								if (entity.getUniqueId().equals(id)) {
									Location location = entity.getLocation();
									PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
											EnumParticle.FLAME, true, (float) location.getX(),
											(float) (location.getY() + 0.5), (float) location.getZ(), (float) 0.1,
											(float) 0.1, (float) 0.1, (float) 0.5, 0, 20);
									for (Player player : Bukkit.getOnlinePlayers()) {
										if (player.getWorld().equals(entity.getWorld())) {
											((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
										}
									}
									break;
								}
							}
						}
					}, 1L, 1L);
					Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

						@Override
						public void run() {
							item.remove();
							task2.cancel();
						}
					}, 210);
				}
			}
		}
	}
}
