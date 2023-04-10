package de.gamexlive.hardcorereloaded.pvp.commands;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class CommandGetBounties implements CommandExecutor {
    private Inventory inv;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            p.openInventory(HardcoreReloaded.bounty.getInventory());
            return true;
        }
        return false;
    }
}
