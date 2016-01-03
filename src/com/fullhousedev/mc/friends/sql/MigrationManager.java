package com.fullhousedev.mc.friends.sql;

import com.fullhousedev.mc.friends.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Austin on 8/19/2015.
 */
public class MigrationManager {

    public void runMigration() {
        newDatabase();
    }

    private Connection createConnection() {
        String address = (String) Main.mainConfig.get("sql.address");
        String database = (String) Main.mainConfig.get("sql.database");
        String user = (String) Main.mainConfig.get("sql.username");
        String pass = (String) Main.mainConfig.get("sql.password");

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + address + "/" + database + "?" +
                    "user=" + user + "&password=" + pass);
            return conn;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return null;
        }
    }

    private void newDatabase() {
        Connection conn = createConnection();

        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS friends (id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "friendOne VARCHAR(45) NOT NULL, friendTwo VARCHAR(45) NOT NULL)");
            stmt.execute("CREATE TABLE IF NOT EXISTS requests (id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "RequestingID VARCHAR(45) NOT NULL, RequestingName VARCHAR(16) NOT NULL, TargetUUID " +
                    "VARCHAR(45) NOT NULL)");
            stmt.execute("CREATE TABLE IF NOT EXISTS users (id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "uuid VARCHAR(45) NOT NULL, lastKnownName VARCHAR(16) NOT NULL)");
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
