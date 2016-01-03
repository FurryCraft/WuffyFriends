package com.fullhousedev.mc.friends;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

/**
 * Created by Austin on 8/19/2015.
 */
public class MessageConstants {

    public static BaseComponent[] BUG_MESSAGE = new ComponentBuilder("Oh no! It appears something has gone wrong! Please" +
            " make a bug report at ").color(ChatColor.GOLD).append("http://www.furrycraft.net/index.php?forums/report-bugs.14/")
            .color(ChatColor.AQUA).event(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://www.furrycraft.net/index.php?forums/report-bugs.14/"))
            .append("!").color(ChatColor.GOLD).create();

    public static BaseComponent[] NO_REQUESTS = new ComponentBuilder("You don't have any friend requests!").color(ChatColor.GOLD)
            .create();
}
