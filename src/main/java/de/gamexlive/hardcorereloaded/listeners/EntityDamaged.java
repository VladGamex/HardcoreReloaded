package de.gamexlive.hardcorereloaded.listeners;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import de.gamexlive.hardcorereloaded.Utils.UtilsInventory;
import de.gamexlive.hardcorereloaded.custom.mobs.Goblin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Date;

public class EntityDamaged implements Listener {
    @EventHandler
    public void onEntityDamage(final EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = ((Player) event.getEntity()).getPlayer();
            bloodMoonChecker(event);
            goblinAttackChecker(event);

            if (p.getHealth() - event.getDamage() <= 0.0) {
                event.setCancelled(true);
                // Drop players items
                for (ItemStack content : p.getInventory().getContents()) {
                    if (content == null) continue;
                    p.getWorld().dropItemNaturally(p.getLocation(), content);
                    p.getInventory().remove(content);
                }
                //Add player to Graveyard
                Date date = new Date(System.currentTimeMillis());
                HardcoreReloaded.graveyard.addPlayer(p.getUniqueId(), date);

                //Check if player had a Bounty
                bountyChecker(event, p);
            }
        } else if (((CraftZombie)event.getEntity()).getHandle() instanceof Goblin) {
            goblinAttackedChecker(event);
        }
    }

    private void goblinAttackedChecker(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) return;
        Goblin g = (Goblin)((CraftZombie)event.getEntity()).getHandle();
        Player p = (Player) event.getDamager();
        if(g.getInventory().isEmpty()) return;

        p.sendMessage("Goblin: How dare you take on of MY items");

        ItemStack item = UtilsInventory.getRandomItem(g.getInventory());
        g.getInventory().remove(item);
        Location loc = new Location(p.getWorld(), g.getX(), g.getY(), g.getZ());
        p.getWorld().dropItemNaturally(loc, item);
    }

    private void goblinAttackChecker(EntityDamageByEntityEvent event) {
        if(!(((CraftZombie)event.getDamager()).getHandle() instanceof Goblin)) return;
        Goblin g = (Goblin) ((CraftZombie)event.getDamager()).getHandle();
        Player p = (Player) event.getEntity();

        if(p.getInventory().isEmpty()) {
            p.sendMessage("Goblin: Damn you got no items on you? Can you get some?");
        } else {
            ItemStack randomItem = UtilsInventory.getRandomItem(p.getInventory());

             p.getInventory().remove(randomItem);
             g.getInventory().addItem(randomItem);
        }
    }

    private void bloodMoonChecker(EntityDamageByEntityEvent event) {
        if(HardcoreReloaded.eventManager.ID == 0) return;
        if(event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) return;
        if(!(event.getDamager() instanceof Zombie)) return;

        Zombie z = (Zombie) event.getDamager();
        if (z.getHealth() < z.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
            z.setHealth(z.getHealth() + event.getDamage());
        }

     }

    private void bountyChecker(EntityDamageEvent event, Player p) {
        if (HardcoreReloaded.bounty.contains(p.getUniqueId())) {

            if (!(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)) return;
            EntityDamageByEntityEvent event1 = (EntityDamageByEntityEvent) event;
            if (!(event1 instanceof Player)) return;
            Player reward = (Player) event1;
            double amount = HardcoreReloaded.bounty.getBountyAmount(p.getUniqueId());
            HardcoreReloaded.cManager.changePlayersCoins(reward.getUniqueId(), amount);
            Bukkit.getServer().broadcastMessage("The bounty of: " + p.getDisplayName() + " has been secured by " + reward.getDisplayName());
            HardcoreReloaded.bounty.removeBounty(p.getUniqueId());
        }
    }
}