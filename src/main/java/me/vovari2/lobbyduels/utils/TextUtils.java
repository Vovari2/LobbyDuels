package me.vovari2.lobbyduels.utils;

import me.vovari2.lobbyduels.LD;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class TextUtils {

    public static void sendInfoMessage(String message){
        LD.getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize("[LobbyDuels] <green>" + message));
    }
    public static void sendWarningMessage(String message){
        LD.getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize("[LobbyDuels] <yellow>" + message));
    }
}
