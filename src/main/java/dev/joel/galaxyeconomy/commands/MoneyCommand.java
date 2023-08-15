package dev.joel.galaxyeconomy.commands;

import dev.joel.galaxyeconomy.GalaxyEconomy;
import dev.joel.galaxyeconomy.config.StupidConfig;
import dev.joel.galaxyeconomy.economy.Eco;
import dev.joel.nu.money.Formatter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoneyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args.length) {
            case 0:
                if (sender instanceof Player) {
                    final Player player = (Player) sender;
                    final double a = GalaxyEconomy.getEconomy().getBalance(player);
                    player.sendMessage(StupidConfig.replaceSimple(player, StupidConfig.balanceSelf.replaceAll("%formatted_money%", Formatter.formatShort(a))));
                }else sender.sendMessage("§cTo view help, fuck you because I didn't do it lol");
                break;
            case 1:
                final OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                if (p.hasPlayedBefore()) {
                    if (!GalaxyEconomy.getEconomy().hasAccount(p)) {
                        sender.sendMessage(StupidConfig.replaceSimple(p, StupidConfig.balanceNoAccount));
                        return false;
                    }
                    final double a = GalaxyEconomy.getEconomy().getBalance(p);
                    sender.sendMessage(StupidConfig.replaceSimple(p, StupidConfig.balanceOthers.replaceAll("%formatted_money%", Formatter.formatShort(a))));
                }else {
                    sender.sendMessage(StupidConfig.replaceSimple(p, StupidConfig.balanceNoPlayed));
                }
                break;
            case 3:
                if (sender.hasPermission("eco.admin")) {
                    switch (args[0].toLowerCase()) {
                        case "set":
                            final OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
                            if (player.hasPlayedBefore() && GalaxyEconomy.getEconomy().hasAccount(player)) {
                                try {
                                    final Eco eco = GalaxyEconomy.getEconomy();
                                    final double a = Double.parseDouble(args[2]);
                                    eco.setBalance(player, a);
                                    sender.sendMessage(StupidConfig.replaceSimple(player, StupidConfig.balanceSet.replaceAll("%formatted_money%", Formatter.formatShort(a))));
                                }catch (NumberFormatException e) {
                                    sender.sendMessage(StupidConfig.replaceSimple(player, StupidConfig.balanceAdminCommandInvalidValue));
                                }
                            }
                            break;
                        case "add":
                            final OfflinePlayer playe = Bukkit.getOfflinePlayer(args[1]);
                            if (playe.hasPlayedBefore() && GalaxyEconomy.getEconomy().hasAccount(playe)) {
                                try {
                                    final Eco eco = GalaxyEconomy.getEconomy();
                                    final double a = Double.parseDouble(args[2]);
                                    eco.depositPlayer(playe, a);
                                    sender.sendMessage(StupidConfig.replaceSimple(playe, StupidConfig.balanceAdd.replaceAll("%formatted_money%", Formatter.formatShort(a))));
                                }catch (NumberFormatException e) {
                                    sender.sendMessage(StupidConfig.replaceSimple(playe, StupidConfig.balanceAdminCommandInvalidValue));
                                }
                            }
                    }
                }
                break;
        }
        return false;
    }
}
