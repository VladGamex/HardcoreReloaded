package de.gamexlive.hardcorereloaded;

import de.gamexlive.hardcorereloaded.coins.CoinsManager;
import de.gamexlive.hardcorereloaded.coins.commands.CommandGetCoins;
import de.gamexlive.hardcorereloaded.custom.events.EventManager;
import de.gamexlive.hardcorereloaded.custom.items.ItemManager;
import de.gamexlive.hardcorereloaded.custom.items.TestCommand;
import de.gamexlive.hardcorereloaded.custom.mobs.TestMobs;
import de.gamexlive.hardcorereloaded.death.Graveyard;
import de.gamexlive.hardcorereloaded.death.TestCommandGraveyard;
import de.gamexlive.hardcorereloaded.listeners.EntityDamaged;
import de.gamexlive.hardcorereloaded.listeners.InventoryClick;
import de.gamexlive.hardcorereloaded.listeners.PlayerDisconnect;
import de.gamexlive.hardcorereloaded.listeners.PlayerJoin;
import de.gamexlive.hardcorereloaded.pvp.Bounty;
import de.gamexlive.hardcorereloaded.pvp.commands.CommandCheckIfPlayerInRadius;
import de.gamexlive.hardcorereloaded.pvp.commands.CommandGetBounties;
import de.gamexlive.hardcorereloaded.pvp.commands.CommandSetBounty;
import de.gamexlive.hardcorereloaded.sql.MySQL;
import de.gamexlive.hardcorereloaded.sql.SQLGrabber;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class HardcoreReloaded extends JavaPlugin {
    public static HardcoreReloaded instance;
    private MySQL sql;
    private SQLGrabber grabber;
    public static Graveyard graveyard;
    public static ItemManager itemManager;
    public static EventManager eventManager;
    public static Bounty bounty;
    public static CoinsManager cManager;

    public void onEnable() {
        this.saveDefaultConfig();
        instance = this;
        sql = new MySQL();
        try {
            sql.connect();
        } catch (SQLException | ClassNotFoundException e) {
            Bukkit.getLogger().info("Database is not connected");
        }
        if (sql.isConnected()) {
            grabber = new SQLGrabber(this, sql);
            graveyard = new Graveyard(grabber);
            Bukkit.getLogger().info("Database is connected");
        }

        itemManager = new ItemManager();
        bounty = new Bounty(grabber);
        eventManager = new EventManager();
        cManager = new CoinsManager(grabber);
        //Commands
        this.getCommand("items").setExecutor(new TestCommand());
        this.getCommand("grave").setExecutor(new TestCommandGraveyard());
        this.getCommand("radiuschecker").setExecutor(new CommandCheckIfPlayerInRadius());
        this.getCommand("setBounty").setExecutor(new CommandSetBounty());
        this.getCommand("coins").setExecutor(new CommandGetCoins());
        this.getCommand("bounties").setExecutor(new CommandGetBounties());
        this.getCommand("kleinerHuso").setExecutor(new TestMobs());

        //Listeners
        Bukkit.getPluginManager().registerEvents(new EntityDamaged(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDisconnect(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
    }

    public void onDisable() {
        Bukkit.getLogger().info("Saving Graveyard");
        graveyard.saveData();
        Bukkit.getLogger().info("Saving coins");
        cManager.saveData();
        Bukkit.getLogger().info("Saving bounties");
        bounty.saveData();

        sql.disconnect();
    }
}