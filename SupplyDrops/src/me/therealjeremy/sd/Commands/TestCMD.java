package me.therealjeremy.sd.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TestCMD implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;
		if (!player.hasPermission("permission")) {
			return false;
		}
		Location location = player.getLocation();
		location.setX(location.getX() + 3);
		location.setY(location.getY() + 3);
		location.setZ(location.getZ() + 3);
		FallingBlock fallingBlock = location.getWorld().spawnFallingBlock(location.add(0, 1, 0),
				Integer.parseInt(args[0]), (byte) Integer.parseInt(args[1]));
		fallingBlock.setDropItem(false);
		fallingBlock.setHurtEntities(false);
		fallingBlock.setVelocity(new Vector(1,1,1));
		//Timer timer = new Timer();
		//for (int i = 0; i < 10000; i++) {
		//	timer.schedule(new TimerTask() {
		//		@Override
		//		public void run() {
		//			fallingBlock.setVelocity(new Vector(Math.cos(System.currentTimeMillis()), 0,
		//					Math.sin(System.currentTimeMillis())));
		//		}
		//	}, i * 1);
		//}

		return false;
	}

}
