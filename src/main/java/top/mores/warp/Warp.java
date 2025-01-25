package top.mores.warp;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class Warp extends JavaPlugin {
    private static Warp instance;
    private static FileConfiguration config;

    @Override
    public void onEnable() {
        instance = this;
        loadConfig();
        Objects.requireNonNull(this.getCommand("warp")).setExecutor(new WarpCommands());
        getLogger().info("Warp has been enabled");
    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
        getLogger().info("Warp has been disabled");
    }

    private void loadConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        config = getConfig();
    }

    public static Warp getInstance() {
        return instance;
    }

    @Override
    public void saveConfig() {
        try{
            config.save(new File(getDataFolder(), "config.yml"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
