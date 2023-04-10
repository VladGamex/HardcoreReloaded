package de.gamexlive.hardcorereloaded.listeners;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
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
    public void onPlayerDamage(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player p = ((Player) event.getEntity()).getPlayer();

        bloodMoonChecker(event);


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
    }

    private void bloodMoonChecker(EntityDamageEvent event) {
        if (HardcoreReloaded.eventManager.ID != 0) {
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                EntityDamageByEntityEvent event1 = (EntityDamageByEntityEvent) event;
                if (event1.getDamager() instanceof Zombie) {
                    Zombie z = (Zombie) event1.getDamager();
                    if (z.getHealth() < z.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
                        z.setHealth(z.getHealth() + event.getDamage());
                    }
                }
            }
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