package me.vovari2.lobbyduels;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LDInventories {
    public static LDInventories inventories;

    // Предметы меню голосования
    private final ItemStack itemKitOne;
    private final ItemStack itemKitTwo;
    private final ItemStack itemKitThree;

    private final ItemStack itemEmptySlot;

    public LDInventories(){
        itemKitOne = new ItemStack(Material.COD);
        ItemMeta itemMeta = itemKitOne.getItemMeta();
        itemMeta.displayName(LDLocale.getLocaleComponent("menu.kit_start_1.name"));
        itemMeta.lore(LDLocale.replacePlaceHoldersList("menu.kit_start_1.lore", "%votes%", "0"));
        itemKitOne.setItemMeta(itemMeta);
        itemKitOne.setAmount(1);

        itemKitTwo = new ItemStack(Material.IRON_HELMET);
        itemMeta = itemKitTwo.getItemMeta();
        itemMeta.displayName(LDLocale.getLocaleComponent("menu.kit_start_2.name"));
        itemMeta.lore(LDLocale.replacePlaceHoldersList("menu.kit_start_2.lore", "%votes%", "0"));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemKitTwo.setItemMeta(itemMeta);
        itemKitTwo.setAmount(1);

        itemKitThree = new ItemStack(Material.NETHERITE_HELMET);
        itemMeta = itemKitThree.getItemMeta();
        itemMeta.displayName(LDLocale.getLocaleComponent("menu.kit_start_3.name"));
        itemMeta.lore(LDLocale.replacePlaceHoldersList("menu.kit_start_3.lore", "%votes%", "0"));
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemKitThree.setItemMeta(itemMeta);
        itemKitThree.setAmount(1);

        itemEmptySlot = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        itemMeta = itemEmptySlot.getItemMeta();
        itemMeta.displayName(LDLocale.getLocaleComponent("menu.background.name"));
        itemMeta.lore(LDLocale.getLocaleListComponent("menu.background.lore"));
        itemEmptySlot.setItemMeta(itemMeta);
        itemEmptySlot.setAmount(1);
    }

    public static Inventory createVotesInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player, 9, LDLocale.getLocaleComponent("menu.name"));
        inventory.setItem(2, inventories.itemKitOne);
        inventory.setItem(4, inventories.itemKitTwo);
        inventory.setItem(6, inventories.itemKitThree);
        for (int i = 0; i < 9; i++)
            if (inventory.getItem(i) == null)
                inventory.setItem(i, inventories.itemEmptySlot);
        return inventory;
    }
}
