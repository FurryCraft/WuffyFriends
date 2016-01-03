package com.fullhousedev.mc.friends.sql;

import com.fullhousedev.mc.friends.FriendRequest;
import com.fullhousedev.mc.friends.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Austin on 7/16/2015.
 */
public class SQLManager {
    private static SQLManager instance;

    public static SQLManager getInstance() {
        if(instance == null) {
            instance = new SQLManager();
        }
        return instance;
    }

    protected Connection conn;

    public SQLManager() {
        String address = (String) Main.mainConfig.get("sql.address");
        String database = (String) Main.mainConfig.get("sql.database");
        String user = (String) Main.mainConfig.get("sql.username");
        String pass = (String) Main.mainConfig.get("sql.password");

        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + address + "/" + database + "?" +
                    "user=" + user + "&password=" + pass);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        new MigrationManager().runMigration();
    }

    public UUID[] getFriends(UUID playerUUID) {
        String uuid = playerUUID.toString();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM friends WHERE friendOne = ? OR friendTwo = ?");
            stmt.setString(1, uuid);
            stmt.setString(2, uuid);

            ResultSet rs = stmt.executeQuery();

            ArrayList<UUID> friends = new ArrayList<UUID>();

            while(rs.next()) {
                if(rs.getString("friendOne").equals(uuid)) {
                    friends.add(UUID.fromString(rs.getString("friendTwo")));
                }
                else {
                    friends.add(UUID.fromString(rs.getString("friendOne")));
                }
            }

            return (UUID[]) friends.toArray(new UUID[1]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public FriendRequest[] getFriendRequests(UUID playerUUID) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM requests WHERE TargetUUID = ?");
            stmt.setString(1, playerUUID.toString());
            ResultSet rs = stmt.executeQuery();

            ArrayList<FriendRequest> friends = new ArrayList<FriendRequest>();

            while(rs.next()) {
                UUID requestingUUID = UUID.fromString(rs.getString("RequestingID"));
                String requestingName = rs.getString("RequestingName");
                UUID target = UUID.fromString(rs.getString("TargetUUID"));

                friends.add(new FriendRequest(requestingUUID, requestingName, target));
            }

            return friends.toArray(new FriendRequest[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isPendingFriends(UUID playerOne, UUID playerTwo) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM requests WHERE (RequestingID = ? AND TargetUUID = ?)" +
                    " OR (RequestingID = ? AND TargetUUID = ?)");
            stmt.setString(1, playerOne.toString());
            stmt.setString(2, playerTwo.toString());
            stmt.setString(3, playerTwo.toString());
            stmt.setString(4, playerOne.toString());

            ResultSet rs = stmt.executeQuery();

            rs.next();

            return (rs.getInt(1) > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean didOriginateRequest(UUID player, UUID target) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM requests WHERE (RequestingID = ? AND TargetUUID = ?)");
            stmt.setString(1, player.toString());
            stmt.setString(2, target.toString());

            ResultSet rs = stmt.executeQuery();

            rs.next();

            return (rs.getInt(1) > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendFriendRequest(FriendRequest request) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO requests (RequestingID, RequestingName, TargetUUID)"
                + " VALUES (?, ?, ?)");
            stmt.setString(1, request.getRequestingUUID().toString());
            stmt.setString(2, request.getRequestingName());
            stmt.setString(3, request.getTarget().toString());

            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void makeFriends(UUID friend1, UUID friend2) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM requests WHERE (RequestingID = ? AND TargetUUID = ?)" +
                    " OR (RequestingID = ? AND TargetUUID = ?)");
            stmt.setString(1, friend1.toString());
            stmt.setString(2, friend2.toString());
            stmt.setString(3, friend2.toString());
            stmt.setString(4, friend1.toString());
            stmt.execute();

            stmt = conn.prepareStatement("INSERT INTO friends (friendOne, friendTwo) VALUES (?, ?)");
            stmt.setString(1, friend1.toString());
            stmt.setString(2, friend2.toString());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isFriends(UUID friendOne, UUID friendTwo) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM friends WHERE (friendOne = ? AND friendTwo = ?) " +
                    "OR (friendOne = ? OR friendTwo = ?)");
            stmt.setString(1, friendOne.toString());
            stmt.setString(2, friendTwo.toString());
            stmt.setString(3, friendTwo.toString());
            stmt.setString(4, friendOne.toString());
            ResultSet rs = stmt.executeQuery();

            rs.next();
            int entries = rs.getInt(1);

            if(entries > 0) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteFriend(UUID personOne, UUID personTwo) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM requests WHERE (RequestingID = ? AND TargetUUID = ?)" +
                    " OR (RequestingID = ? AND TargetUUID = ?)");
            stmt.setString(1, personOne.toString());
            stmt.setString(2, personTwo.toString());
            stmt.setString(3, personTwo.toString());
            stmt.setString(4, personOne.toString());
            stmt.execute();

            stmt = conn.prepareStatement("DELETE FROM friends WHERE (friendOne = ? AND friendTwo = ?)" +
                    " OR (friendOne = ? AND friendTwo = ?)");
            stmt.setString(1, personOne.toString());
            stmt.setString(2, personTwo.toString());
            stmt.setString(3, personTwo.toString());
            stmt.setString(4, personOne.toString());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getLastKnownName(UUID uuid) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE uuid = ?");
            stmt.setString(1, uuid.toString());

            ResultSet rs = stmt.executeQuery();
            rs.next();

            return rs.getString("lastKnownName");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updatePlayerName(UUID uuid, String playerName) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (lastKnownName, uuid) VALUES (?, ?)" +
                    " ON DUPLICATE KEY UPDATE lastKnownName = ?");
            stmt.setString(1, playerName);
            stmt.setString(2, uuid.toString());
            stmt.setString(3, playerName);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
