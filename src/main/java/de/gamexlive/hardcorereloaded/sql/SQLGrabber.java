package de.gamexlive.hardcorereloaded.sql;

import de.gamexlive.hardcorereloaded.HardcoreReloaded;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockState;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SQLGrabber {

    private HardcoreReloaded plugin;
    private MySQL sql;

    public SQLGrabber(HardcoreReloaded plugin, MySQL sql) {
        this.plugin = plugin;
        this.sql = sql;
        createTables();
    }

    public void createTables() {
        PreparedStatement statement;
        try{
            //deadPlayers Table
            statement = sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS deadPlayers " +
                    "(UUID VARCHAR(100), TIMEINMILLIS BIGINT)");
            statement.executeUpdate();

            //Coins Table
            statement = sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS coins " +
                    "(UUID VARCHAR(100), COINS DOUBLE)");
            statement.executeUpdate();

            //Bounties Table
            statement = sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS bounties " +
                    "(UUID VARCHAR(100), AMOUNT DOUBLE)");
            statement.executeUpdate();

            //portals Table
            statement = sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS portals " +
                    "(pX DOUBLE, pY DOUBLE, pZ DOUBLE, pID INT)");
            statement.executeUpdate();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    // Bitte funktioniere
    public HashMap<Integer, List<BlockState>> getAllEntriesOfPortals() {
        PreparedStatement statement;
        HashMap<Integer, List<BlockState>> result = new HashMap<>();
        List<BlockState> current =new ArrayList<>();
        ResultSet set;
        try {
            statement = sql.getConnection().prepareStatement("SELECT * FROM portals GROUP BY pID");
            set = statement.executeQuery();
            int currentID = Integer.parseInt(set.getString(1));
            while(set.next()) {
                if(currentID == Integer.parseInt(set.getString(1))) {
                    Location loc = new Location(Bukkit.getWorld("world"),
                            Double.parseDouble(set.getString(2)),
                            Double.parseDouble(set.getString(3)),
                            Double.parseDouble(set.getString(4)));
                   current.add(Bukkit.getWorld("world").getBlockState(loc));
                } else {
                    result.put(currentID, current);
                    current.clear();
                    currentID = Integer.parseInt(set.getString(1));

                    Location loc = new Location(Bukkit.getWorld("world"),
                            Double.parseDouble(set.getString(2)),
                            Double.parseDouble(set.getString(3)),
                            Double.parseDouble(set.getString(4)));
                    current.add(Bukkit.getWorld("world").getBlockState(loc));
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public void addDataPortal(Location loc, int id) {
        PreparedStatement statement;
        try {
            statement = sql.getConnection().prepareStatement("INSERT INTO portals VALUES(?,?,?,?)");
            statement.setDouble(0, loc.getX());
            statement.setDouble(1, loc.getY());
            statement.setDouble(2, loc.getZ());
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeDataPortal(int id) {
        PreparedStatement statement;
        try{
            statement = sql.getConnection().prepareStatement("DELETE FROM portals WHERE pID=?");
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean containsPortal(int id) {
        PreparedStatement statement;
        try{
            statement = sql.getConnection().prepareStatement("SELECT * FROM portals WHERE pID=?");
            statement.setInt(1, id);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public HashMap<UUID, Date> getAllEntriesOfDeadPlayers() {
        PreparedStatement statement;
        HashMap<UUID, Date> result = new HashMap<>();
        try {
            statement = sql.getConnection().prepareStatement("SELECT * FROM deadPlayers");
            ResultSet set = statement.executeQuery();
            while(set.next()) {
                result.put(UUID.fromString(set.getString(1)), new Date(set.getLong(2)));
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public HashMap<UUID, Double> getAllEntriesOfCoins() {
        PreparedStatement statement;
        HashMap<UUID, Double> result = new HashMap<>();
        try {
            statement = sql.getConnection().prepareStatement("SELECT * FROM coins");
            ResultSet set = statement.executeQuery();
            while(set.next()) {
                result.put(UUID.fromString(set.getString(1)), Double.parseDouble(set.getString(2)));
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void addDataCoins(UUID uuid, double amount) {
        PreparedStatement statement;
        try{
            statement = sql.getConnection().prepareStatement("INSERT INTO coins(UUID, COINS) VALUES(?,?)");
            statement.setString(1, uuid.toString());
            statement.setDouble(2, amount);
            statement.executeUpdate();
            statement.close();
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void updateDataCoins(UUID uuid, double amount) {
        PreparedStatement statement;
        try{
            statement = sql.getConnection().prepareStatement("UPDATE coins SET COINS=? WHERE UUID=?");
            statement.setDouble(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
            statement.close();
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void addDataDeadPlayers(UUID uuid, Date date) {
        PreparedStatement statement;
        try{
            statement = sql.getConnection().prepareStatement("INSERT INTO deadPlayers(UUID, TIMEINMILLIS) VALUES(?,?)");
            statement.setString(1, uuid.toString());
            statement.setLong(2, date.getTime());
            statement.executeUpdate();
            statement.close();
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void removeDataDeadPlayers(UUID uuid) {
        PreparedStatement statement;
        try{
            statement = sql.getConnection().prepareStatement("DELETE FROM deadPlayers WHERE UUID=?");
            statement.setString(1, uuid.toString());
            statement.executeUpdate();
            statement.close();
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Map<UUID, Double> getAllEntriesOfBounties() {
        PreparedStatement statement;
        HashMap<UUID, Double> result = new HashMap<>();
        try {
            statement = sql.getConnection().prepareStatement("SELECT * FROM bounties");
            ResultSet set = statement.executeQuery();
            while(set.next()) {
                result.put(UUID.fromString(set.getString(1)), Double.parseDouble(set.getString(2)));
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void updateDataBounties(UUID uuid, double amount) {
        PreparedStatement statement;
        try{
            statement = sql.getConnection().prepareStatement("UPDATE bounties SET AMOUNT=? WHERE UUID=?");
            statement.setDouble(1, amount);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
            statement.close();
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void addDataBounties(UUID uuid, double amount) {
        PreparedStatement statement;
        try{
            statement = sql.getConnection().prepareStatement("INSERT INTO bounties(UUID, AMOUNT) VALUES(?,?)");
            statement.setString(1, uuid.toString());
            statement.setString(2,"" + amount);
            statement.executeUpdate();
            statement.close();
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }
}
