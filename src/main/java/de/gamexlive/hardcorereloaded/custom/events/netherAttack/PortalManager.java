package de.gamexlive.hardcorereloaded.custom.events.netherAttack;

import de.gamexlive.hardcorereloaded.sql.SQLGrabber;
import org.bukkit.Location;
import org.bukkit.block.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PortalManager {

    private SQLGrabber grabber;
    private Map<Integer, List<BlockState>> portals;


    public PortalManager(SQLGrabber grabber) {
        this.grabber = grabber;
        portals = grabber.getAllEntriesOfPortals();
    }

    public void addNewPortal(int id, List<BlockState> blocks) {
        portals.put(id, blocks);
    }

    public void removePortal(int id) {
        portals.remove(id);
    }

    public ArrayList<Location> getAllLocations() {
        ArrayList<Location> result = new ArrayList<>();
        portals.forEach((key, value) -> {
           result.add(value.get(0).getLocation());
        });
        return result;
    }


    // Gute Frage wie mache ich das
    public void saveData() {
        Map<Integer, List<BlockState>> allEntries = grabber.getAllEntriesOfPortals();

    }
}
