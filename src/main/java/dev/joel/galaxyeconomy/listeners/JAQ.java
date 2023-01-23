package dev.joel.galaxyeconomy.listeners;

import dev.joel.bukkitutil.BukkitUtil;
import dev.joel.galaxyeconomy.GalaxyEconomy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JAQ implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void j(final PlayerJoinEvent e) {
        final boolean a = GalaxyEconomy.INSTANCE.getPluginDatabase().createIfNotExists(e.getPlayer());
        GalaxyEconomy.getEconomy().createPlayerAccount(e.getPlayer());
        if (a) {
            BukkitUtil.send(String.format("§5[Economy] §eCreated and loaded data for player §f%s§e.", e.getPlayer().getName()));
        }else {
            BukkitUtil.send(String.format("§5[Economy] §eLoaded data for player §f%s§e.", e.getPlayer().getName()));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void q(final PlayerQuitEvent e) {
        GalaxyEconomy.INSTANCE.getPluginDatabase().put(e.getPlayer());
        BukkitUtil.send(String.format("§5[Economy] §eSaved data for player §f%s§e.", e.getPlayer().getName()));
    }
}
