package de.gamexlive.hardcorereloaded.death;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommandGraveyard implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        HardcoreReloaded.graveyard.checkIfRevivable(p.getUniqueId());
        return true;
    }
}
