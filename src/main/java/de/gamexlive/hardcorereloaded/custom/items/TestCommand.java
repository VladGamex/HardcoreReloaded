package de.gamexlive.hardcorereloaded.custom.items;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        p.getInventory().addItem(HardcoreReloaded.itemManager.items.get(0));
        return true;
    }
}
