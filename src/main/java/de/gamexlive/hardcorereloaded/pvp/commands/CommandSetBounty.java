package de.gamexlive.hardcorereloaded.pvp.commands;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandSetBounty implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            Player p = (Player) sender;
            // /setBounty <Target> <amount>
            if (args.length != 2) {
                p.sendMessage("Please use /setBounty <Target> <Amount>");
            } else {
                UUID target = Bukkit.getServer().getPlayer(args[0]).getUniqueId();
                // Check if player has enough coins
                double amount = Double.parseDouble(args[1]);
                if (HardcoreReloaded.cManager.getPlayersCoins(p.getUniqueId()) < amount) {
                    p.sendMessage("You do not have enough coins for this");
                } else {
                    HardcoreReloaded.cManager.changePlayersCoins(p.getUniqueId(), -amount);
                    if(HardcoreReloaded.bounty.contains(target)) {
                        HardcoreReloaded.bounty.changeAmount(target, amount);
                        Bukkit.broadcastMessage("The new bounty of " + Bukkit.getServer().getPlayer(target) +
                                "is now: " + HardcoreReloaded.bounty.getBountyAmount(target));
                    } else {
                        HardcoreReloaded.bounty.addBounty(Bukkit.getServer().getPlayer(args[0]).getUniqueId(), amount);
                        Bukkit.broadcastMessage("There is a new bounty against: " + args[0] + " amount: " + amount);
                    }
                }
            }
            return true;
    }
}
