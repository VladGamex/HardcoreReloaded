package de.gamexlive.hardcorereloaded.custom.events.netherAttack;

import de.gamexlive.hardcorereloaded.sql.SQLGrabber;
import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PortalManager {

    private SQLGrabber grabber;
    private Map<Integer, Location> portals;
    private Map<Integer, BoundingBox> portalsNew;


    public PortalManager(SQLGrabber grabber) {
        this.grabber = grabber;
        portals = grabber.getAllEntriesOfPortals();
    }

    public void addNewPortal(int id, Location loc) {
        portals.put(id, loc);
    }

    public void addNewPortal(int id, BoundingBox box) {
        portalsNew.put(id, box);
    }

    public void removePortal(int id) {
        portals.remove(id);
    }

    public ArrayList<Location> getAllLocations() {
        ArrayList<Location> result = new ArrayList<>();
        portals.forEach((key, value) -> result.add(value));
        return result;
    }


    // Gute Frage wie mache ich das
    public void saveData() {
        HashMap<Integer, Location> allEntries = grabber.getAllEntriesOfPortals();
        for(Map.Entry<Integer, Location> entry : portals.entrySet()) {
            if (allEntries.containsKey(entry.getKey())) {
                allEntries.remove(entry);
                portals.remove(entry);
            }
        }
        allEntries.forEach((key, value) -> grabber.removeDataPortal(key));
        portals.forEach((key, value) -> grabber.addDataPortal(key, value));
    }
}
