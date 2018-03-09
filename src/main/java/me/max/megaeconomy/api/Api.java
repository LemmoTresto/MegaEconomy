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

package me.max.megaeconomy.api;

import me.max.megaeconomy.MegaEconomy;
import me.max.megaeconomy.economy.Economy;
import org.bukkit.OfflinePlayer;

import java.math.BigInteger;

public class Api {

    private MegaEconomy megaEconomy;

    public Api(MegaEconomy megaEconomy){
        this.megaEconomy = megaEconomy;
    }

    /**
     *
     * @param p The player you want to get the balance of.
     * @param econ The economy to get the balance from.
     * @return Balance of player in a BigInteger object.
     */
    public BigInteger getBalanceOfPlayer(OfflinePlayer p, Economy econ){
        return megaEconomy.getEconomyManager().getBalanceOfPlayer(p, econ);
    }

    /**
     * Get the Economy object using its name.
     * @param name of the economy.
     * @return Economy object or null if not found.
     */
    public Economy getEconomy(String name){
        return megaEconomy.getEconomyManager().getEconomy(name);
    }
}
