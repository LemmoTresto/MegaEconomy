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

package me.max.megaeconomy.listeners;

import me.max.megaeconomy.MegaEconomy;
import me.max.megaeconomy.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.math.BigInteger;

public class PlayerJoinListener implements Listener {

    private MegaEconomy megaEconomy;

    public PlayerJoinListener(MegaEconomy megaEconomy){
        this.megaEconomy = megaEconomy;

        this.megaEconomy.getServer().getPluginManager().registerEvents(this, megaEconomy);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event){
        if (!event.getPlayer().hasPlayedBefore()) registerNewPlayerData(event.getPlayer());
        else loadPlayerData(event.getPlayer());
    }

    public void registerNewPlayerData(Player p){
        for (Economy economy : megaEconomy.getEconomyManager().getEconomies()){
            BigInteger startingMoney = new BigInteger(megaEconomy.getConfig().getString("economies." + economy.getName() + ".starting-money"));
            megaEconomy.getEconomyManager().getDataYml().set(economy.getName() + "." + p.getUniqueId().toString(), startingMoney);
            economy.getBalances().put(p.getUniqueId().toString(), startingMoney);
        }
    }

    public void loadPlayerData(Player p){
        megaEconomy.getEconomyManager().getEconomies().forEach(economy -> economy.getBalances().put(p.getUniqueId().toString(), megaEconomy.getEconomyManager().getBalanceOfPlayer(p, economy)));

    }
}
