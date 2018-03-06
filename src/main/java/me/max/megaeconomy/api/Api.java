package me.max.megaeconomy.api;

import me.max.megaeconomy.MegaEconomy;
import me.max.megaeconomy.economy.Economy;
import org.bukkit.entity.Player;

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
    public BigInteger getBalanceOfPlayer(Player p, Economy econ){
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
