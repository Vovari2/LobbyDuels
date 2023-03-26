package me.vovari2.lobbyduels;

import me.vovari2.lobbyduels.utils.TextUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class LDTaskSeconds extends BukkitRunnable {
    private final LD plugin;
    LDTaskSeconds(LD plugin){
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (LDRequest request : plugin.requests)
            TextUtils.sendWarningMessage(request.second + " " + plugin.periodRequests);
        plugin.requests.removeIf(ldRequest -> ldRequest.second >= plugin.periodRequests);
        if (!plugin.requests.isEmpty())
            for (LDRequest request : plugin.requests)
                request.second++;
    }
}
