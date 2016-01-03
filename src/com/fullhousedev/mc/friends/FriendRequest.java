package com.fullhousedev.mc.friends;

import java.util.UUID;

/**
 * Created by Austin on 7/17/2015.
 */
public class FriendRequest {

    private UUID playerRequesting;
    private String requestingName;
    private UUID target;

    public FriendRequest(UUID playerRequesting, String requestingName, UUID target) {
        this.playerRequesting = playerRequesting;
        this.requestingName = requestingName;
        this.target = target;
    }

    public String getRequestingName() {
        return requestingName;
    }

    public UUID getRequestingUUID() {
        return playerRequesting;
    }

    public UUID getTarget() {
        return target;
    }
}
