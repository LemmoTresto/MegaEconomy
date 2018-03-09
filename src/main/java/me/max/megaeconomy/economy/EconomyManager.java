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

package me.max.megaeconomy.economy;

import me.max.megaeconomy.MegaEconomy;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EconomyManager {

    private MegaEconomy megaEconomy;
    private List<Economy> economies;
    private YamlConfiguration dataYml;
    private File dataFile;

    public EconomyManager(MegaEconomy megaEconomy){
        this.megaEconomy = megaEconomy;
        economies = new ArrayList<>();

        File dataFolder = new File(megaEconomy.getDataFolder(), "data");
        if (!dataFolder.exists()) dataFolder.mkdir();
        dataFile = new File(megaEconomy.getDataFolder() + File.separator + "data", "data.yml");
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

    public void saveData() throws IOException {
        for (Economy economy : economies){
            economy.getBalances().forEach((s, bigInteger) -> dataYml.set(economy.getName() + "." + s, bigInteger.toString()));
        }
        dataYml.save(dataFile);
    }

    public String colorise(String text){
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public YamlConfiguration getDataYml() {
        return dataYml;
    }

    public BigInteger getBalanceOfPlayer(OfflinePlayer p, Economy economy){
        if (economy.getBalances().containsKey(p.getUniqueId().toString())) return economy.getBalances().get(p.getUniqueId().toString());
        return new BigInteger(dataYml.getString(economy.getName() + "." + p.getUniqueId().toString()));
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
