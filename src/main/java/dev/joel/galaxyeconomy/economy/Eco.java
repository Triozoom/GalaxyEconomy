package dev.joel.galaxyeconomy.economy;

import dev.joel.galaxyeconomy.GalaxyEconomy;
import dev.joel.galaxyeconomy.api.events.MoneyPreWithdrawEvent;
import dev.joel.galaxyeconomy.economy.data.DataMap;
import dev.joel.nu.money.Formatter;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Eco implements net.milkbowl.vault.economy.Economy {

    public final DataMap dataMap = new DataMap();

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return "GalaxyEconomy";
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return Formatter.formatShort(amount);
    }

    public String formatBalance(final OfflinePlayer op) {
        return format(getBalance(op));
    }

    @Override
    public String currencyNamePlural() {
        return "dinheiros";
    }

    @Override
    public String currencyNameSingular() {
        return "dinheiro";
    }

    @Override
    public boolean hasAccount(String playerName) {
        final OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        if (!player.hasPlayedBefore()) return false;

        return dataMap.getEconomyMapThingy().containsKey(player.getUniqueId());
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        if (!player.hasPlayedBefore()) return false;

        return dataMap.getEconomyMapThingy().containsKey(player.getUniqueId());
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return hasAccount(playerName);
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return hasAccount(player);
    }

    @Override
    public double getBalance(String playerName) {
        return getBalance(Bukkit.getOfflinePlayer(playerName));
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        return dataMap.getEconomyMapThingy().containsKey(player.getUniqueId()) ? dataMap.getEconomyMapThingy().get(player.getUniqueId()) : 0;
    }

    @Override
    public double getBalance(String playerName, String world) {
        return getBalance(playerName);
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return getBalance(player);
    }

    @Override
    public boolean has(String playerName, double amount) {
        return getBalance(playerName) >= amount;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return getBalance(player) >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return has(playerName, amount);
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return has(player, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        return withdrawPlayer(Bukkit.getOfflinePlayer(playerName), amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        final MoneyPreWithdrawEvent event = new MoneyPreWithdrawEvent(player, getBalance(player), amount, false);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) return new EconomyResponse(event.getAmountToWithdraw(), event.getBalance(), EconomyResponse.ResponseType.FAILURE, "Cancelled by event cancel.");
        double expected = event.getExpectedAfterWithdraw();
        if (event.isWithdrawingMoreThanBalance()) expected = 0;
        dataMap.getEconomyMapThingy().put(player.getUniqueId(), expected);
        return new EconomyResponse(amount, expected, EconomyResponse.ResponseType.SUCCESS, null);
    }

    public EconomyResponse withdrawNoEvent(OfflinePlayer player, double amount) {
        double expected = getBalance(player) - amount;
        if (expected < 0) expected = 0;
        dataMap.getEconomyMapThingy().put(player.getUniqueId(), expected);
        return new EconomyResponse(amount, expected, EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return withdrawPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return withdrawPlayer(player, amount);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return depositPlayer(Bukkit.getOfflinePlayer(playerName), amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        final double expected = dataMap.getEconomyMapThingy().get(player.getUniqueId()) + amount;
        if (expected > Double.MAX_VALUE) throw new IllegalArgumentException();
        dataMap.getEconomyMapThingy().put(player.getUniqueId(), expected);
        return new EconomyResponse(amount, expected, EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return depositPlayer(playerName, amount);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return depositPlayer(player, amount);
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "Not implemented.");
    }

    @Override
    public List<String> getBanks() {
        return Collections.emptyList();
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
       return createPlayerAccount(Bukkit.getOfflinePlayer(playerName));
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        dataMap.add(player, GalaxyEconomy.INSTANCE.getPluginDatabase().get(player));
        return dataMap.getEconomyMapThingy().containsKey(player.getUniqueId());
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return createPlayerAccount(playerName);
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return createPlayerAccount(player);
    }

    public EconomyResponse setBalance(final OfflinePlayer player, final double balance) {
        dataMap.getEconomyMapThingy().put(player.getUniqueId(), balance);
        return new EconomyResponse(balance, balance, EconomyResponse.ResponseType.SUCCESS, null);
    }
}
