package com.fullhousedev.mc.friends.menus;

import net.md_5.bungee.api.chat.ClickEvent;

/**
 * Created by Austin on 8/23/2015.
 */
public interface MenuItem {

    public ClickEvent getMethod(String[] args);
}
