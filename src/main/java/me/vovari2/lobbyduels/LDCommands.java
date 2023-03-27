package me.vovari2.lobbyduels;

import me.vovari2.lobbyduels.objects.LDDuel;
import me.vovari2.lobbyduels.objects.LDRequest;
import me.vovari2.lobbyduels.objects.LDWaitRequest;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
            sender.sendMessage(LDLocale.getLocaleComponent("command.use_only_player"));
            return true;
        }

        Player player = (Player) sender;
        if (args.length < 1){
            player.sendMessage(LDLocale.getLocaleComponent("command.command_incorrectly"));
            return true;
        }

        if (args[0].equals("accept")){
            LDRequest request;
            if (args.length == 2){
                request = plugin.getRequest(args[1], player.getName());
                if (request == null){
                    player.sendMessage(LDLocale.getLocaleComponent("command.have_not_request_from_player"));
                    return true;
                }
            }
            else {
                request = plugin.getRequest(player.getName(), true);
                if (request == null){
                    player.sendMessage(LDLocale.getLocaleComponent("command.have_not_request_for_you"));
                    return true;
                }
            }

            Player playerTo = request.getPlayerTo(),
                    playerFrom = request.getPlayerFrom();

            // Проверка, находится ли один из игроков в дуэле
            if (plugin.getDuel(playerTo.getName()) != null){
                playerTo.sendMessage(LDLocale.getLocaleComponent("command.you_already_have_duel"));
                return true;
            }
            if (plugin.getDuel(playerFrom.getName()) != null){
                playerTo.sendMessage(LDLocale.getLocaleComponent("command.player_already_have_duel"));
                return true;
            }

            // Проверка на расстояние между игроками
            if (playerFrom.getLocation().getWorld() != plugin.world || playerTo.getLocation().getWorld() != plugin.world || playerTo.getLocation().distance(playerFrom.getLocation()) > 5 ){
                player.sendMessage(LDLocale.getLocaleComponent("command.distance_too_big"));
                return true;
            }

            LDDuel.startDuel(request);

            // Сообщение, что вызов на дуэль принят
            playerTo.sendMessage(LDLocale.replacePlaceHolders("command.you_access_request", "%player%", playerFrom.getName()));
            playerFrom.sendMessage(LDLocale.replacePlaceHolders("command.player_access_request", "%player%", playerTo.getName()));

            playerTo.sendMessage(MiniMessage.miniMessage().deserialize("<newline>").append(LDLocale.replacePlaceHolders("menu.duel_start_in_time", "%time%", String.valueOf(LD.getInstance().durationPolling))));
            playerFrom.sendMessage(MiniMessage.miniMessage().deserialize("<newline>").append(LDLocale.replacePlaceHolders("menu.duel_start_in_time", "%time%", String.valueOf(LD.getInstance().durationPolling))));
            return true;
        }
        if (args[0].equals("cancel")){
            LDRequest request;
            if (args.length == 2){
                request = plugin.getRequest(args[1], player.getName());
                if (request == null){
                    player.sendMessage(LDLocale.getLocaleComponent("command.have_not_request_from_player"));
                    return true;
                }
            }
            else {
                request = plugin.getRequest(player.getName(), false);
                if (request == null){
                    player.sendMessage(LDLocale.getLocaleComponent("command.have_not_request_for_you"));
                    return true;
                }
            }

            Player playerTo = request.getPlayerTo(),
                    playerFrom = request.getPlayerFrom();

            String namePlayerTo = playerTo.getName(),
                    namePlayerFrom = playerFrom.getName();


            LDTaskSeconds.waitRequest.add(new LDWaitRequest(namePlayerTo, namePlayerFrom));
            LD.getInstance().requests.remove(request);

            // Сообщение, что вызов на дуэль отклонен
            if (player == playerTo){
                playerTo.sendMessage(LDLocale.replacePlaceHolders("command.you_cancel_player_request", "%player%", namePlayerFrom));
                playerFrom.sendMessage(LDLocale.replacePlaceHolders("command.player_cancel_your_request", "%player%", namePlayerTo));
            }
            else{
                playerTo.sendMessage(LDLocale.replacePlaceHolders("command.player_cancel_player_request", "%player%", namePlayerFrom));
                playerFrom.sendMessage(LDLocale.replacePlaceHolders("command.you_cancel_your_request", "%player%", namePlayerTo));
            }
            return true;
        }
        if (args[0].equals("vote")){
            if (args.length > 1){
                player.sendMessage(LDLocale.getLocaleComponent("command.command_incorrectly"));
                return true;
            }

            // Проверка, находится ли игрок в дуэле
            LDDuel duel = LD.getInstance().getDuel(player.getName());
            if (duel == null){
                player.sendMessage(LDLocale.getLocaleComponent("command.you_are_not_in_duel"));
                return true;
            }

            // Проверка, находится ли дуэль на этапе голосования
            if (duel.isGo){
                player.sendMessage(LDLocale.getLocaleComponent("command.you_already_have_duel"));
                return true;
            }

            player.openInventory(duel.menuPolling);
            return true;
        }
        player.sendMessage(LDLocale.getLocaleComponent("command.command_incorrectly"));

        return true;
    }
}
