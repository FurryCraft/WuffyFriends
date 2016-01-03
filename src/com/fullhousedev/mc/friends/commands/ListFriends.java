package com.fullhousedev.mc.friends.commands;

import com.fullhousedev.mc.friends.Friend;
import com.fullhousedev.mc.friends.Utils;
import com.fullhousedev.mc.friends.menus.friends.DeleteHandler;
import com.fullhousedev.mc.friends.sql.SQLManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Austin on 8/16/2015.
 */
public class ListFriends implements Command {

    private Plugin plugin;

    public ListFriends(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(final ProxiedPlayer player, final String[] args) {
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                UUID[] friends = SQLManager.getInstance().getFriends(player.getUniqueId());
                Friend[] friendList = new Friend[friends.length];
                int i = 0;
                for(UUID friendUUID : friends) {
                    ProxiedPlayer player = plugin.getProxy().getPlayer(friendUUID);
                    if(player == null) {
                        friendList[i] = new Friend(friendUUID, false);
                        i++;
                    }
                    else {
                        friendList[i] = new Friend(friendUUID, true, player.getName());
                        i++;
                    }
                }

                player.sendMessage(new ComponentBuilder("Your friends: ").color(ChatColor.GOLD).create());
                player.sendMessage(new ComponentBuilder("------------------------------").color(ChatColor.GRAY).create());

                BaseComponent[][] menu = buildMenu(friendList);
                for(BaseComponent[] line : menu) {
                    player.sendMessage(line);
                }

                /*for(Friend friend : friendList) {
                    player.sendMessage(new ComponentBuilder(friend.getName()).color(ChatColor.BLUE).append("(").color(ChatColor.GRAY)
                        .append((friend.isOnline()) ? "online" : "offline").color((friend.isOnline()) ? ChatColor.GREEN : ChatColor.RED)
                        .append(")").color(ChatColor.GRAY).create());
                }*/
                player.sendMessage(new ComponentBuilder("------------------------------").color(ChatColor.GRAY).create());
            }
        });
    }

    private BaseComponent[][] buildMenu(Friend[] friends) {
        ArrayList<BaseComponent[]> lines = new ArrayList<BaseComponent[]>();

        for(Friend friend : friends) {
            int totalLength = 2 + friend.getName().length() + ((friend.isOnline()) ? 5 : 6);
            ComponentBuilder builder = new ComponentBuilder(friend.getName())
                    .color(ChatColor.BLUE)
                    .append("(")
                    .color(ChatColor.GRAY)
                    .append((friend.isOnline()) ? "online" : "offline")
                    .color((friend.isOnline()) ? ChatColor.GREEN : ChatColor.RED)
                    .append(")")
                    .color(ChatColor.GRAY)
                    .append(Utils.padLeft(" ", 29 - totalLength))
                    .append("Delete")
                    .color(ChatColor.RED)
                    .event(new DeleteHandler().getMethod(new String[] {friend.getName()}));

            lines.add(builder.create());
        }

        return lines.toArray(new BaseComponent[0][]);
    }

}
