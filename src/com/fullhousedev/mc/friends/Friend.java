package com.fullhousedev.mc.friends;

import com.fullhousedev.mc.friends.sql.SQLManager;

import java.util.UUID;

/**
 * Created by Austin on 8/18/2015.
 */
public class Friend {

    private UUID uuid;
    private boolean online;
    private String name;

    public Friend(UUID uuid, boolean online) {
        this(uuid, online, null);
    }

    public Friend(UUID uuid, boolean online, String name) {
        this.uuid = uuid;
        this.online = online;
        this.name = name;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public boolean isOnline() {
        return this.online;
    }

    public String getName() {
        if(name == null) {
            name = SQLManager.getInstance().getLastKnownName(uuid);
        }

        return name;
    }
}
