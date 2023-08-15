package dev.joel.galaxyeconomy.config;

import dev.joel.bukkitutil.config.ConfigSetting;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class StupidConfig {


    @ConfigSetting(name = "prefix")
    public static String PREFIX = "&5&lBANK &8»";

    @ConfigSetting(path = "messages.command", name = "self-balance")
    public static String balanceSelf = "%prefix% &eYou have $ %formatted_money% in your balance.";
    @ConfigSetting(path = "messages.command", name = "other-balance")
    public static String balanceOthers = "%prefix% &e%player% has $ %formatted_money% in their balance.";

    @ConfigSetting(path = "messages.command", name = "no-account")
    public static String balanceNoAccount = "%prefix% &c%player% hasn't created a server account yet.";

    @ConfigSetting(path = "messages.command", name = "hasnt-played")
    public static String balanceNoPlayed = "%prefix% &c%player% hasn't played on this server.";

    @ConfigSetting(path = "messages.command", name = "set")
    public static String balanceSet = "%prefix% &a%player_ap% balance has been successfully set to $ %formatted_money%.";
    @ConfigSetting(path = "messages.command", name = "add")
    public static String balanceAdd = "%prefix% &aSuccessfully added $ %formatted_money% to %player_ap% account balance.";

    @ConfigSetting(path = "messages.command", name = "invalid")
    public static String balanceAdminCommandInvalidValue = "%prefix% &cThe inserted value is invalid. Try again.";

    public static String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', PREFIX);
    }

    public static String replaceSimple(final OfflinePlayer player, final String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replaceAll("%prefix%", getPrefix()).replaceAll("%player%", player.getName()).replaceAll("%player_ap%", player.getName() + (player.getName().endsWith("s") ? "'" : "'s")));
    }
}
