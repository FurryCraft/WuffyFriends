package com.fullhousedev.mc.friends;

import java.util.UUID;

/**
 * Created by Austin on 8/23/2015.
 */
public class Utils {

    public static String convertMojangUUIDToJavaUUID(String uuid) {
        return uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16)
            + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32);
    }

    //Following two courtesy of
    //http://stackoverflow.com/a/391978
    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }
}
