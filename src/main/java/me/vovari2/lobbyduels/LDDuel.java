package me.vovari2.lobbyduels;

import me.vovari2.lobbyduels.utils.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class LDDuel {
    public boolean isGo;
    public Inventory inventory;

    private final Player playerTo;
    private final Player playerFrom;

    public int[] votes;

    public LDDuel(Player playerTo, Player playerFrom, Inventory inventory){
        this.inventory = inventory;

        this.playerTo = playerTo;
        this.playerFrom = playerFrom;

        votes = new int[3];
    }

    public Player getPlayerTo() {
        return playerTo;
    }
    public Player getPlayerFrom() {
        return playerFrom;
    }

    public static void startDuel(LDRequest request){
        Player playerTo = request.getPlayerTo(), playerFrom = request.getPlayerFrom();
        Inventory inventory = InventoryUtils.openVotesInventory(playerTo);
        playerTo.openInventory(inventory);
        playerFrom.openInventory(inventory);
        LD.getInstance().duels.add(new LDDuel(playerTo, playerFrom, inventory));
    }
}
