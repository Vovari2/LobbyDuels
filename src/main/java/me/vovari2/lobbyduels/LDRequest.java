package me.vovari2.lobbyduels;

import org.bukkit.entity.Player;

public class LDRequest {
    public int second;

    private final Player playerTo;
    private final Player playerFrom;

    public Player getPlayerTo() {
        return playerTo;
    }
    public Player getPlayerFrom() {
        return playerFrom;
    }

    LDRequest(Player playerTo, Player playerFrom){
        this.second = 0;
        this.playerTo = playerTo;
        this.playerFrom = playerFrom;
    }

}
