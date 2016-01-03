package com.fullhousedev.mc.friends;

import com.fullhousedev.mc.friends.config.ConfigManager;
import com.fullhousedev.mc.friends.listeners.JoinListener;
import com.mojang.api.profiles.HttpProfileRepository;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by Austin on 7/16/2015.
 */
public class Main extends Plugin {

    public static Plugin plugin;
    public static Configuration mainConfig;
    public static HttpProfileRepository uuidFetcher = new HttpProfileRepository("minecraft");

    @Override
    public void onEnable() {
        plugin = this;
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            try {
                Files.copy(getResourceAsStream("config.yml"), file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            mainConfig = ConfigManager.getInstance().loadConfig("config.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        getProxy().getPluginManager().registerCommand(this, new CommandProcessor(this));
        getProxy().getPluginManager().registerListener(this, new JoinListener(this));
    }
}
