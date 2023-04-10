package de.gamexlive.hardcorereloaded.Utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class UtilsInventory {

    public static ItemStack getRandomItem(Inventory inv) {
        ItemStack[] nonNull = nonNullItemContent(inv.getContents());
        int amountOfItems = nonNull.length;
        int randomItem = new Random().nextInt(amountOfItems);
        return nonNull[randomItem];
    }

    private static ItemStack[] nonNullItemContent(ItemStack[] arr) {
        ArrayList<ItemStack> test = new ArrayList<>();
        for(ItemStack stack : arr) {
            if(stack != null) {
                test.add(stack);
            }
        }
        ItemStack[] result = new ItemStack[test.size()];
        return test.toArray(result);
    }

}
