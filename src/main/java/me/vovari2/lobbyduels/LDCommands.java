package me.vovari2.lobbyduels;

import com.sun.org.apache.bcel.internal.generic.LDC;
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
        if (args.length > 2){
            TextUtils.sendPlayerErrorMessage(player, "Слишком много параметров!");
            return true;
        }

        if (args.length > 0){
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

                TextUtils.sendPlayerChatMessage(player, TextUtils.getGradient() + "Вы приняли вызов игрока " + request.getPlayerFrom().getName() + " </gradient>");
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
                TextUtils.sendPlayerChatMessage(player, TextUtils.getGradient() + "Вы отклонил вызов игрока " + request.getPlayerFrom().getName() + " </gradient>");
                TextUtils.sendPlayerChatMessage(request.getPlayerFrom(), TextUtils.getGradient() + "Игрок " + request.getPlayerTo().getName() + " ваш вызов на дуэль! </gradient>");
                return true;
            }
        }

        return true;
    }
}
