package com.thevoxelbox.voxelsniper.command;

import com.thevoxelbox.voxelsniper.SnipeAction;
import com.thevoxelbox.voxelsniper.Sniper;
import com.thevoxelbox.voxelsniper.SniperManager;
import com.thevoxelbox.voxelsniper.VoxelSniperPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class VoxelBrushToolCommand extends VoxelCommand {

	private VoxelSniperPlugin plugin;

	public VoxelBrushToolCommand(VoxelSniperPlugin plugin) {
		super("VoxelBrushTool", "btool", "voxelsniper.sniper");
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(Player sender, String[] args) {
		SniperManager sniperManager = this.plugin.getSniperManager();
		Sniper sniper = sniperManager.getSniperForPlayer(sender);
		if (args != null && args.length > 0) {
			if (args[0].equalsIgnoreCase("assign")) {
				SnipeAction action;
				if (args[1].equalsIgnoreCase("arrow")) {
					action = SnipeAction.ARROW;
				} else if (args[1].equalsIgnoreCase("powder")) {
					action = SnipeAction.GUNPOWDER;
				} else {
					sender.sendMessage("/btool assign <arrow|powder> <toolid>");
					return true;
				}
				if (args.length == 3 && args[2] != null && !args[2].isEmpty()) {
					PlayerInventory inventory = sender.getInventory();
					ItemStack itemInHand = inventory.getItemInMainHand();
					Material material = itemInHand.getType();
					if (material.isEmpty()) {
						sender.sendMessage("/btool assign <arrow|powder> <toolid>");
						return true;
					}
					if (sniper.setTool(args[2], action, material)) {
						sender.sendMessage(material.name() + " has been assigned to '" + args[2] + "' as action " + action.name() + ".");
					} else {
						sender.sendMessage("Couldn't assign tool.");
					}
					return true;
				}
			} else if (args[0].equalsIgnoreCase("remove")) {
				if (args.length == 2 && args[1] != null && !args[1].isEmpty()) {
					sniper.removeTool(args[1]);
				} else {
					PlayerInventory inventory = sender.getInventory();
					ItemStack itemInHand = inventory.getItemInMainHand();
					Material material = itemInHand.getType();
					if (material.isEmpty()) {
						sender.sendMessage("Can't unassign empty hands.");
						return true;
					}
					if (sniper.getCurrentToolId() == null) {
						sender.sendMessage("Can't unassign default tool.");
						return true;
					}
					sniper.removeTool(sniper.getCurrentToolId(), material);
				}
				return true;
			}
		}
		sender.sendMessage("/btool assign <arrow|powder> <toolid>");
		sender.sendMessage("/btool remove [toolid]");
		return true;
	}
}