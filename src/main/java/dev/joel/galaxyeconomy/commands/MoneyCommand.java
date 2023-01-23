package dev.joel.galaxyeconomy.commands;

import dev.joel.galaxyeconomy.GalaxyEconomy;
import dev.joel.galaxyeconomy.economy.Eco;
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
                    player.sendMessage(String.format("§5§lGALAXY BANK §8» §eVocê tem R$ %s de dinheiro.", GalaxyEconomy.getEconomy().format(a)));
                }
                break;
            case 1:
                final OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                if (p.hasPlayedBefore()) {
                    if (!GalaxyEconomy.getEconomy().hasAccount(p)) {
                        sender.sendMessage("§5§lGALAXY BANK §8» §eEsse jogador não tem uma conta no banco.");
                        return false;
                    }
                    final double a = GalaxyEconomy.getEconomy().getBalance(p);
                    sender.sendMessage(String.format("§5§lGALAXY BANK §8» §eO jogador %s tem R$ %s de dinheiro.", p.getName(), GalaxyEconomy.getEconomy().format(a)));
                }else {
                    sender.sendMessage("§5§lGALAXY BANK §8» §eEsse jogador nunca jogou no servidor.");
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
                                    sender.sendMessage(String.format("§5§lGALAXY BANK §8» §aO saldo de %s foi definido para %s.", player.getName(), eco.format(a)));
                                }catch (NumberFormatException e) {
                                    sender.sendMessage("§cO valor digitado é inválido.");
                                }
                            }
                            break;
                    }
                }
                break;
        }
        return false;
    }
}
