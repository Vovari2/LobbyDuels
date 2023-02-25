package me.vovari2.lobbyduels;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class TextUtils {
    public static void sendPlayerChatMessage(Player player, String message){
        player.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }
    public static void sendPlayerErrorMessage(Player player, String message){
        player.sendMessage(MiniMessage.miniMessage().deserialize("<bold><red>[!]</bold> <gray>" + message));
    }
    public static void sendConsoleWarningMessage(String message){
        LD.getInstance().getLogger().warning(message);
    }

    public static String getButtonAccept(String player){
        return "<hover:show_text:'<green>Принять вызов на дуэль'><click:run_command:/ld accept " + player +"><bold><green>[✓]</click></hover>";
    }
    public static String getButtonRefuse(String player){
        return "<hover:show_text:'<red>Отказаться от вызова на дуэль'><click:run_command:/ld cancel " + player +"><bold><red>[×]</click></hover>";
    }

    public static String getGradient(){
        return "<gradient:#00D1FF:#0038FF>";
    }
}
