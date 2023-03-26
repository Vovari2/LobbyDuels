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
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
            return;

        Player player = (Player) event.getEntity(),
                damager = (Player) event.getDamager();

        if (plugin.hasRequest(player.getName(), damager.getName()))
            return;

        String playerName = player.getName(), damagerName = damager.getName();
        TextUtils.sendPlayerMessage(damager, LD.getLocaleTexts().get("command.you_send_request").replaceAll("%player", playerName));
        TextUtils.sendPlayerMessage(player, LD.getLocaleTexts().get("command.player_send_request").replaceAll("%player", damagerName));
        plugin.requests.add(new LDRequest(player, damager));
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

        player.openInventory(duel.inventoryView);
    }
}
