package me.vovari2.lobbyduels.objects;

import me.vovari2.lobbyduels.*;
import me.vovari2.lobbyduels.utils.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LDDuel {
    public boolean isGo;

    public Inventory menuPolling;
    public int unitSecondPolling;

    private final LDDuelPlayer[] players;

    public LDDuel(Player playerTo, Player playerFrom, Inventory menuPolling){
        this.menuPolling = menuPolling;
        unitSecondPolling = LDTaskSeconds.getSecondAfterPeriod(LD.getInstance().durationPolling);
        this.players = new LDDuelPlayer[]{new LDDuelPlayer(playerTo), new LDDuelPlayer(playerFrom)};
    }

    public void giveVote(Player player, int index){
        players[getIndexPlayer(player)].numberKit = index;
        updateMenu();
    } // Указать выбор набора у игрока

    public int getVote(Player player){
        return players[getIndexPlayer(player)].numberKit;
    } // Выбранный набор у игрока (0 по умолчанию)
    public boolean getAlreadyVoted(Player player){
        return players[getIndexPlayer(player)].alreadyVoted;
    } // Значение того, что игрок уже проголосовал
    public void setAlreadyVoted(Player player, boolean value){
        players[getIndexPlayer(player)].alreadyVoted = value;
    }
    private int getIndexPlayer(Player player){
        return players[0].getPlayer().equals(player) ? 0 : 1;
    } // Индекса игрока в массиве

    public Player getPlayerTo() {
        return players[0].getPlayer();
    }
    public Player getPlayerFrom() {
        return players[1].getPlayer();
    }

    private int getAmountVotes(int index){
        int amount = 0;
        if (players[0].numberKit == index)
            amount++;
        if (players[1].numberKit == index)
            amount++;
        return amount;
    } // Количество голосов у одного набора

    public void updateMenu(){
        for (int index = 1; index < 4; index++){
            ItemStack item = menuPolling.getItem(2 * index);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.lore(LDLocale.replacePlaceHoldersList("menu.kit_start_" + index + ".lore", "%votes%", String.valueOf(getAmountVotes(index))));
            item.setItemMeta(itemMeta);
        }
    } // Обновление количество голосов у всех наборов


    public static void startDuel(LDRequest request){
        Player playerTo = request.getPlayerTo(), playerFrom = request.getPlayerFrom();
        Inventory inventory = InventoryUtils.createVotesInventory(playerTo);
        playerTo.openInventory(inventory);
        playerFrom.openInventory(inventory);
        LD.getInstance().duels.add(new LDDuel(playerTo, playerFrom, inventory));
        LD.getInstance().requests.remove(request);
    }
}
