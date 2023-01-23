package dev.joel.galaxyeconomy.placeholderapi;

import dev.joel.bukkitutil.BukkitUtil;
import dev.joel.galaxyeconomy.GalaxyEconomy;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PAPIHook extends PlaceholderExpansion {

    @Override
    public boolean register() {
        BukkitUtil.send("§5[Economy] §bPlaceholderAPI found, registering placeholders...");
        return super.register();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "galaxyeconomy";
    }

    @Override
    public @NotNull String getAuthor() {
        return "joel";
    }

    @Override
    public @NotNull String getVersion() {
        return "b1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("money_f")) {
            return GalaxyEconomy.getEconomy().formatBalance(player);
        }
        if (params.equalsIgnoreCase("money_n")) {
            return GalaxyEconomy.getEconomy().getBalance(player)+"";
        }
        if (params.equalsIgnoreCase("money")) {
            return GalaxyEconomy.getEconomy().formatBalance(player) + " dinheiros";
        }
        // unknown
        return null;
    }
}
