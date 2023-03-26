package me.vovari2.lobbyduels.utils;

import me.vovari2.lobbyduels.LD;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryUtils {
    public static ItemStack kitOne;
    public static ItemStack kitTwo;
    public static ItemStack kitThree;

    public static ItemStack emptySlot;

    public static Inventory defaultVotes;

    public static void Initialization(){
        kitOne = new ItemStack(Material.COD);
        ItemMeta itemMeta = kitOne.getItemMeta();
        itemMeta.displayName(LD.getLocaleStrings().get("menu.kit_start_1.name"));
        itemMeta.lore(LD.getLocaleLists().get("menu.kit_start_1.lore"));
        kitOne.setItemMeta(itemMeta);
        kitOne.setAmount(1);

        kitTwo = new ItemStack(Material.IRON_HELMET);
        itemMeta = kitTwo.getItemMeta();
        itemMeta.displayName(LD.getLocaleStrings().get("menu.kit_start_2.name"));
        itemMeta.lore(LD.getLocaleLists().get("menu.kit_start_2.lore"));
        kitTwo.setItemMeta(itemMeta);
        kitTwo.setAmount(1);

        kitThree = new ItemStack(Material.NETHERITE_HELMET);
        itemMeta = kitThree.getItemMeta();
        itemMeta.displayName(LD.getLocaleStrings().get("menu.kit_start_3.name"));
        itemMeta.lore(LD.getLocaleLists().get("menu.kit_start_3.lore"));
        kitThree.setItemMeta(itemMeta);
        kitThree.setAmount(1);

        emptySlot = new ItemStack(Material.BLACK_STAINED_GLASS);
        itemMeta = emptySlot.getItemMeta();
        itemMeta.displayName(LD.getLocaleStrings().get("menu.background.name"));
        itemMeta.lore(LD.getLocaleLists().get("menu.background.lore"));
        emptySlot.setItemMeta(itemMeta);
        emptySlot.setAmount(1);
    }

    public static Inventory openVotesInventory(Player player){
        defaultVotes = Bukkit.createInventory(player, 9, Component.text("Дуэль начнётся через 10"));
        defaultVotes.setItem(2, kitOne);
        defaultVotes.setItem(4, kitTwo);
        defaultVotes.setItem(6, kitThree);
        TextUtils.sendWarningMessage(defaultVotes.getItem(0).toString());
        for (int i = 0; i < 9; i++)
            if (defaultVotes.getItem(i) == null)
                defaultVotes.setItem(i, emptySlot);
        return defaultVotes;
    }
}
