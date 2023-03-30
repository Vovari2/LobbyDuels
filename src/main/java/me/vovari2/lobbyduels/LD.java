package me.vovari2.lobbyduels;

import me.vovari2.lobbyduels.objects.LDDuel;
import me.vovari2.lobbyduels.objects.LDException;
import me.vovari2.lobbyduels.objects.LDRequest;
import me.vovari2.lobbyduels.utils.ConfigUtils;
import me.vovari2.lobbyduels.utils.TextUtils;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class LD extends JavaPlugin {

    private static LD plugin;

    public World world;

    public int durationRequest;
    public int durationBetweenRequest;
    public int durationPolling;

    public int maxPlayers;

    public List<LDRequest> requests;
    public List<LDDuel> duels;

    public LDTaskSeconds taskSeconds;

    @Override
    public void onEnable() {
        plugin = this;

        try{
            ConfigUtils.Initialization();
        } catch(LDException error){
            TextUtils.sendWarningMessage(error.getMessage());
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        LDInventories.inventories = new LDInventories();

        requests = new ArrayList<>();
        duels = new ArrayList<>();

        getServer().getPluginManager().registerEvents(new LDListener(plugin), plugin);
        getCommand("lobbyduels").setExecutor(new LDCommands(plugin));
        getCommand("lobbyduels").setTabCompleter(new LDTabCompleter());

        taskSeconds = new LDTaskSeconds(plugin);
        taskSeconds.runTaskTimer(this, 20L, 20L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public LDRequest getRequest(String player, boolean isToPlayer){
        if (isToPlayer){
            for (LDRequest request : requests)
                if (player.equals(request.getPlayerTo().getName()))
                    return request;
        }
        else
            for (LDRequest request : requests)
                    if (player.equals(request.getPlayerFrom().getName()) || player.equals(request.getPlayerTo().getName()))
                        return request;
        return null;
    }
    public LDRequest getRequest(String player1, String player2){
        for (LDRequest request : requests)
            if (player1.equals(request.getPlayerTo().getName()) && player2.equals(request.getPlayerFrom().getName()) || player2.equals(request.getPlayerTo().getName()) && player1.equals(request.getPlayerFrom().getName()))
                return request;
        return null;
    }
    public LDRequest getRequest(String player){
        for (LDRequest request : requests)
            if (player.equals(request.getPlayerFrom().getName()) || player.equals(request.getPlayerTo().getName()))
                return request;
        return null;
    }

    public LDDuel getDuel(String player){
        for (LDDuel duel : LD.getInstance().duels)
            if (duel.havePlayer(player))
                return duel;
        return null;
    }

    public static LD getInstance(){
        return plugin;
    }
    public static ConsoleCommandSender getConsoleSender(){
        return plugin.getServer().getConsoleSender();
    }
}
