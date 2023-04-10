package de.gamexlive.hardcorereloaded.death;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import de.gamexlive.hardcorereloaded.sql.SQLGrabber;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Graveyard {

    private Map<UUID, Date> deadPlayers;
    private SQLGrabber grabber;
    public static int ID;


    public Graveyard(SQLGrabber grabber) {
        this.grabber = grabber;
        deadPlayers = grabber.getAllEntriesOfDeadPlayers();
    }

    public void addPlayer(UUID uuid, Date date) {
        Player p = Bukkit.getPlayer(uuid);
        p.setHealth(20);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 1);
        deadPlayers.put(uuid, calendar.getTime());

        long diff = calendar.getTimeInMillis() - System.currentTimeMillis();
        p.sendMessage("Welcome to the graveyard");
        p.sendMessage("Your remaining time is: " + formatDuration(diff));
        p.teleport(new Location(
                p.getWorld(),
                HardcoreReloaded.instance.getConfig().getInt("graveyard.X"),
                HardcoreReloaded.instance.getConfig().getInt("graveyard.Y"),
                HardcoreReloaded.instance.getConfig().getInt("graveyard.Z")
        ));
    }
    public void checkIfRevivable(UUID uuid) {
        long diff = deadPlayers.get(uuid).getTime() - System.currentTimeMillis();
        long hours = TimeUnit.MILLISECONDS.toHours(diff);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        Player p = Bukkit.getPlayer(uuid);
        System.out.println(hours + ":" + minutes + ":" + seconds);
        if(hours <= 0 && minutes <= 0 && seconds <= 0) {
            deadPlayers.remove(uuid);
            p.sendMessage("You are now free to go. See you soon");
            p.teleport(new Location(
                    p.getWorld(),
                    HardcoreReloaded.instance.getConfig().getInt("spawn.X"),
                    HardcoreReloaded.instance.getConfig().getInt("spawn.Y"),
                    HardcoreReloaded.instance.getConfig().getInt("spawn.Z")
            ));
        } else {
            p.sendMessage("Your remaining time is: " + formatDuration(diff));
        }
    }
    public boolean contains(UUID uuid) {
        return deadPlayers.containsKey(uuid);
    }
    private static String formatDuration(long duration) {
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public Map<UUID, Date> getDeadPlayers() { return deadPlayers; }

    public void saveData() {
        HashMap<UUID, Date> allEntries = grabber.getAllEntriesOfDeadPlayers();
        for(UUID uuid : deadPlayers.keySet()) {
            if(allEntries.containsKey(uuid)) {
                allEntries.remove(uuid);
                deadPlayers.remove(uuid);
            }
        }
        for (Map.Entry<UUID, Date> entry : deadPlayers.entrySet()) {
            UUID uuid = entry.getKey();
            Date value = entry.getValue();
            grabber.addDataDeadPlayers(uuid, value);
        }
        for(UUID uuid : allEntries.keySet()) {
            grabber.removeDataDeadPlayers(uuid);
        }
    }

    public void activateTask() {
        ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(HardcoreReloaded.instance, new Runnable() {
            @Override
            public void run() {
                if (HardcoreReloaded.instance.getServer().getOnlinePlayers().size() > 0) {
                    deadPlayers.forEach((key, value) -> {
                        if (HardcoreReloaded.instance.getServer().getPlayer(key) != null) {
                            checkIfRevivable(key);
                        }
                    });
                }
            }
        }, 0, 20L * 60);
    }
}