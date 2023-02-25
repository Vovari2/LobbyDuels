package me.vovari2.lobbyduels;

import org.bukkit.scheduler.BukkitRunnable;

public class LDTaskSeconds extends BukkitRunnable {
    private final LD plugin;
    LDTaskSeconds(LD plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (LDRequest request : plugin.requests)
            TextUtils.sendConsoleWarningMessage(request.second + " " + plugin.delayRequests);
        plugin.requests.removeIf(ldRequest -> ldRequest.second >= plugin.delayRequests);
        if (!plugin.requests.isEmpty())
            for (LDRequest request : plugin.requests)
                request.second++;
    }
}
