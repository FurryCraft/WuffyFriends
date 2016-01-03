package com.fullhousedev.mc.friends.config;

import com.fullhousedev.mc.friends.Main;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by Austin on 7/17/2015.
 */
public class ConfigManager {

    private static ConfigManager instance;

    public static ConfigManager getInstance() {
        if(instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    public Configuration loadConfig(String fileName) throws IOException {
        return ConfigurationProvider.getProvider(YamlConfiguration.class)
                .load(new File(Main.plugin.getDataFolder(), fileName));
    }

    public void saveConfig(Configuration config, String fileName) throws IOException {
        ConfigurationProvider.getProvider(YamlConfiguration.class)
                .save(config, new File(Main.plugin.getDataFolder(), fileName));
    }
}
