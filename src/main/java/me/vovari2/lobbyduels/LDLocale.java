package me.vovari2.lobbyduels;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LDLocale {

    public static LDLocale locale;

    private final HashMap<String, Object> localeTexts;
    private final HashMap<String, List<Component>> localeListComponent;
    private final HashMap<String, List<String>> localeListString;

    public LDLocale(HashMap<String, Object> localeTexts, HashMap<String, List<Component>> localeListComponent, HashMap<String, List<String>> localeListString){
        this.localeTexts = localeTexts;
        this.localeListComponent = localeListComponent;
        this.localeListString = localeListString;
    }

    public static Component getLocaleComponent(String key){
        return (Component) locale.localeTexts.get(key);
    }
    public static List<Component> getLocaleListComponent(String key){
        return locale.localeListComponent.get(key);
    }

    private static String getLocaleString(String key){
        return (String) locale.localeTexts.get(key);
    }
    private static List<String> getLocaleListString(String key){
        return locale.localeListString.get(key);
    }

    public static Component replacePlaceHolders(String key, String placeholder, String value){
        return MiniMessage.miniMessage().deserialize(getLocaleString(key).replaceAll(placeholder, value));
    }
    public static List<Component> replacePlaceHoldersList(String key, String placeholder, String value){
        List<Component> listComponents = new ArrayList<>();
        for (String str : getLocaleListString(key))
            listComponents.add(MiniMessage.miniMessage().deserialize(str.replaceAll(placeholder, value)));
        return listComponents;
    }
}
