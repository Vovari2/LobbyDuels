package me.vovari2.lobbyduels;

import me.vovari2.lobbyduels.objects.LDDuel;
import me.vovari2.lobbyduels.objects.LDWaitRequest;
import me.vovari2.lobbyduels.utils.TextUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class LDTaskSeconds extends BukkitRunnable {
    private final LD plugin;
    LDTaskSeconds(LD plugin){
        this.plugin = plugin;
        offClicks = new ArrayList<>();
        waitRequest = new ArrayList<>();
    }

    public static int seconds;
    public static List<String> offClicks; // Добавляется элемент, когда игрок вызывает на дуэль, очищается в конце каждой секунды (один удар в секунду)

    public static List<LDWaitRequest> waitRequest; // Для формирования задержки между дуэлями одних и тех же игроков
    public static LDWaitRequest getWaitRequest(String player1, String player2){
         for (LDWaitRequest waitRequest : waitRequest)
             if (waitRequest.equals(player1, player2))
                 return waitRequest;
         return null;
    }

    @Override
    public void run() {
        // Остановка выполнения, чтобы просто так не выполнялись команды ниже
        if (offClicks.isEmpty() && waitRequest.isEmpty() && plugin.requests.isEmpty() && plugin.duels.isEmpty())
            return;

        seconds++;
        if (seconds > 3599)
            seconds = 0;

        // Удаление вызова, если его срок вышел
        plugin.requests.removeIf(ldRequest -> { TextUtils.sendWarningMessage(getSecondsToTime(ldRequest.unitSecond) + ""); return ldRequest.unitSecond == seconds; });

        for (LDDuel duel : plugin.duels){
            int time = getSecondsToTime(duel.unitSecondPolling);
            if (time < 6 && time > 0){
                duel.getPlayerTo().sendMessage(LDLocale.replacePlaceHolders("menu.duel_start_in_time", "%time%", String.valueOf(time)));
                duel.getPlayerFrom().sendMessage(LDLocale.replacePlaceHolders("menu.duel_start_in_time", "%time%", String.valueOf(time)));
            }
            if (time == 0){
                duel.isGo = true;
                duel.getPlayerTo().closeInventory();
                duel.getPlayerTo().closeInventory();

                duel.getPlayerTo().sendMessage(LDLocale.getLocaleComponent("menu.duel_starting"));
                duel.getPlayerFrom().sendMessage(LDLocale.getLocaleComponent("menu.duel_starting"));
            }
        }

        // Удаление ожидающего игрока, если длительность ожидания уже закончилась (против спамеров дуэлей)
        waitRequest.removeIf(object -> object.getUnitSecond() == seconds);

        // Очистка списка с кликами игроков (против игроков кликеров)
        offClicks.clear();
    }

    public static int getSecondAfterPeriod(int period){
        int time = seconds + period;
        return time > 3599 ? time - 3600 : time;
    } // Число, в какую секунду нужно вызвать событие из промежутка 0 - 3599
    public static int getSecondsToTime(int time){
        return time < seconds ? 3600 - seconds + time : time - seconds;
    } // Количество секунд, оставшееся до вызова события
}
