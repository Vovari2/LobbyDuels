package me.vovari2.lobbyduels;

import me.vovari2.lobbyduels.utils.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

public class LDDuel {
    public boolean isGo;

    public InventoryView inventoryView;

    private final Player playerTo;
    private final Player playerFrom;

    public int[] votes;

    public LDDuel(Player playerTo, Player playerFrom, InventoryView inventoryView){

        isGo = false;

        this.inventoryView = inventoryView;

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
        InventoryView inventoryView = playerTo.openInventory(InventoryUtils.openVotesInventory(playerTo));
        playerFrom.openInventory(inventoryView);
        LD.getInstance().duels.add(new LDDuel(playerTo, playerFrom, inventoryView));
    }
}
