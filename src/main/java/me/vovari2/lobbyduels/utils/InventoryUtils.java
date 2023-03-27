package me.vovari2.lobbyduels.utils;

import me.vovari2.lobbyduels.LDLocale;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
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
        itemMeta.displayName(LDLocale.getLocaleComponent("menu.kit_start_1.name"));
        itemMeta.lore(LDLocale.replacePlaceHoldersList("menu.kit_start_1.lore", "%votes%", "0"));
        kitOne.setItemMeta(itemMeta);
        kitOne.setAmount(1);

        kitTwo = new ItemStack(Material.IRON_HELMET);
        itemMeta = kitTwo.getItemMeta();
        itemMeta.displayName(LDLocale.getLocaleComponent("menu.kit_start_2.name"));
        itemMeta.lore(LDLocale.replacePlaceHoldersList("menu.kit_start_2.lore", "%votes%", "0"));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        kitTwo.setItemMeta(itemMeta);
        kitTwo.setAmount(1);

        kitThree = new ItemStack(Material.NETHERITE_HELMET);
        itemMeta = kitThree.getItemMeta();
        itemMeta.displayName(LDLocale.getLocaleComponent("menu.kit_start_3.name"));
        itemMeta.lore(LDLocale.replacePlaceHoldersList("menu.kit_start_3.lore", "%votes%", "0"));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        kitThree.setItemMeta(itemMeta);
        kitThree.setAmount(1);

        emptySlot = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        itemMeta = emptySlot.getItemMeta();
        itemMeta.displayName(LDLocale.getLocaleComponent("menu.background.name"));
        itemMeta.lore(LDLocale.getLocaleListComponent("menu.background.lore"));
        emptySlot.setItemMeta(itemMeta);
        emptySlot.setAmount(1);
    }

    public static Inventory createVotesInventory(Player player){
        defaultVotes = Bukkit.createInventory(player, 9, LDLocale.getLocaleComponent("menu.name"));
        defaultVotes.setItem(2, kitOne);
        defaultVotes.setItem(4, kitTwo);
        defaultVotes.setItem(6, kitThree);
        for (int i = 0; i < 9; i++)
            if (defaultVotes.getItem(i) == null)
                defaultVotes.setItem(i, emptySlot);
        return defaultVotes;
    }
}
