package com.fullhousedev.mc.friends.menus.friends;

import com.fullhousedev.mc.friends.menus.MenuItem;
import net.md_5.bungee.api.chat.ClickEvent;

/**
 * Created by Austin on 8/23/2015.
 */
public class DeleteHandler implements MenuItem {

    @Override
    public ClickEvent getMethod(String[] args) {
        return new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friends delete " + args[0]);
    }
}
