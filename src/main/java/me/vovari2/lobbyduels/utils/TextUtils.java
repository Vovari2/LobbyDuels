package me.vovari2.lobbyduels.utils;

import me.vovari2.lobbyduels.LD;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextUtils {

    public static void sendPlayerMessage(Player player, String message){
        player.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }
    public static void sendSenderMessage(CommandSender sender, String message){
        sender.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }
    public static void sendInfoMessage(String message){
        LD.getServerLogger().info(message);
    }
    public static void sendWarningMessage(String message){
        LD.getServerLogger().warning(message);
    }
}
