package de.gamexlive.hardcorereloaded.custom.items;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    public ArrayList<ItemStack> items;

    public ItemManager() {
        init();
    }

    private void init() {
        items = new ArrayList<>();
        HardcoreReloaded.instance.getConfig().getConfigurationSection("items").getKeys(false).forEach(key ->{
         ItemStack currentItem;
         List<String> allItems = HardcoreReloaded.instance.getConfig().getStringList("items."+ key);

            String name = allItems.get(1);
            currentItem = new ItemStack(Material.getMaterial(allItems.get(2)));
            ItemMeta meta = currentItem.getItemMeta();
            switch (allItems.get(0)) {
                case "LEGENDARY":
                    meta.setDisplayName(ChatColor.GOLD + name);
                    break;
                case "EPIC":
                    meta.setDisplayName(ChatColor.LIGHT_PURPLE + name);
                    break;
                case "RARE":
                    meta.setDisplayName(ChatColor.AQUA + name);
                    break;
                case "UNCOMMON":
                    meta.setDisplayName(ChatColor.GREEN + name);
                    break;
                case "COMMON":
                    meta.setDisplayName(ChatColor.GRAY + name);
                    break;
                case "BROKEN":
                    meta.setDisplayName(ChatColor.DARK_GRAY + name);
                    break;
            }
            currentItem.setItemMeta(meta);
            items.add(currentItem);
        });
    }
}