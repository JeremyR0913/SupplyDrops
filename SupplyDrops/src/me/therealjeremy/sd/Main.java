package me.therealjeremy.sd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.therealjeremy.sd.Commands.GiveFlareCMD;
import me.therealjeremy.sd.Events.ChestOpenEvent;
import me.therealjeremy.sd.Events.FlareTossEvent;
import me.therealjeremy.sd.Events.ItemPickupEvent;
import me.therealjeremy.sd.Objects.SupplyDrop;

public class Main extends JavaPlugin {
	
	public void onDisable() {
	}
	
	public void onEnable() {
		registerConfig();
		registerCommands();
		registerEvents();
		registerRewards();
		registerLocations();
		registerSupplyDrops();
	}

	private void registerConfig() {
		// TODO Auto-generated method stub
		
	}

	private void registerEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new FlareTossEvent(this), this);
		pm.registerEvents(new ChestOpenEvent(this), this);
		pm.registerEvents(new ItemPickupEvent(this), this);
	}

	private void registerCommands() {
		//this.getCommand("test").setExecutor(new TestCMD());
		this.getCommand("giveflare").setExecutor(new GiveFlareCMD());
	}
	
	private List<Location> locations;
	
	private void registerLocations() {
		this.locations = new ArrayList<Location>();
		this.getServer().getScheduler().runTaskTimer(this, new Runnable() {
			
			@Override
			public void run() {
				for (Location location : locations) {
					Firework firework = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
					FireworkMeta fireworkMeta = firework.getFireworkMeta();
					FireworkEffect effect = FireworkEffect.builder().with(Type.BALL).withColor(Color.ORANGE).build();
					fireworkMeta.addEffect(effect);
					firework.setFireworkMeta(fireworkMeta);
				}
			}
		}, 50L, 50L);
	}
	
	public List<Location> getLocations() {
		return locations;
	}
	
	private Map<UUID, SupplyDrop> supplyDrops;
	
	private void registerSupplyDrops() {
		this.supplyDrops = new HashMap<UUID, SupplyDrop>();
	}
	
	public Map<UUID, SupplyDrop> getSupplyDrops() {
		return supplyDrops;
	}
	
	private List<SupplyDrop> rewards;
	
	private void registerRewards() {
		this.rewards = new ArrayList<SupplyDrop>();
		this.rewards.add(new SupplyDrop("givekey {player} gear 2", "&8(&9Supply Drop&8) &7You received &32 Gear Keys&7!", Material.TRIPWIRE_HOOK, Color.YELLOW));
		this.rewards.add(new SupplyDrop("givekey {player} pickaxe 1", "&8(&9Supply Drop&8) &7You received &b1 Pickaxe Key&7!", Material.TRIPWIRE_HOOK, Color.YELLOW));
		this.rewards.add(new SupplyDrop("givekey {player} spawner 1", "&8(&9Supply Drop&8) &7You received &c1 Spawner Key&7!", Material.TRIPWIRE_HOOK, Color.YELLOW));
		this.rewards.add(new SupplyDrop("givekey {player} tag 2", "&8(&9Supply Drop&8) &7You received &e2 Tag Keys&7!", Material.TRIPWIRE_HOOK, Color.YELLOW));
		this.rewards.add(new SupplyDrop("give {player} dpick 1 digspeed:10 unbreaking:5 fortune:15", "&8(&9Supply Drop&8) &7You received an &bFortune XV Pickaxe&7!", Material.DIAMOND_PICKAXE, Color.AQUA));
		this.rewards.add(new SupplyDrop("give {player} dhelmet 1 protection:10 unbreaking:5", "&8(&9Supply Drop&8) &7You received &bOP Armor&7!", Material.DIAMOND_HELMET, Color.AQUA));
		this.rewards.add(new SupplyDrop("give {player} dchestplate 1 protection:10 unbreaking:5", "&8(&9Supply Drop&8) &7You received &bOP Armor&7!", Material.DIAMOND_CHESTPLATE, Color.AQUA));
		this.rewards.add(new SupplyDrop("give {player} dleggings 1 protection:10 unbreaking:5", "&8(&9Supply Drop&8) &7You received &bOP Armor&7!", Material.DIAMOND_LEGGINGS, Color.AQUA));
		this.rewards.add(new SupplyDrop("give {player} dboots 1 protection:10 unbreaking:5", "&8(&9Supply Drop&8) &7You received &bOP Armor&7!", Material.DIAMOND_BOOTS, Color.AQUA));
		this.rewards.add(new SupplyDrop("pex user {player} add essentials.fly", "&8(&9Supply Drop&8) &7You received &c/fly&7!", Material.NETHER_STAR, Color.RED));
		this.rewards.add(new SupplyDrop("pex user {player} add xpfly.command", "&8(&9Supply Drop&8) &7You received &c/xpfly&7!", Material.NETHER_STAR, Color.RED));
		this.rewards.add(new SupplyDrop("pex user {player} add minetheft.sellwand", "&8(&9Supply Drop&8) &7You received &c/sellwand&7!", Material.NETHER_STAR, Color.RED));
		this.rewards.add(new SupplyDrop("armor give {player} diamond chestplate money:5000:300", "&8(&9Supply Drop&8) &7You received &eCustom Armor&7!", Material.DIAMOND_CHESTPLATE, Color.NAVY));
		this.rewards.add(new SupplyDrop("armor give {player} diamond chestplate xp:1000:300", "&8(&9Supply Drop&8) &7You received &eCustom Armor&7!", Material.DIAMOND_CHESTPLATE, Color.NAVY));
		this.rewards.add(new SupplyDrop("armor give {player} diamond chestplate stardust:40:300", "&8(&9Supply Drop&8) &7You received &eCustom Armor&7!", Material.DIAMOND_CHESTPLATE, Color.NAVY));
	}
	
	public List<SupplyDrop> getRewards() {
		return rewards;
	}

}
