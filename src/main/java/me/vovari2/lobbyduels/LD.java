package me.vovari2.lobbyduels;

import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class LD extends JavaPlugin {

    private static LD plugin;
    public static LD getInstance(){
        return plugin;
    }

    public World world;
    public int delayRequests;

    public List<LDRequest> requests;

    public LDTaskSeconds taskSeconds;

    @Override
    public void onEnable() {
        plugin = this;

        requests = new ArrayList<>();

        // Загрузка конфигов
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists())
            saveResource("config.yml", false);

        try{ getConfig().load(file); }
        catch(InvalidConfigurationException | IOException error){ TextUtils.sendConsoleWarningMessage("Не удалось загрузить файл \"config.yml\": \n" + error.getMessage()); }

        String worldName = getConfig().getString("world");
        if (worldName == null)
            TextUtils.sendConsoleWarningMessage("Мир в конфиге указан не верно!");
        else world = getServer().getWorld(worldName);

        delayRequests = getConfig().getInt("delay_request");

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

    public LDRequest getRequest(String playerTo){
        for (LDRequest request : requests)
            if (playerTo.equals(request.getPlayerTo().getName()))
                return request;
        return null;
    }
    public LDRequest getRequest(String playerTo, String playerFrom){
        for (LDRequest request : requests)
            if (playerTo.equals(request.getPlayerTo().getName()) && playerFrom.equals(request.getPlayerFrom().getName()))
                return request;
        return null;
    }

    public boolean hasRequest(String playerTo, String playerFrom) {
        if (requests.isEmpty())
            return false;

        for (LDRequest request : requests)
            if (request.equals(playerTo, playerFrom))
                return true;
        return false;
    }
}
