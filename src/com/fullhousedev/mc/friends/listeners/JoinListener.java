package com.fullhousedev.mc.friends.listeners;

import com.fullhousedev.mc.friends.FriendRequest;
import com.fullhousedev.mc.friends.MessageConstants;
import com.fullhousedev.mc.friends.sql.SQLManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

/**
 * Created by Austin on 8/19/2015.
 */
public class JoinListener implements Listener {

    private Plugin plugin;

    public JoinListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPostLogin(final PostLoginEvent event) {
        plugin.getProxy().getScheduler().runAsync(plugin, new Runnable() {
            @Override
            public void run() {
                FriendRequest[] requests = SQLManager.getInstance().getFriendRequests(event.getPlayer().getUniqueId());

                if(requests == null) {
                    event.getPlayer().sendMessage(MessageConstants.BUG_MESSAGE);
                }
                else if(requests.length == 0) {
                    event.getPlayer().sendMessage(MessageConstants.NO_REQUESTS);
                }
                else {
                    event.getPlayer().sendMessage(new ComponentBuilder("You have ").color(ChatColor.GOLD).append("" + requests.length)
                            .color(ChatColor.BLUE).append(" friend requests pending! Use (or click) ").color(ChatColor.GOLD)
                            .append("/friends requests").color(ChatColor.AQUA).event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/friends requests"))
                            .append("!").color(ChatColor.GOLD).create());
                }

                SQLManager.getInstance().updatePlayerName(event.getPlayer().getUniqueId(), event.getPlayer().getName());

                UUID[] friends = SQLManager.getInstance().getFriends(event.getPlayer().getUniqueId());

                for(UUID friend : friends) {
                    ProxiedPlayer player = plugin.getProxy().getPlayer(friend);

                    if(player != null) {
                        player.sendMessage(new ComponentBuilder("[").color(ChatColor.GRAY).append(event.getPlayer().getName())
                            .color(ChatColor.GRAY).append(" has just logged in]").color(ChatColor.GRAY).create());
                    }
                }
            }
        });
    }
}
