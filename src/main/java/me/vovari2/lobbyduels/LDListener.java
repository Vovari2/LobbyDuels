package me.vovari2.lobbyduels;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LDListener implements Listener {

    @EventHandler
    public void entityDamageByEntity(EntityDamageByEntityEvent event){
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
            return;

        Player player = (Player) event.getEntity(),
                damager = (Player) event.getDamager();


    }
}
