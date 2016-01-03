package com.fullhousedev.mc.friends.commands;

import com.fullhousedev.mc.friends.Friend;
import com.fullhousedev.mc.friends.FriendRequest;
import com.fullhousedev.mc.friends.MessageConstants;
import com.fullhousedev.mc.friends.sql.SQLManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.UUID;

/**
 * Created by Austin on 8/16/2015.
 */
public class ListRequests implements Command {

    private Plugin plugin;

    public ListRequests(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(final ProxiedPlayer player, final String[] args) {
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                FriendRequest[] requests = SQLManager.getInstance().getFriendRequests(player.getUniqueId());

                if(requests.length == 0) {
                    player.sendMessage(MessageConstants.NO_REQUESTS);
                    return;
                }

                int i = 1;

                player.sendMessage(new ComponentBuilder("Your current requests: ").color(ChatColor.GOLD).create());
                player.sendMessage(new ComponentBuilder("------------------------------").color(ChatColor.GRAY).create());
                for(FriendRequest request : requests) {
                    player.sendMessage(new ComponentBuilder(request.getRequestingName()).color(ChatColor.BLUE).create());
                }
                player.sendMessage(new ComponentBuilder("------------------------------").color(ChatColor.GRAY).create());
                player.sendMessage(new ComponentBuilder("Use ").color(ChatColor.GOLD).append("/friends friend <username>")
                    .color(ChatColor.AQUA).append(" to add as a friend!").color(ChatColor.GOLD).create());
            }
        });
    }
}
