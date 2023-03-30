package me.vovari2.lobbyduels.objects;

import me.vovari2.lobbyduels.*;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class LDDuel {
    public boolean isGo;

    public Inventory menuPolling;
    public int unitSecondPolling;

    private final List<LDDuelPlayer> players;

    public LDDuel(Player playerTo, Player playerFrom, Inventory menuPolling){
        this.menuPolling = menuPolling;
        unitSecondPolling = LDTaskSeconds.getSecondAfterPeriod(LD.getInstance().durationPolling);
        players = new ArrayList<>();
        players.add(new LDDuelPlayer(playerFrom));
        players.add(new LDDuelPlayer(playerTo));
    }

    public int getVote(Player player){
        for (LDDuelPlayer duelPlayer : players)
            if (duelPlayer.getPlayer().equals(player))
                return duelPlayer.numberKit;
        return -1;
    } // Выбранный набор у игрока (0 по умолчанию)
    public void setVote(Player player, int index){
        for (LDDuelPlayer duelPlayer : players)
            if (duelPlayer.getPlayer().equals(player))
                duelPlayer.numberKit = index;
        updateMenu();
    } // Указать выбор набора у игрока
    public boolean getAlreadyVoted(Player player){
        for (LDDuelPlayer duelPlayer : players)
            if (duelPlayer.getPlayer().equals(player)){
                return duelPlayer.alreadyVoted;
            }
        return false;
    } // Значение того, что игрок уже проголосовал
    public void setAlreadyVoted(Player player, boolean value){
        for (LDDuelPlayer duelPlayer : players)
            if (duelPlayer.getPlayer().equals(player)){
                duelPlayer.alreadyVoted = value;
                return;
            }
    }

    public int getAmountPlayers(){
        return players.size();
    }
    public boolean havePlayer(String playerName){
        for (LDDuelPlayer duelPlayer : players)
            if (duelPlayer.getPlayer().getName().equals(playerName))
                return true;
        return false;
    } // Проверяет есть ли игрок в дуэли
    public void addPlayer(Player player){
        players.add(new LDDuelPlayer(player));
    }
    public void removePlayer(Player player){
        players.removeIf(duelPlayer -> duelPlayer.getPlayer().equals(player));
    }

    private int getAmountVotes(int index){
        int amount = 0;
        for (LDDuelPlayer duelPlayer : players)
            if (duelPlayer.numberKit == index)
                amount++;
        return amount;
    } // Количество голосов у одного набора

    public void sendMessageAll(Component message){
        for (LDDuelPlayer duelPlayer : players)
            duelPlayer.getPlayer().sendMessage(message);
    }
    public void closeInventoryAll(){
        for (LDDuelPlayer duelPlayer : players)
            duelPlayer.getPlayer().closeInventory();
    }

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
        Inventory inventory = LDInventories.createVotesInventory(playerTo);
        playerTo.openInventory(inventory);
        playerFrom.openInventory(inventory);
        LD.getInstance().duels.add(new LDDuel(playerTo, playerFrom, inventory));
        LD.getInstance().requests.remove(request);
    }
}
