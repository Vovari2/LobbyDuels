package me.vovari2.lobbyduels;

import me.vovari2.lobbyduels.utils.ConfigUtils;
import me.vovari2.lobbyduels.utils.InventoryUtils;
import me.vovari2.lobbyduels.utils.TextUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class LD extends JavaPlugin {

    private static LD plugin;
    private HashMap<String, Component> localeStrings;
    private HashMap<String, List<Component>> localeLists;
    private HashMap<String, String> localeTexts;

    public World world;
    public int periodRequests;

    public List<LDRequest> requests;
    public List<LDDuel> duels;

    public LDTaskSeconds taskSeconds;

    @Override
    public void onEnable() {
        plugin = this;

        localeStrings = new HashMap<>();
        localeLists = new HashMap<>();
        localeTexts = new HashMap<>();

        try{
            ConfigUtils.Initialization();
        } catch(LDException error){
            TextUtils.sendWarningMessage(error.getMessage());
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        InventoryUtils.Initialization();

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
    public LDRequest getRequest(String player1, String plaer2){
        for (LDRequest request : requests)
            if (player1.equals(request.getPlayerTo().getName()) && plaer2.equals(request.getPlayerFrom().getName()) || plaer2.equals(request.getPlayerTo().getName()) && player1.equals(request.getPlayerFrom().getName()))
                return request;
        return null;
    }

    public LDDuel getDuel(String player){
        for (LDDuel duel : LD.getInstance().duels)
            if (duel.getPlayerTo().getName().equals(player) || duel.getPlayerFrom().getName().equals(player))
                return duel;
        return null;
    }

    public static LD getInstance(){
        return plugin;
    }
    public static ConsoleCommandSender getConsoleSender(){
        return plugin.getServer().getConsoleSender();
    }
    public static HashMap<String, Component> getLocaleStrings(){
        return plugin.localeStrings;
    }
    public static HashMap<String, List<Component>> getLocaleLists(){
        return plugin.localeLists;
    }
    public static HashMap<String, String> getLocaleTexts(){
        return plugin.localeTexts;
    }
}
