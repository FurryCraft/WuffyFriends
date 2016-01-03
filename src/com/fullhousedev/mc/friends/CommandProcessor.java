package com.fullhousedev.mc.friends;

import com.fullhousedev.mc.friends.commands.*;
import com.fullhousedev.mc.friends.commands.Command;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.*;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Austin on 8/19/2015.
 */
public class CommandProcessor extends net.md_5.bungee.api.plugin.Command {

    public HashMap<String, Command> commandHandlers = new HashMap<String, Command>();

    public CommandProcessor(Plugin plugin) {
        super("friends");
        commandHandlers.put("add", new AddFriend(plugin));
        commandHandlers.put("delete", new DeleteFriend(plugin));
        commandHandlers.put("friends", new ListFriends(plugin));
        commandHandlers.put("requests", new ListRequests(plugin));
        commandHandlers.put("list", commandHandlers.get("friends"));
        commandHandlers.put("help", new Help());
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof ProxiedPlayer)) {
            commandSender.sendMessage(new ComponentBuilder("No no no! You can't do that!").color(ChatColor.RED).create());
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) commandSender;

        if(args.length < 1) {
            commandHandlers.get("help").execute(player, null);
            return;
        }

        Command command = commandHandlers.get(args[0]);

        if(command == null) {
            player.sendMessage(new ComponentBuilder("Command not found!").color(ChatColor.RED).create());
            return;
        }

        command.execute(player, Arrays.copyOfRange(args, 1, args.length));
    }
}
