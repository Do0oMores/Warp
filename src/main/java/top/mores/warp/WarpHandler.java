package top.mores.warp;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class WarpHandler {
    FileConfiguration config = Warp.getInstance().getConfig();

    //获取所有禁止使用的世界
    public List<String> getAllDenyWorlds() {
        return config.getStringList("在以下世界禁止使用");
    }

    //设置传送点
    public void addWarp(Location location, String warpName) {
        String world = Objects.requireNonNull(location.getWorld()).getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        config.set("data." + warpName + ".world", world);
        config.set("data." + warpName + ".x", x);
        config.set("data." + warpName + ".y", y);
        config.set("data." + warpName + ".z", z);
        Warp.getInstance().saveConfig();
    }

    //删除传送点
    public void removeWarp(String warpName) {
        config.set("data." + warpName, null);
        Warp.getInstance().saveConfig();
    }

    //使用传送点
    public void teleportPlayer(Player player,String warpName) {
        String world= config.getString("data." + warpName + ".world");
        if (world == null) {
            player.sendMessage("该传送点不存在！");
        }else if (getAllDenyWorlds().contains(player.getWorld().getName())) {
            player.sendMessage("该世界不允许使用该命令！");
        }else {
            double x = config.getDouble("data." + warpName + ".x");
            double y = config.getDouble("data." + warpName + ".y");
            double z = config.getDouble("data." + warpName + ".z");
            Location loc = new Location(Warp.getInstance().getServer().getWorld(world), x, y, z);
            player.teleport(loc);
        }
    }
}
