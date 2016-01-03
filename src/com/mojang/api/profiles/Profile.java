package com.mojang.api.profiles;

import com.fullhousedev.mc.friends.Utils;

public class Profile {
    private String id;
    private String name;

    public String getId() {
        //Altered to return an actual proper UUID.
        return Utils.convertMojangUUIDToJavaUUID(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
