package dev.joel.galaxyeconomy.api.events;

import dev.joel.galaxyeconomy.GalaxyEconomy;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
@Getter
public class MoneyPreWithdrawEvent extends Event implements Cancellable {

    private final OfflinePlayer player;

    private double balance, amountToWithdraw;

    private boolean cancelled;

    private final static HandlerList handlerList = new HandlerList();

    public MoneyPreWithdrawEvent(final OfflinePlayer player, double balance, double amountToWithdraw, boolean cancelled) {
        this.player = player;
        this.balance = balance;
        this.amountToWithdraw = amountToWithdraw;
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public void setBalance(double balance) {
        GalaxyEconomy.getEconomy().setBalance(player, balance);
        this.balance = balance;
    }

    public void setAmountToWithdraw(double amountToWithdraw) {
        this.amountToWithdraw = amountToWithdraw;
    }

    public boolean isWithdrawingMoreThanBalance() {
        return getExpectedAfterWithdraw() < 0;
    }

    public double getExpectedAfterWithdraw() {
        return balance - amountToWithdraw;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
