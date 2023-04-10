package de.gamexlive.hardcorereloaded.custom.events;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.Bukkit;

import java.util.Random;

public class EventManager {

    public int ID;

    public EventManager() {
        ID = 0;
    }
    public void activateTask() {
          ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(HardcoreReloaded.instance, new Runnable() {
            @Override
            public void run() {
                    Random random = new Random();
                    int randomNumber = random.nextInt(10 - 1 + 1) + 1;
                    System.out.println(randomNumber);
                    if (randomNumber == 2) {
                        new BloodMoon();
                    }
            }
        }, 0L, 20L * 60L * 5);
    }
}