package me.max.megaeconomy;

import me.max.megaeconomy.api.Api;
import me.max.megaeconomy.economy.EconomyManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MegaEconomy extends JavaPlugin {

    private EconomyManager economyManager;
    private static Api api;

    @Override
    public void onEnable() {
        // Plugin startup logic

        this.economyManager = new EconomyManager(this);
        this.api = new Api(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public static Api getApi(){
        return api;
    }
}
