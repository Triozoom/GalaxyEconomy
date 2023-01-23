package dev.joel.galaxyeconomy.economy.hook;

import dev.joel.bukkitutil.BukkitUtil;
import dev.joel.galaxyeconomy.GalaxyEconomy;
import dev.joel.galaxyeconomy.economy.Eco;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

public class VaultHook {

    public static VaultHook INSTANCE;

    private final Economy econProvider;

    public VaultHook() {
        INSTANCE=this;
        econProvider = new Eco();
    }

    public Eco hook() {
        Bukkit.getServicesManager().register(Economy.class, this.econProvider, GalaxyEconomy.INSTANCE, ServicePriority.Normal);
        BukkitUtil.send("§aHooked into Vault");
        return (Eco)econProvider;
    }

    public void unhook() {
        Bukkit.getServicesManager().unregister(Economy.class, this.econProvider);
    }

}
