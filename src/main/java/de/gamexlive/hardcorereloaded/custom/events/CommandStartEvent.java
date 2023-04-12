package de.gamexlive.hardcorereloaded.custom.events;

import de.gamexlive.hardcorereloaded.custom.events.netherAttack.NetherAttack;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandStartEvent implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch (args[0]) {
            case "blood":
                new BloodMoon();
                break;
            case "nether":
                new NetherAttack();
                break;
        }
        return true;
    }
}
