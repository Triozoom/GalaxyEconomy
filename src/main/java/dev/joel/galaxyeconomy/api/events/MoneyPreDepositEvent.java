package dev.joel.galaxyeconomy.api.events;

import dev.joel.galaxyeconomy.GalaxyEconomy;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class MoneyPreDepositEvent extends Event implements Cancellable {

    private final OfflinePlayer player;

    private double balance, amountToDeposit;

    private boolean cancelled;

    private final static HandlerList handlerList = new HandlerList();

    public MoneyPreDepositEvent(final OfflinePlayer player, double balance, double amountToDeposit, boolean cancelled) {
        this.player = player;
        this.balance = balance;
        this.amountToDeposit = amountToDeposit;
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

    public void setAmountToDeposit(double amountToDeposit) {
        this.amountToDeposit = amountToDeposit;
    }

    public double getExpectedAfterWithdraw() {
        return balance + amountToDeposit;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
