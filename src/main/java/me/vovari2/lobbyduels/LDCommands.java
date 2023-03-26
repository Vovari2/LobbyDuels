package me.vovari2.lobbyduels;

import me.vovari2.lobbyduels.utils.TextUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LDCommands implements CommandExecutor {
    private final LD plugin;
    LDCommands(LD plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)){
            TextUtils.sendSenderMessage(sender, LD.getLocaleTexts().get("command.use_only_player"));
            return true;
        }

        Player player = (Player) sender;
        if (args.length < 1){
            player.sendMessage(LD.getLocaleStrings().get("command.command_incorrectly"));
            return true;
        }

        if (args[0].equals("accept")){
            LDRequest request;
            if (args.length == 2){
                request = plugin.getRequest(player.getName(), args[1]);
                if (request == null){
                    player.sendMessage(LD.getLocaleStrings().get("command.have_not_request_from_player"));
                    return true;
                }
            }
            else {
                request = plugin.getRequest(player.getName(), true);
                if (request == null){
                    player.sendMessage(LD.getLocaleStrings().get("command.have_not_request_for_you"));
                    return true;
                }
            }

            Player playerTo = request.getPlayerTo(),
                    playerFrom = request.getPlayerFrom();

            if (playerFrom.getLocation().getWorld() != plugin.world || playerTo.getLocation().getWorld() != plugin.world || playerTo.getLocation().distance(playerFrom.getLocation()) > 5 ){
                player.sendMessage(LD.getLocaleStrings().get("command.distance_too_big"));
                return true;
            }

            LDDuel.startDuel(request);

            TextUtils.sendPlayerMessage(playerTo, LD.getLocaleTexts().get("command.you_access_request").replaceAll("%player%", playerFrom.getName()));
            TextUtils.sendPlayerMessage(playerFrom, LD.getLocaleTexts().get("command.player_access_request").replaceAll("%player%", playerTo.getName()));
            return true;
        }
        if (args[0].equals("cancel")){
            LDRequest request;
            if (args.length == 2){
                request = plugin.getRequest(player.getName(), args[1]);
                if (request == null){
                    player.sendMessage(LD.getLocaleStrings().get("command.have_not_request_from_player"));
                    return true;
                }
            }
            else {
                request = plugin.getRequest(player.getName(), false);
                if (request == null){
                    player.sendMessage(LD.getLocaleStrings().get("command.have_not_request_for_you"));
                    return true;
                }
            }

            Player playerTo = request.getPlayerTo(),
                    playerFrom = request.getPlayerFrom();

            plugin.requests.remove(request);

            if (player == playerTo){
                TextUtils.sendPlayerMessage(playerTo, LD.getLocaleTexts().get("command.you_cancel_player_request").replaceAll("%player%", playerFrom.getName()));
                TextUtils.sendPlayerMessage(playerFrom,  LD.getLocaleTexts().get("command.player_cancel_your_request").replaceAll("%player%", playerTo.getName()));
            }
            else{
                TextUtils.sendPlayerMessage(playerTo, LD.getLocaleTexts().get("command.player_cancel_player_request").replaceAll("%player%", playerFrom.getName()));
                TextUtils.sendPlayerMessage(playerFrom,  LD.getLocaleTexts().get("command.you_cancel_your_request").replaceAll("%player%", playerTo.getName()));
            }
            return true;
        }
        player.sendMessage(LD.getLocaleStrings().get("command.command_incorrectly"));

        return true;
    }
}
