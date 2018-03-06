package me.max.megaeconomy.listeners;

import me.max.megaeconomy.MegaEconomy;
import me.max.megaeconomy.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.math.BigInteger;

public class PlayerJoinListener implements Listener {

    private MegaEconomy megaEconomy;

    public PlayerJoinListener(MegaEconomy megaEconomy){
        this.megaEconomy = megaEconomy;

        this.megaEconomy.getServer().getPluginManager().registerEvents(this, megaEconomy);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if (!event.getPlayer().hasPlayedBefore()) registerNewPlayerData(event.getPlayer());
        else loadPlayerData(event.getPlayer());
    }

    public void registerNewPlayerData(Player p){
        for (Economy economy : megaEconomy.getEconomyManager().getEconomies()){
            megaEconomy.getEconomyManager().getDataYml().set(economy.getName() + "." + p.getUniqueId().toString(), new BigInteger(megaEconomy.getConfig().getString("economies." + economy.getName() + ".starting-money")));
        }
    }

    public void loadPlayerData(Player p){

    }
}
