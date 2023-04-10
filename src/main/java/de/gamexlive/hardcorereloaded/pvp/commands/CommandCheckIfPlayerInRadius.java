package de.gamexlive.hardcorereloaded.pvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCheckIfPlayerInRadius implements CommandExecutor {

    private String PERMISSION_FOR_CHECKING = "hardcorereloaded.RadiusChecker";
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;

        Boolean found = false;
        if(!p.hasPermission(PERMISSION_FOR_CHECKING)) {
            p.sendMessage("You do not have the necessary permission");
            return false;
        }
        for (Player key : Bukkit.getServer().getOnlinePlayers()) {
            if (key.getUniqueId().equals(p.getUniqueId())) continue;
            if (p.getLocation().distanceSquared(key.getLocation()) <= 30) {
                p.sendMessage("Player(s) found");
                found = true;
            }
        }
        if(!found) {
            p.sendMessage("No player(s) found");
        }
        return true;
    }
}
