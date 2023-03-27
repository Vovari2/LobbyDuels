package me.vovari2.lobbyduels.objects;

import me.vovari2.lobbyduels.LD;
import me.vovari2.lobbyduels.LDTaskSeconds;

public class LDWaitRequest {
    private final String player1;
    private final String player2;
    private final int unitSecond;

    public LDWaitRequest(String player1, String player2){
        this.player1 = player1;
        this.player2 = player2;
        unitSecond = LDTaskSeconds.getSecondAfterPeriod(LD.getInstance().durationBetweenRequest);
    }

    public boolean equals(String player1, String player2){
        return this.player1.equals(player1) && this.player2.equals(player2) || this.player1.equals(player2) && this.player2.equals(player1);
    }
    public int getUnitSecond(){
        return unitSecond;
    }
}
