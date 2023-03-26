package me.vovari2.lobbyduels;

import me.vovari2.lobbyduels.utils.TextUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class LDTaskSeconds extends BukkitRunnable {
    private final LD plugin;
    LDTaskSeconds(LD plugin){
        this.plugin = plugin;
        offClicks = new ArrayList<>();
    }

    public static int seconds;
    public static List<String> offClicks;

    @Override
    public void run() {
        if (plugin.requests.isEmpty())
            return;

        seconds++;
        if (seconds == plugin.periodRequests)
            seconds = 0;
        plugin.requests.removeIf(ldRequest -> { TextUtils.sendWarningMessage(seconds + " " + ldRequest.periodSecond); return ldRequest.periodSecond == seconds; });

        offClicks.clear();
    }
}
