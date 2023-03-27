package me.vovari2.lobbyduels;

import org.bukkit.entity.Player;

public class LDRequest {
    // Класс для хранения информации о запросах игроков на дуэли
    public int periodSecond;
    public boolean isCancel;

    private final Player playerTo;
    private final Player playerFrom;

    LDRequest(Player playerTo, Player playerFrom){
        this.periodSecond = LDTaskSeconds.seconds;
        this.playerTo = playerTo;
        this.playerFrom = playerFrom;
    }

    public Player getPlayerTo() {
        return playerTo;
    }
    public Player getPlayerFrom() {
        return playerFrom;
    }

}
