package com.fullhousedev.mc.friends.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Created by Austin on 8/20/2015.
 */
public class Help implements Command {
    @Override
    public void execute(ProxiedPlayer player, String[] args) {
        player.sendMessage(new ComponentBuilder("Usage of /friends:").color(ChatColor.GOLD).create());
        player.sendMessage(new ComponentBuilder("    add <username>").color(ChatColor.BLUE).append(": Add a friend, " +
                "or confirm a friend request").color(ChatColor.GOLD).create());
        player.sendMessage(new ComponentBuilder("    delete <username>").color(ChatColor.BLUE).append(": Delete a friend, " +
                "or delete a friend request").color(ChatColor.GOLD).create());
        player.sendMessage(new ComponentBuilder("    friends").color(ChatColor.BLUE).append(": List all your friends")
                .color(ChatColor.GOLD).create());
        player.sendMessage(new ComponentBuilder("    list").color(ChatColor.BLUE).append(": List all your friends")
                .color(ChatColor.GOLD).create());
        player.sendMessage(new ComponentBuilder("    requests").color(ChatColor.BLUE).append(": List all your friend requests")
                .color(ChatColor.GOLD).create());
    }
}
