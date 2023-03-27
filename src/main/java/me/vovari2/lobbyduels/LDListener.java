package me.vovari2.lobbyduels;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class LDListener implements Listener {

    private final LD plugin;
    LDListener(LD plugin){
        this.plugin = plugin;
    }

    // Вызов другого игрока на дуэль
    @EventHandler
    public void sendRequest(EntityDamageByEntityEvent event){
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player) || LDTaskSeconds.offClicks.contains(event.getDamager().getName()))
            return;

        Player player = (Player) event.getEntity(),
                damager = (Player) event.getDamager();
        String playerName = player.getName(),
                damagerName = damager.getName();

        LDTaskSeconds.offClicks.add(damagerName);

        // Проверка, вызова не существует или вызов отменен
        LDRequest request = plugin.getRequest(playerName, damagerName);
        if (request != null) {
            if (request.isCancel)
                damager.sendMessage(LDLocale.replacePlaceHolders("command.wait_send_request", "%time%", String.valueOf(getTimeToNextRequest(request.periodSecond))));
            return;
        }

        // Проверка, находится ли один из игроков в дуэле
        if (plugin.getDuel(playerName) != null || plugin.getDuel(damagerName) != null)
            return;

        // Сообщение о вызове на дуэль


        damager.sendMessage(LDLocale.replacePlaceHolders("command.you_send_request", "%player%", playerName));
        player.sendMessage(LDLocale.replacePlaceHolders("command.player_send_request", "%player%", damagerName));
        plugin.requests.add(new LDRequest(player, damager));
    }
    private int getTimeToNextRequest(int time) {
        int timer = LDTaskSeconds.seconds;
        if (time - timer > 0)
            return time - timer;
        else return time + plugin.periodRequests - timer;
    } // Время, сколько осталось до возможности следующего вызова на дуэль

    @EventHandler
    public void playerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        String playerName = player.getName();
        LDRequest request = LD.getInstance().getRequest(playerName);
        while(request != null){
            if (request.getPlayerTo() == player)
                request.getPlayerFrom().sendMessage(LDLocale.replacePlaceHolders("command.your_opponent_quit", "%player%", request.getPlayerTo().getName()));
            else request.getPlayerTo().sendMessage(LDLocale.replacePlaceHolders("command.your_opponent_quit", "%player%", request.getPlayerFrom().getName()));
            LD.getInstance().requests.remove(request);

            request = LD.getInstance().getRequest(playerName);
        }

        LDDuel duel = LD.getInstance().getDuel(playerName);
        if (duel != null){
            if (duel.getPlayerTo() == player)
                duel.getPlayerFrom().sendMessage(LDLocale.replacePlaceHolders("command.your_opponent_quit", "%player%", duel.getPlayerTo().getName()));
            else duel.getPlayerTo().sendMessage(LDLocale.replacePlaceHolders("command.your_opponent_quit", "%player%", duel.getPlayerFrom().getName()));
            LD.getInstance().duels.remove(duel);
        }
    }



    // Закрытие инвентаря меню выбора набора
    @EventHandler
    public void closeMenu(InventoryCloseEvent event){
        if (!(event.getPlayer() instanceof Player) || !event.getView().title().equals(LDLocale.getLocaleComponent("menu.name")))
            return;

        Player player = (Player) event.getPlayer();
        LDDuel duel = plugin.getDuel(player.getName());

        if (duel == null)
            return;

        if (duel.getVote(player) == 0)
            player.sendMessage(LDLocale.getLocaleComponent("menu.close_menu_and_not_voted"));
        else player.sendMessage(LDLocale.getLocaleComponent("menu.close_menu_and4_voted"));
    }

    // Закрытие инвентаря меню выбора набора
    @EventHandler
    public void clickMenu(InventoryClickEvent event){
        if (!(event.getWhoClicked() instanceof Player) || !event.getView().title().equals(LDLocale.getLocaleComponent("menu.name")) || event.getCurrentItem() == null)
            return;

        Player player = (Player) event.getWhoClicked();
        LDDuel duel = plugin.getDuel(player.getName());

        if (duel == null)
            return;

        ItemStack item = event.getCurrentItem();
        switch(item.getType()){
            case COD: {
                duel.giveVote(player, 1);
            } break;
            case IRON_HELMET: {
                duel.giveVote(player, 2);
            } break;
            case NETHERITE_HELMET: {
                duel.giveVote(player, 3);
            } break;
        }

        event.setCancelled(true);
    }
}
