package dev.joel.galaxyeconomy.economy.data;

import dev.joel.bukkitutil.sql.SQLFile;
import dev.joel.bukkitutil.sql.enums.UpdateType;
import dev.joel.galaxyeconomy.GalaxyEconomy;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Database extends SQLFile {

    public Database(JavaPlugin src, UpdateType type, String filename, String tableName, String tableItems) {
        super(src, type, filename, tableName, tableItems);
    }

    public void put(final Player player) {
        try {
            final PreparedStatement prp = con.prepareStatement("SELECT * from `economy` WHERE uuid=?");
            prp.setString(1, player.getUniqueId().toString());
            final ResultSet rs = prp.executeQuery();
            if (rs.next()) {
                final PreparedStatement pst = con.prepareStatement("UPDATE `economy` SET amount=? WHERE uuid=?");
                pst.setDouble(1, GalaxyEconomy.getEconomy().getBalance(player));
                pst.setString(2, player.getUniqueId().toString());
                pst.executeUpdate();
            }else {
                final PreparedStatement pst = con.prepareStatement("INSERT INTO `economy` VALUES (?,?)");
                pst.setString(1, player.getUniqueId().toString());
                pst.setDouble(2, GalaxyEconomy.getEconomy().getBalance(player));
                pst.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public double get(final OfflinePlayer player) {
        try {
            final PreparedStatement prp = con.prepareStatement("SELECT * from `economy` WHERE uuid=?");
            prp.setString(1, player.getUniqueId().toString());
            final ResultSet rs = prp.executeQuery();
            if (rs.next()) {
                return rs.getDouble("amount");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean createIfNotExists(final OfflinePlayer player) {
        try {
            final PreparedStatement prp = con.prepareStatement("SELECT * from `economy` WHERE uuid=?");
            prp.setString(1, player.getUniqueId().toString());
            final ResultSet rs = prp.executeQuery();
            if (!rs.next()) {
                final PreparedStatement pst = con.prepareStatement("INSERT INTO `economy` VALUES (?,?)");
                pst.setString(1, player.getUniqueId().toString());
                pst.setDouble(2, 0.D);
                pst.executeUpdate();
                return true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
