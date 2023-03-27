package me.vovari2.lobbyduels.utils;

import me.vovari2.lobbyduels.LD;
import me.vovari2.lobbyduels.LDException;
import me.vovari2.lobbyduels.LDLocale;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigUtils {
    private static File dataFolder;
    private static FileConfiguration loadConfiguration(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void Initialization() throws LDException {
        dataFolder = LD.getInstance().getDataFolder();

        // Загрузка основного файла конфигурации
        if (dataFolder.mkdir())
            TextUtils.sendInfoMessage("Folder \"LobbyDuels\" in \"plugins\" was created!");

        File file = new File(dataFolder, "config.yml");
        if (!file.exists())
            LD.getInstance().saveResource("config.yml", false);
        FileConfiguration config = loadConfiguration(file);

        loadSettings(config);

        // Загрузка файлов локализации
        if (new File(dataFolder, "locales").mkdir())
            TextUtils.sendInfoMessage("Folder \"locales\" was created!");

        if (!new File(dataFolder, "locales\\default.yml").exists())
            LD.getInstance().saveResource("locales\\default.yml", false);

        loadLocale(config);
    }

    // Загрузка параметров основного конфига
    private static void loadSettings(FileConfiguration config) throws LDException {
        // Получение мира из конфига
        String nameWorld = config.getString("world");
        if (nameWorld == null)
            throw new LDException("Value \"world\" must not be empty!");

        World world = Bukkit.getWorld(nameWorld);
        if (world == null)
            throw new LDException("World \"" + nameWorld + "\" does not exist!");

        LD.getInstance().world = world;


        // Получение периода между вызовами
        int periodRequests = config.getInt("period_requests");
        if (periodRequests < 1)
            throw new LDException("Value \"period_requests\" must be greater than 0!");

        LD.getInstance().periodRequests = config.getInt("period_requests");
    }


    // Загрузка локализации плагина
    private static void loadLocale(FileConfiguration config) throws LDException{
        // Получение название файла локализации
        String locale = config.getString("locale");
        if (locale == null || locale.equals("")){
            TextUtils.sendWarningMessage("Value \"locale\" must not be empty!");
            locale = "default";
        }

        if (!new File(dataFolder, "locales\\" + locale + ".yml").exists()){
            TextUtils.sendWarningMessage("Not exist locale file \"" + locale + ".yml\" in folder locales!");
            locale = "default";
        }

        if (!new File(dataFolder, "locales\\default.yml").exists())
            throw new LDException("Not exist locale file \"default.yml\" in folder locales!");


        //
        // Загрузка всех надписей локализации
        FileConfiguration configLocale = loadConfiguration(new File(dataFolder,  "locales\\" + locale + ".yml"));

        // Надписи, которые сразу можно конвертировать в Component
        String[] array = new String[] {
                "menu.name",
                "command.use_only_player",
                "menu.kit_start_1.name",
                "menu.kit_start_2.name",
                "menu.kit_start_3.name",
                "menu.background.name",
                "command.command_incorrectly",
                "command.have_not_request_from_player",
                "command.have_not_request_for_you",
                "command.distance_too_big",
                "command.you_already_have_duel",
                "command.player_already_have_duel",
                "command.you_are_not_in_duel",
                "menu.close_menu_and_voted",
                "menu.close_menu_and_not_voted"
        };
        HashMap<String, Object> localeTexts = new HashMap<>();
        for (String str : array)
            localeTexts.put(str, convertStringToComponent(configLocale.getString(str), str, locale));

        // Надписи, которые нужно оставить в виде строк
        array = new String[] {
                "command.wait_send_request",
                "command.you_send_request",
                "command.player_send_request",
                "command.you_access_request",
                "command.player_access_request",
                "command.you_cancel_your_request",
                "command.you_cancel_player_request",
                "command.player_cancel_your_request",
                "command.player_cancel_player_request",
                "command.your_opponent_quit"
        };
        for (String str : array)
            localeTexts.put(str, checkString(configLocale.getString(str), str, locale));

        // Списки надписей, которые можно сразу конвертировать в списки Component
        array = new String[] {
                "menu.background.lore"
        };
        HashMap<String, List<Component>> localeListComponent = new HashMap<>();
        for (String str : array)
            localeListComponent.put(str, convertStringListToComponent(configLocale.getStringList(str), str, locale));

        // Списки надписей, которые нужно оставить в виде строк
        array = new String[] {
                "menu.kit_start_1.lore",
                "menu.kit_start_2.lore",
                "menu.kit_start_3.lore"
        };
        HashMap<String, List<String>> localeListString = new HashMap<>();
        for (String str : array){
            List<String> listStrings = new ArrayList<>();
            for (String str2 : configLocale.getStringList(str))
                listStrings.add(checkString(str2, str, locale));
            localeListString.put(str, listStrings);
        }

        // Запись всех списков в основные переменные
        LDLocale.locale = new LDLocale(localeTexts, localeListComponent, localeListString);

        TextUtils.sendInfoMessage("Locale file \"" + locale + ".yml\" was loaded!");
    }

    private static String checkString(String text, String path, String locale){
        if (text == null){
            TextUtils.sendWarningMessage("Title \"" + path + "\" in \"" + locale + "\" not exists! An empty string will be given");
            return "";
        }
        if (text.contains("&") || text.contains("§")){
            TextUtils.sendWarningMessage("Title \"" + path + "\" in \"" + locale + "\" must not have char \"&\" or \"§\"! An empty string will be given");
            return "";
        }
        return text;
    } // Проверяет строку на null и на символы & или §
    private static Component convertStringToComponent(String text, String path, String locale){
        return MiniMessage.miniMessage().deserialize(checkString(text, path, locale));
    } // С проверкой конвертирует строку в Component
    private static List<Component> convertStringListToComponent(List<String> texts, String path, String locale){
        List<Component> listComponent = new ArrayList<>();
        for (String text : texts)
            listComponent.add(convertStringToComponent(text, path, locale));
        return listComponent;
    } // С проверкой конвертирует список строк в список Component
}
