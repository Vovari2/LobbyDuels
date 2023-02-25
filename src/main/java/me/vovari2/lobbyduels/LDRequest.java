package me.vovari2.lobbyduels;

import org.bukkit.entity.Player;

import java.util.Objects;

public class LDRequest {
    // Класс для хранения информации о запросах игроков на дуэли
    public int second;

    private final Player playerTo;
    private final Player playerFrom;

    public Player getPlayerTo() {
        return playerTo;
    }
    public Player getPlayerFrom() {
        return playerFrom;
    }

    public boolean equals(String playerTo, String playerFrom){
        return Objects.equals(this.playerFrom.getName(), playerFrom) && Objects.equals(this.playerTo.getName(), playerTo);
    }

    LDRequest(Player playerTo, Player playerFrom){
        this.second = 0;
        this.playerTo = playerTo;
        this.playerFrom = playerFrom;
    }

}
