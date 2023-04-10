package de.gamexlive.hardcorereloaded.pvp;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import de.gamexlive.hardcorereloaded.sql.SQLGrabber;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.permissions.PermissionAttachment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Bounty {

    private Map<UUID, Double> bounties;
    private Inventory allBounties;
    private SQLGrabber grabber;
    public Bounty(SQLGrabber grabber) {
        this.grabber = grabber;
        init();
    }

    public void init() {
        bounties = this.grabber.getAllEntriesOfBounties();
        allBounties = Bukkit.createInventory(null, 9*5, "Bounties");
        bounties.forEach((key, value) -> {
            addPlayerToInventory(key, value);
        });
    }

    public void addBounty(UUID uuid, double amount) {
        bounties.put(uuid, amount);
        addPlayerToInventory(uuid, amount);
        PermissionAttachment pA = Bukkit.getServer().getPlayer(uuid).addAttachment(HardcoreReloaded.instance);
        pA.setPermission("hardcorereloaded.RadiusChecker", true);
    }

    public void removeBounty(UUID uuid) {
        bounties.remove(uuid);
        removePlayerFromInventory(uuid);
        PermissionAttachment pA = Bukkit.getServer().getPlayer(uuid).addAttachment(HardcoreReloaded.instance);
        pA.unsetPermission("hardcorereloaded.RadiusChecker");
    }

    public double getBountyAmount(UUID uuid) {
        return bounties.get(uuid);
    }

    public boolean contains(UUID uuid) {
        return bounties.containsKey(uuid);
    }

    public Map<UUID,Double> getBounties() {
        return bounties;
    }

    public void saveData() {
        Map<UUID, Double> allEntries = grabber.getAllEntriesOfBounties();
        for(Map.Entry<UUID, Double> entry : bounties.entrySet()) {
            if (allEntries.containsKey(entry.getKey())) {
                grabber.updateDataBounties(entry.getKey(), entry.getValue());
                bounties.remove(entry);
            } else {
                grabber.addDataBounties(entry.getKey(), entry.getValue());
            }
        }
    }

    public void addPlayerToInventory(UUID uuid, double amount) {
            ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
            SkullMeta skull = (SkullMeta) head.getItemMeta();
            skull.setDisplayName(Bukkit.getServer().getPlayer(uuid).getDisplayName());
            ArrayList<String> lore = new ArrayList<>();
            lore.add("" + amount);
            skull.setLore(lore);
            skull.setOwner(Bukkit.getServer().getPlayer(uuid).getDisplayName());
            head.setItemMeta(skull);
            allBounties.addItem(head);
    }

    public void removePlayerFromInventory(UUID uuid) {
        allBounties.forEach(key -> {
            if(key.getItemMeta().getDisplayName().equalsIgnoreCase(
                    Bukkit.getServer().getPlayer(uuid).getDisplayName())) {
                allBounties.remove(key);
            }
        });
    }
    public Inventory getInventory() { return allBounties;}
}
