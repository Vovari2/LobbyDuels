package me.vovari2.lobbyduels;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LDTabCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1){
            List<String> subCommands = new ArrayList<>();
            subCommands.add("accept");
            subCommands.add("cancel");
            subCommands.removeIf(str -> !str.toLowerCase().startsWith(args[0].toLowerCase()));
            return subCommands;
        }
        if (args.length == 2){
            List<String> players = getListPlayers();
            players.removeIf(str -> !str.toLowerCase().startsWith(args[1].toLowerCase()));
            return players;
        }
        return new ArrayList<>();
    }

    public List<String> getListPlayers(){
        List<String> list = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers())
            list.add(player.getName());
        return list;
    }
}
