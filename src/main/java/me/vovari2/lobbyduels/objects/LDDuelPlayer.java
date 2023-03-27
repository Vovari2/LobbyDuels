package me.vovari2.lobbyduels.objects;

import org.bukkit.entity.Player;

public class LDDuelPlayer {
    private final Player player;
    public int numberKit;
    public boolean alreadyVoted;

    public LDDuelPlayer(Player player){
        this.player = player;
        numberKit = 0;
        alreadyVoted = false;
    }

    public Player getPlayer(){
        return player;
    }

}
