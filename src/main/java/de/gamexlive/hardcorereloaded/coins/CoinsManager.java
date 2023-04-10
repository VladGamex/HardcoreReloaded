package de.gamexlive.hardcorereloaded.coins;

import de.gamexlive.hardcorereloaded.sql.SQLGrabber;

import java.util.Map;
import java.util.UUID;

public class CoinsManager {

    private Map<UUID, Double> playersCoins;
    private SQLGrabber grabber;

    public CoinsManager(SQLGrabber grabber) {
        this.grabber = grabber;
        playersCoins = this.grabber.getAllEntriesOfCoins();
    }

    public void addNewPlayer(UUID uuid) {
        playersCoins.put(uuid, 0.0);
    }

    public boolean contains(UUID uuid) {
        return playersCoins.containsKey(uuid);
    }

    public double getPlayersCoins(UUID uuid) {
        return playersCoins.get(uuid);
    }

    public void changePlayersCoins(UUID uuid, double amount) {
        playersCoins.put(uuid, playersCoins.get(uuid) + amount);
    }

    public void saveData() {
        //
        Map<UUID, Double> allEntries = grabber.getAllEntriesOfCoins();
        for(Map.Entry<UUID, Double> entry : playersCoins.entrySet()) {
            if (allEntries.containsKey(entry.getKey())) {
                grabber.updateDataCoins(entry.getKey(), entry.getValue());
                playersCoins.remove(entry);
            } else {
                grabber.addDataCoins(entry.getKey(), entry.getValue());
            }
        }

    }
}
