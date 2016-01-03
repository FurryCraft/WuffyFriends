package com.fullhousedev.mc.friends.commands;

import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Created by Austin on 8/16/2015.
 */
public interface Command {
    public void execute(ProxiedPlayer player, String[] args);
}
