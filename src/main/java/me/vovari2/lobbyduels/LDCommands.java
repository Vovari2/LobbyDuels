package me.vovari2.lobbyduels;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LDCommands implements CommandExecutor {
    private LD plugin;
    LDCommands(LD plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)){
            TextUtils.sendConsoleWarningMessage("Эту команду может использовать только игрок!");
            return true;
        }

        Player player = (Player) sender;
        if (args.length < 1){
            TextUtils.sendPlayerErrorMessage(player, "Команда введена неверно!");
            return true;
        }

        if (args[0].equals("accept")){
            LDRequest request;
            if (args.length == 2){
                request = plugin.getRequest(player.getName(), args[1]);
                if (request == null){
                    TextUtils.sendPlayerErrorMessage(player, "Вы не имеете брошенных вам вызовов от этого игрока!");
                    return true;
                }
            }
            else {
                request = plugin.getRequest(player.getName());
                if (request == null){
                    TextUtils.sendPlayerErrorMessage(player, "Вы не имеете брошенных вам вызовов!");
                    return true;
                }
            }

            TextUtils.sendPlayerChatMessage(player, TextUtils.getGradient() + "Вы приняли вызов игрока <gray>" + request.getPlayerFrom().getName() + "</gray> </gradient>");
            TextUtils.sendPlayerChatMessage(request.getPlayerFrom(), TextUtils.getGradient() + "Игрок <gray>" + request.getPlayerTo().getName() + "</gray> принял ваш вызов на дуэль </gradient>");
            return true;
        }
        if (args[0].equals("cancel")){
            LDRequest request;
            if (args.length == 2){
                request = plugin.getRequest(player.getName(), args[1]);
                if (request == null){
                    TextUtils.sendPlayerErrorMessage(player, "Вы не имеете брошенных вам вызовов от этого игрока!");
                    return true;
                }
            }
            else {
                request = plugin.getRequest(player.getName());
                if (request == null){
                    TextUtils.sendPlayerErrorMessage(player, "Вы не имеете брошенных вам вызовов!");
                    return true;
                }
            }

            plugin.requests.remove(request);
            TextUtils.sendPlayerChatMessage(player, TextUtils.getGradient() + "Вы отклонил вызов игрока <gray>" + request.getPlayerFrom().getName() + "</gray> </gradient>");
            TextUtils.sendPlayerChatMessage(request.getPlayerFrom(), TextUtils.getGradient() + "Игрок <gray>" + request.getPlayerTo().getName() + "</gray> отклонил ваш вызов на дуэль </gradient>");
            return true;
        }
        TextUtils.sendPlayerErrorMessage(player, "Команда введена неверно!");

        return true;
    }
}
