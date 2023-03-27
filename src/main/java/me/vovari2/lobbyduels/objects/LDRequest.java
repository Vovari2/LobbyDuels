package me.vovari2.lobbyduels.objects;

import me.vovari2.lobbyduels.LD;
import me.vovari2.lobbyduels.LDTaskSeconds;
import org.bukkit.entity.Player;

public class LDRequest {
    // Класс для хранения информации о запросах игроков на дуэли
    public int unitSecond;

    private final Player playerTo;
    private final Player playerFrom;

    public LDRequest(Player playerTo, Player playerFrom){
        this.unitSecond = LDTaskSeconds.getSecondAfterPeriod(LD.getInstance().durationRequest);
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
