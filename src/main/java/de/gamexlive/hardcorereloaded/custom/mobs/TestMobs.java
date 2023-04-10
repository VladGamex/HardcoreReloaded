package de.gamexlive.hardcorereloaded.custom.mobs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.entity.Player;

public class TestMobs implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p = (Player) commandSender;
        System.out.println(p.getLocation());
        Goblin g = new Goblin(p.getLocation());
        ((CraftWorld)p.getWorld()).getHandle().addFreshEntity(g);
        return false;
    }
}
