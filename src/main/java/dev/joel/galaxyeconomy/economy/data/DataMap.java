package dev.joel.galaxyeconomy.economy.data;

import lombok.Getter;
import org.bukkit.OfflinePlayer;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class DataMap {

    @Getter
    private final ConcurrentHashMap<UUID, Double> economyMapThingy = new ConcurrentHashMap<>();

    public void add(final OfflinePlayer player, final double baseValue) {
        economyMapThingy.putIfAbsent(player.getUniqueId(), baseValue);
    }
}
