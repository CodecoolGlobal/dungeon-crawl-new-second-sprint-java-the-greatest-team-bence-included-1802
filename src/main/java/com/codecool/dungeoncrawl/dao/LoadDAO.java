package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.data.actors.*;
import com.codecool.dungeoncrawl.service.LoadService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadDAO {
    private final DataSource dataSource;

    public LoadDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void loadActors(LoadService loadService) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM actors";

            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                loadService.loadActor(rs.getString("name"), rs.getInt("x"), rs.getInt("y"), rs.getInt("health"), rs.getInt("attackPower"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadItems(LoadService loadService) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM items";

            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                loadService.loadItem(rs.getString("name"), rs.getInt("x"), rs.getInt("y"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadInventoryItems(LoadService loadService) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT * FROM inventory_items";

            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                loadService.loadInventoryItem(rs.getString("name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
