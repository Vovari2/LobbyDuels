package me.vovari2.lobbyduels;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LDListener implements Listener {

    private final LD plugin;
    LDListener(LD plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void entityDamageByEntity(EntityDamageByEntityEvent event){
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
            return;

        Player player = (Player) event.getEntity(),
                damager = (Player) event.getDamager();

        if (plugin.hasRequest(player.getName(), damager.getName()))
            return;

        String damagerName = damager.getName();

        TextUtils.sendPlayerChatMessage(player, TextUtils.getGradient() + "Игрок " + damagerName + " вызвал вас на дуэль</gradient> " + TextUtils.getButtonAccept(damagerName) + " " + TextUtils.getButtonRefuse(damagerName));
        plugin.requests.add(new LDRequest(player, damager));
    }
}
