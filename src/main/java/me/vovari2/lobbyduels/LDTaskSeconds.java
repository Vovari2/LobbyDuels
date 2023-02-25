package me.vovari2.lobbyduels;

import org.bukkit.scheduler.BukkitRunnable;

public class LDTaskSeconds extends BukkitRunnable {

    @Override
    public void run() {
        if (!LD.requests.isEmpty())
            for (String playerName : LD.requests.keySet()){
                LDRequest request = LD.requests.get(playerName);
                if (request.second >= LD.delayRequests)
                    LD.requests.remove(playerName);
                else request.second++;
            }
    }
}
