package top.mores.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommands implements CommandExecutor {
    private final WarpHandler warpHandler = new WarpHandler();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player player) {

            if (args.length == 0) {
                player.sendMessage("使用方法：/warp <warpName> 或 /warp add <warpName> 或 /warp del <warpName>");
                return true;
            }

            if (args[0].equalsIgnoreCase("add")) {
                if (args.length != 2) {
                    player.sendMessage("请提供传送点的名称！");
                    return true;
                }
                String warpName = args[1];
                // 只有管理员才能添加传送点
                if (player.hasPermission("warp.add")) {
                    warpHandler.addWarp(player.getLocation(), warpName);
                    player.sendMessage("传送点 '" + warpName + "' 已添加！");
                } else {
                    player.sendMessage("你没有权限添加传送点！");
                }
                return true;

            } else if (args[0].equalsIgnoreCase("del")) {
                if (args.length != 2) {
                    player.sendMessage("请提供要删除的传送点名称！");
                    return true;
                }
                String warpName = args[1];
                // 只有管理员才能删除传送点
                if (player.hasPermission("warp.del")) {
                    warpHandler.removeWarp(warpName);
                    player.sendMessage("传送点 '" + warpName + "' 已删除！");
                } else {
                    player.sendMessage("你没有权限删除传送点！");
                }
                return true;

            } else {
                String warpName = args[0];
                warpHandler.teleportPlayer(player, warpName);
                return true;
            }
        } else {
            commandSender.sendMessage("只有玩家可以执行此命令！");
            return false;
        }
    }
}
