package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.dao.sqlData.ActorData;
import com.codecool.dungeoncrawl.dao.sqlData.InventoryData;
import com.codecool.dungeoncrawl.dao.sqlData.ItemData;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLDao {
    private final DataSource dataSource;

    public SQLDao() {
        try {
            dataSource = connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //SAVE

    public void saveActor(String name, int x, int y, int health, int attackPower) {

        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO actors (name, x, y, health, attackpower) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.setInt(2, x);
            st.setInt(3, y);
            st.setInt(4, health);
            st.setInt(5, attackPower);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Actor cannot be added to database.", e);
        }
    }

    public void saveItem(String name, int x, int y) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO items (name, x, y) VALUES (?, ?, ?)";

            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.setInt(2, x);
            st.setInt(3, y);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Author cannot be added to database.", e);
        }
    }

    public void saveInventoryItem(String name) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO inventory_items (name) VALUES (?)";

            PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Author cannot be added to database.", e);
        }
    }

    //LOAD

    public List<ActorData> loadActors() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM actors";

            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            List<ActorData> actors = new ArrayList<>();
            while (rs.next()) {
                actors.add(new ActorData(rs.getString("name"),
                        rs.getInt("x"),
                        rs.getInt("y"),
                        rs.getInt("health"),
                        rs.getInt("attackPower")));
            }
            return actors;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ItemData> loadItems() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM items";

            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            List<ItemData> items = new ArrayList<>();

            while (rs.next()) {
                items.add(new ItemData(rs.getString("name"),
                        rs.getInt("x"),
                        rs.getInt("y")));
            }

            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<InventoryData> loadInventoryItems() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM inventory_items";

            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            List<InventoryData> inventoryItems = new ArrayList<>();

            while (rs.next()) {
                inventoryItems.add(new InventoryData( rs.getString("name")));
            }

            return inventoryItems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();

        dataSource.setDatabaseName("dungeon_crawl");
        dataSource.setUser("postgres");
        dataSource.setPassword("codecool2026");

        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connection OK");

        return dataSource;
    }

    public void resetTables() {
        try (Connection conn = dataSource.getConnection()) {
            String[] sqls = {
                    "DELETE FROM actors",
                    "DELETE FROM items",
                    "DELETE FROM inventory_items"
            };

            for (String sql : sqls) {
                try (PreparedStatement st = conn.prepareStatement(sql)) {
                    st.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Author cannot be added to database.", e);
        }
    }
}
