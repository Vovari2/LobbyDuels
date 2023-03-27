package me.vovari2.lobbyduels;

import me.vovari2.lobbyduels.utils.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LDDuel {
    public boolean isGo;
    public Inventory inventory;

    private final Player[] players;
    private final int[] votes;

    public LDDuel(Player playerTo, Player playerFrom, Inventory inventory){
        this.inventory = inventory;

        this.players = new Player[]{playerTo, playerFrom};

        votes = new int[]{0,0};
    }

    public void giveVote(Player player, int index){
        votes[getIndexPlayer(player)] = index;
        updateMenu();
    }
    public int getVote(Player player){
        return votes[getIndexPlayer(player)];
    }
    private int getIndexPlayer(Player player){
        return players[0].equals(player) ? 0 : 1;
    }

    public Player getPlayerTo() {
        return players[0];
    }
    public Player getPlayerFrom() {
        return players[1];
    }

    private int getAmountVotes(int index){
        int amount = 0;
        if (votes[0] == index)
            amount++;
        if (votes[1] == index)
            amount++;
        return amount;
    }

    public void updateMenu(){
        for (int index = 1; index < 4; index++){
            ItemStack item = inventory.getItem(2 * index);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.lore(LDLocale.replacePlaceHoldersList("menu.kit_start_" + index + ".lore", "%votes%", String.valueOf(getAmountVotes(index))));
            item.setItemMeta(itemMeta);
        }
    }

    public static void startDuel(LDRequest request){
        Player playerTo = request.getPlayerTo(), playerFrom = request.getPlayerFrom();
        Inventory inventory = InventoryUtils.createVotesInventory(playerTo);
        playerTo.openInventory(inventory);
        playerFrom.openInventory(inventory);
        LD.getInstance().duels.add(new LDDuel(playerTo, playerFrom, inventory));
        LD.getInstance().requests.remove(request);
    }
}
