package de.gamexlive.hardcorereloaded.coins.commands;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGetCoins implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player p = (Player) sender;
        p.sendMessage("" + HardcoreReloaded.cManager.getPlayersCoins(p.getUniqueId()));
        return true;
    }
}
