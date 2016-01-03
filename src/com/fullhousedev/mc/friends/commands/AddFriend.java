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
public class AddFriend implements Command {
    private Plugin plugin;

    public AddFriend(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(final ProxiedPlayer player, final String[] args) {
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                if(args.length < 1) {
                    player.sendMessage(new ComponentBuilder("Usage: ").color(ChatColor.RED).append("/friends friend <username>")
                        .color(ChatColor.AQUA).create());
                    return;
                }

                Profile[] profiles = Main.uuidFetcher.findProfilesByNames(args[0]);

                if(profiles.length == 0) {
                    player.sendMessage(new ComponentBuilder("Player ").color(ChatColor.GOLD).append(args[0]).color(ChatColor.BLUE)
                        .append(" does not exist!").color(ChatColor.GOLD).create());
                    return;
                }

                Profile profile = profiles[0];

                UUID friend = UUID.fromString(profile.getId());
                ProxiedPlayer friendInstance = plugin.getProxy().getPlayer(friend);

                if(SQLManager.getInstance().isPendingFriends(player.getUniqueId(), friend)) {
                    if(SQLManager.getInstance().didOriginateRequest(player.getUniqueId(), friend)) {
                        player.sendMessage(new ComponentBuilder("You already sent a friend request!").color(ChatColor.GOLD).create());
                    }
                    else {
                        SQLManager.getInstance().makeFriends(player.getUniqueId(), friend);
                        player.sendMessage(new ComponentBuilder(args[0] + " is now your friend!").color(ChatColor.GOLD).create());
                        if(friendInstance != null) {
                            friendInstance.sendMessage(new ComponentBuilder(player.getName() + " is now your friend!").color(ChatColor.GOLD).create());
                        }
                    }
                }
                else if(SQLManager.getInstance().isFriends(player.getUniqueId(), friend)) {
                    player.sendMessage(new ComponentBuilder("That person is already your friend!").color(ChatColor.GOLD).create());
                }
                else {
                    SQLManager.getInstance().sendFriendRequest(new FriendRequest(player.getUniqueId(), player.getName(), friend));
                    player.sendMessage(new ComponentBuilder("Friend request sent!").color(ChatColor.GOLD).create());
                    if(friendInstance != null) {
                        friendInstance.sendMessage(new ComponentBuilder(player.getName() + " has requested to be your friend! " +
                                "Type /friends add " + player.getName() + " to accept it!").color(ChatColor.GOLD).create());
                    }
                }
            }
        });
    }
}
