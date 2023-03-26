package me.vovari2.lobbyduels;

import me.vovari2.lobbyduels.utils.TextUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class LDListener implements Listener {

    private final LD plugin;
    LDListener(LD plugin){
        this.plugin = plugin;
    }

    // Вызов другого игрока на дуэль
    @EventHandler
    public void entityDamageByEntity(EntityDamageByEntityEvent event){
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player) || LDTaskSeconds.offClicks.contains(event.getDamager().getName()))
            return;

        Player player = (Player) event.getEntity(),
                damager = (Player) event.getDamager();
        String playerName = player.getName(),
                damagerName = damager.getName();

        LDTaskSeconds.offClicks.add(damagerName);

        LDRequest request = plugin.getRequest(playerName, damagerName);
        if (request != null) {
            if (request.isCancel)
                TextUtils.sendPlayerMessage(damager, LD.getLocaleTexts().get("command.wait_send_request").replaceAll("%time%", String.valueOf(getTimeToNextRequest(request.periodSecond))));
            return;
        }

        TextUtils.sendPlayerMessage(damager, LD.getLocaleTexts().get("command.you_send_request").replaceAll("%player%", playerName));
        TextUtils.sendPlayerMessage(player, LD.getLocaleTexts().get("command.player_send_request").replaceAll("%player%", damagerName));
        plugin.requests.add(new LDRequest(player, damager));
    }
    private int getTimeToNextRequest(int time) {
        int timer = LDTaskSeconds.seconds;
        if (time - timer > 0)
            return time - timer;
        else return time + plugin.periodRequests - timer;
    }

    @EventHandler
    public void playerInventoryClose(InventoryCloseEvent event){
        if (!(event.getPlayer() instanceof Player))
            return;

        Player player = (Player) event.getPlayer();
        LDDuel duel = plugin.getDuel(player.getName());

        if (duel == null)
            return;

        if (duel.isGo)
            return;

    }
}
