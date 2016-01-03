package com.fullhousedev.mc.friends.commands;

import com.fullhousedev.mc.friends.FriendRequest;
import com.fullhousedev.mc.friends.Main;
import com.fullhousedev.mc.friends.sql.SQLManager;
import com.mojang.api.profiles.Profile;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.UUID;

/**
 * Created by Austin on 8/19/2015.
 */
public class DeleteFriend implements Command {
    private Plugin plugin;

    public DeleteFriend(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(final ProxiedPlayer player, final String[] args) {
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                if (args.length < 1) {
                    player.sendMessage(new ComponentBuilder("Usage: ").color(ChatColor.RED).append("/friends friend <username>")
                            .color(ChatColor.AQUA).create());
                    return;
                }

                Profile profile = Main.uuidFetcher.findProfilesByNames(args[0])[0];
                UUID friend = UUID.fromString(profile.getId());

                if(!SQLManager.getInstance().isFriends(player.getUniqueId(), friend) &&
                        !SQLManager.getInstance().isPendingFriends(player.getUniqueId(), friend)) {
                    player.sendMessage(new ComponentBuilder("You two don't appear to be friends, nor is there a friend " +
                            "request!").color(ChatColor.GOLD).create());
                    return;
                }

                SQLManager.getInstance().deleteFriend(player.getUniqueId(), friend);
                player.sendMessage(new ComponentBuilder("Friend (or request) deleted!").color(ChatColor.GOLD).create());
            }
        });
    }
}