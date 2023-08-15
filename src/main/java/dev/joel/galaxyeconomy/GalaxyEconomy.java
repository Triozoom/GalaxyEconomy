package dev.joel.galaxyeconomy;

import dev.joel.bukkitutil.BukkitUtil;
import dev.joel.bukkitutil.sql.enums.UpdateType;
import dev.joel.bukkitutil.sql.managing.SQLManager;
import dev.joel.galaxyeconomy.commands.MoneyCommand;
import dev.joel.galaxyeconomy.config.StupidConfig;
import dev.joel.galaxyeconomy.economy.Eco;
import dev.joel.galaxyeconomy.economy.data.Database;
import dev.joel.galaxyeconomy.economy.hook.VaultHook;
import dev.joel.galaxyeconomy.listeners.JAQ;
import dev.joel.galaxyeconomy.placeholderapi.PAPIHook;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;

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

        BukkitUtil.send("§5___________                                         ");
        BukkitUtil.send("§5\\_   _____/ ____  ____   ____   ____   _____ ___.__.");
        BukkitUtil.send("§5 |    __)__/ ___\\/  _ \\ /    \\ /  _ \\ /     <   |  |");
        BukkitUtil.send("§5 |        \\  \\__(  <_> )   |  (  <_> )  Y Y  \\___  |");
        BukkitUtil.send("§5/_______  /\\___  >____/|___|  /\\____/|__|_|  / ____|");
        BukkitUtil.send("§5        \\/     \\/           \\/             \\/\\/     ");
        BukkitUtil.send("§eStarting initialization...");

        economy = vh.hook();

        BukkitUtil.INSTANCE.getSqlManager().registerFile(pluginDatabase = new Database(
                this,
                UpdateType.JOIN_AND_QUIT,
                "atOmDEV.aToMpLuGINsdATAbASE.SQLIte",
                "economy",
                "`uuid` TEXT, `amount` DOUBLE"
        ));

        try {
            BukkitUtil.INSTANCE.getConfigUtil().registerConfigurationSetting(StupidConfig.class);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

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
        }.runTaskTimerAsynchronously(this, 0L, 1200L); // thank god this is async lol


        if (BukkitUtil.INSTANCE.isAPlugin("PlaceholderAPI")) {
            new PAPIHook().register();
        }else {
            BukkitUtil.send("§5[Economy] §cPlaceholderAPI isn't available, skipping...");
        }

        BukkitUtil.send("§eFinished initialization. Enjoy GalaxyEconomy!");
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
