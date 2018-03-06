package me.max.megaeconomy.economy;

import me.max.megaeconomy.MegaEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class EconomyManager {

    private MegaEconomy megaEconomy;
    private List<Economy> economies;
    private YamlConfiguration dataYml;

    public EconomyManager(MegaEconomy megaEconomy){
        this.megaEconomy = megaEconomy;
        economies = new ArrayList<>();

        File dataFolder = new File(megaEconomy.getDataFolder(), "data");
        if (!dataFolder.exists()) dataFolder.mkdir();
        File dataFile = new File(megaEconomy.getDataFolder() + File.separator + "data", "data.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dataYml = YamlConfiguration.loadConfiguration(dataFile);

        ConfigurationSection economySection = megaEconomy.getConfig().getConfigurationSection("economies");
        for (String name : economySection.getKeys(false)){
            ConfigurationSection economy = economySection.getConfigurationSection(name);
            economies.add(new Economy(name, colorise(economy.getString("displayname")), colorise(economy.getString("symbol")), new HashMap<>()));
        }
    }

    public String colorise(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public YamlConfiguration getDataYml() {
        return dataYml;
    }

    public BigInteger getBalanceOfPlayer(Player p, Economy economy){
        if (economy.getBalances().containsKey(p.getUniqueId().toString())) return economy.getBalances().get(p.getUniqueId().toString());
        return (BigInteger) dataYml.get(economy.getName() + "." + p.getUniqueId().toString());
    }

    public List<Economy> getEconomies() {
        return economies;
    }

    public Economy getEconomy(String name){
        for (Economy econ : megaEconomy.getEconomyManager().getEconomies()){
            if (econ.getName().equals(name)) return econ;
        }
        return null;
    }
}
