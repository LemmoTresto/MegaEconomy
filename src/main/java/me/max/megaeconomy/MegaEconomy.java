/*
 *
 *  *
 *  *  *
 *  *  *  * MegaEconomy - The only limit is your imagination
 *  *  *  * Copyright (C) 2018 Max Berkelmans AKA LemmoTresto
 *  *  *  *
 *  *  *  * This program is free software: you can redistribute it and/or modify
 *  *  *  * it under the terms of the GNU General Public License as published by
 *  *  *  * the Free Software Foundation, either version 3 of the License, or
 *  *  *  * (at your option) any later version.
 *  *  *  *
 *  *  *  * This program is distributed in the hope that it will be useful,
 *  *  *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *  *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  *  *  * GNU General Public License for more details.
 *  *  *  *
 *  *  *  * You should have received a copy of the GNU General Public License
 *  *  *  * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *  *  *
 *  *
 *
 */

package me.max.megaeconomy;

import me.max.megaeconomy.api.Api;
import me.max.megaeconomy.commands.BalCommand;
import me.max.megaeconomy.commands.PayCommand;
import me.max.megaeconomy.economy.EconomyManager;
import me.max.megaeconomy.listeners.PlayerJoinListener;
import me.max.megaeconomy.listeners.PlayerLeaveListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MegaEconomy extends JavaPlugin {

    private EconomyManager economyManager;
    private static Api api;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        try {
            getLogger().info("Initialising Economies..");
            economyManager = new EconomyManager(this);
            getLogger().info("Initialised Economies!");
        } catch (Exception e){
            getLogger().severe("Error initialising economies! Shutting down..");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            getLogger().info("Initialising listeners..");
            new PlayerJoinListener(this);
            new PlayerLeaveListener(this);
            getLogger().info("Initialised listeners!");
        } catch (Exception e){
            getLogger().severe("Error initialising listeners! Shutting down..");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            getLogger().info("Initialising commands..");
            new PayCommand(this);
            new BalCommand(this);
            getLogger().info("Initialised commands!");
        } catch (Exception e){
            getLogger().severe("Error initialising commands! Shutting down..");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            getLogger().info("Initialising API..");
            api = new Api(this);
            getLogger().info("Initialised API!");
        } catch (Exception e){
            getLogger().severe("Error initialising API! Shutting down..");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("Saving data..");
        try {
            economyManager.saveData();
            getLogger().info("Saved data!");
        } catch (Exception e) {
            getLogger().severe("Error saving data! Non-saved data will be lost!");
            e.printStackTrace();
        }
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public static Api getApi(){
        return api;
    }
}
