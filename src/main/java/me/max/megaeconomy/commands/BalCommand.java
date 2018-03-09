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

import java.util.UUID;

public class BalCommand implements CommandExecutor {

    private MegaEconomy megaEconomy;

    public BalCommand(MegaEconomy megaEconomy){
        this.megaEconomy = megaEconomy;

        this.megaEconomy.getCommand("bal").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage("Console cannot use this command!");
            return true;
        }

        Player p = (Player) sender;

        if (args == null || args.length == 0){
            p.sendMessage(ChatColor.RED + "Invalid usage. /bal <economy> <player>");
            return true;
        }

        Economy economy = megaEconomy.getEconomyManager().getEconomy(args[0]);
        if (economy == null){
            p.sendMessage(ChatColor.RED + "That economy does not exist.");
            return true;
        }

        if (args.length == 1){
            String amount = megaEconomy.getEconomyManager().getBalanceOfPlayer(p, economy).toString();
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', megaEconomy.getConfig().getString("messages.balance.self").replace("%formatted_balance%", String.format("%,.2f", amount)).replace("%balance%", amount)));
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(UUID.fromString(args[1]));
        String amount = megaEconomy.getEconomyManager().getBalanceOfPlayer(target, economy).toString();
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', megaEconomy.getConfig().getString("messages.balance.other").replace("%formatted_balance%", String.format("%,.2f", amount)).replace("%balance%", amount)));

        return true;
    }
}
