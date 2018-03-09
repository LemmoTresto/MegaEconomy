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

package me.max.megaeconomy.commands;

import me.max.megaeconomy.MegaEconomy;
import me.max.megaeconomy.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.UUID;

public class PayCommand implements CommandExecutor {

    private MegaEconomy megaEconomy;

    public PayCommand(MegaEconomy megaEconomy){
        this.megaEconomy = megaEconomy;

        this.megaEconomy.getCommand("pay").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Console cannot use this command!");
            return true;
        }

        Player p = (Player) sender;

        if (args == null || args.length < 3){
            p.sendMessage(ChatColor.RED + "Invalid usage. /pay <player> <economy> <amount>");
            return true;
        }

        Economy economy = megaEconomy.getEconomyManager().getEconomy(args[1]);
        if (economy == null){
            p.sendMessage(ChatColor.RED + "That economy does not exist.");
            return true;
        }

        if (!p.hasPermission("megaeconomy.pay." + economy.getName())){
            p.sendMessage(ChatColor.RED + "You don't have permission to pay in that economy.");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(args[0]));
        if (target == null){
            p.sendMessage(ChatColor.RED + "This player is unknown.");
            return true;
        }

        BigInteger amount = new BigInteger(args[2]);

        if (target.isOnline()){
            economy.getBalances().put(p.getUniqueId().toString(), economy.getBalances().get(p.getUniqueId().toString()).subtract(amount));
            economy.getBalances().put(target.getUniqueId().toString(), economy.getBalances().get(target.getUniqueId().toString()).add(amount));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', megaEconomy.getConfig().getString("messages.pay.send-money").replace("%receiver%", target.getName())));
            target.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', megaEconomy.getConfig().getString("messages.pay.receive-money").replace("%sender%", p.getName())));
            return true;
        }

        economy.getBalances().put(p.getUniqueId().toString(), economy.getBalances().get(p.getUniqueId().toString()).subtract(amount));
        megaEconomy.getEconomyManager().getDataYml().set(economy.getName() + "." + target.getUniqueId().toString(), new BigInteger(megaEconomy.getEconomyManager().getDataYml().getString(economy.getName() + "." + target.getUniqueId().toString())).add(amount));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', megaEconomy.getConfig().getString("messages.pay.send-money").replace("%receiver%", target.getName())));


        return true;
    }
}
