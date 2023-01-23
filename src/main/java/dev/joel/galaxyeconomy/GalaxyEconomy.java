package dev.joel.galaxyeconomy;

import dev.joel.bukkitutil.BukkitUtil;
import dev.joel.bukkitutil.sql.enums.UpdateType;
import dev.joel.bukkitutil.sql.managing.SQLManager;
import dev.joel.galaxyeconomy.commands.MoneyCommand;
import dev.joel.galaxyeconomy.economy.Eco;
import dev.joel.galaxyeconomy.economy.data.Database;
import dev.joel.galaxyeconomy.economy.hook.VaultHook;
import dev.joel.galaxyeconomy.listeners.JAQ;
import dev.joel.galaxyeconomy.placeholderapi.PAPIHook;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class GalaxyEconomy extends JavaPlugin {

    public static GalaxyEconomy INSTANCE;

    @Getter
    private static Eco economy = null;

    public VaultHook vh = new VaultHook();

    @Getter
    private Database pluginDatabase = null;

    @Override
    public void onLoad() {
        if (!BukkitUtil.isCreated()) BukkitUtil.create();
    }

    @Override
    public void onEnable() {
        INSTANCE = this;

        if (!BukkitUtil.isLoaded()) BukkitUtil.INSTANCE.load();

        BukkitUtil.assign(this);

        saveDefaultConfig();

        economy = vh.hook();
        BukkitUtil.INSTANCE.getSqlManager().registerFile(pluginDatabase = new Database(
                this,
                UpdateType.JOIN_AND_QUIT,
                "jOeLpLUgins.JoElpLGUinSdATAbASE.SQLIte",
                "economy",
                "`uuid` TEXT, `amount` DOUBLE"
        ));
        BukkitUtil.INSTANCE.register("money", new MoneyCommand());
        BukkitUtil.INSTANCE.register(new JAQ());

        new BukkitRunnable() {
            @Override
            public void run() {
                int i = 0;
                for (final Player playerIn : getServer().getOnlinePlayers()) {
                    i++;
                    getPluginDatabase().put(playerIn);
                }
                BukkitUtil.send(String.format("§5[Economy] §eAutomatically updated data for %d players.", i));
            }
        }.runTaskTimerAsynchronously(this, 0L, 600L);


        if (BukkitUtil.INSTANCE.isAPlugin("PlaceholderAPI")) {
            new PAPIHook().register();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        int i = 0;
        for (final Player playerIn : getServer().getOnlinePlayers()) {
            i++;
            getPluginDatabase().put(playerIn);
        }
        BukkitUtil.send(String.format("§5[Economy] §ePlugin execution finished; automatically updated data for %d players.", i));
        vh.unhook();
        pluginDatabase.finishConnection();
    }
}
