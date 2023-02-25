package me.vovari2.lobbyduels;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class LD extends JavaPlugin {


    public static HashMap<String, LDRequest> requests;
    public static int delayRequests;

    public static LDTaskSeconds taskSeconds;

    @Override
    public void onEnable() {
        taskSeconds = new LDTaskSeconds();
        taskSeconds.runTaskTimer(this, 20L, 20L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
