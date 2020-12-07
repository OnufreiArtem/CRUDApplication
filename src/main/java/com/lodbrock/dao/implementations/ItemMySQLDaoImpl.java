package com.lodbrock.dao.implementations;

import com.lodbrock.dao.interfaces.IItemDao;
import com.lodbrock.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ItemMySQLDaoImpl implements IItemDao {

    private static ItemMySQLDaoImpl instance;

    private final String host = "localhost";
    private final String port = "3306";
    private final String login = "root";
    private final String password = "password";
    private final String dbName = "gameDB";

    private Connection connection;
    private Statement cursor;


    private ItemMySQLDaoImpl(){

        String uri = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC", host, port, dbName);
        try {
            connection = DriverManager.getConnection(uri, login, password);
            cursor = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public static ItemMySQLDaoImpl getInstance() {
        if(instance == null) instance = new ItemMySQLDaoImpl();
        return instance;
    }

    @Override
    public List<Item> getAll() {
        String query = "SELECT * FROM item;";
        List<Item> allList = new ArrayList<>();
        try {
            ResultSet resultSet = cursor.executeQuery(query);

            while (resultSet.next()){
                Item item = new Item();
                item.setUUId(resultSet.getString("uuid"));
                item.setName(resultSet.getString("name"));
                item.setDescription(resultSet.getString("description"));
                item.setType(resultSet.getString("type"));
                item.setPrice(resultSet.getInt("price"));
                item.setCreated_at(resultSet.getTimestamp("created_at").toLocalDateTime());
                item.setUpdated_at(resultSet.getTimestamp("updated_at").toLocalDateTime());

                allList.add(item);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
        return allList;
    }

    @Override
    public Item getById(String uuid) {
        String query = String.format("SELECT * FROM item WHERE uuid=\"%s\" LIMIT 1;", uuid);
        Item selectedItem = null;
        try {
            ResultSet resultSet = cursor.executeQuery(query);

            while (resultSet.next()){
                selectedItem = new Item();
                selectedItem.setUUId(resultSet.getString("uuid"));
                selectedItem.setName(resultSet.getString("name"));
                selectedItem.setDescription(resultSet.getString("description"));
                selectedItem.setType(resultSet.getString("type"));
                selectedItem.setPrice(resultSet.getInt("price"));
                selectedItem.setCreated_at(resultSet.getTimestamp("created_at").toLocalDateTime());
                selectedItem.setUpdated_at(resultSet.getTimestamp("updated_at").toLocalDateTime());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return selectedItem;
    }

    @Override
    public boolean add(Item item) {
        String query = String.format(
                "INSERT INTO item(uuid, name, description, type, price) " +
                        "VALUES(\"%s\",\"%s\", \"%s\", \"%s\", %d);",
                item.getUUId(),
                item.getName(),
                item.getDescription(),
                item.getType(),
                item.getPrice()
        );

        boolean hasAdded;
        try {
            hasAdded = cursor.executeUpdate(query) != 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return hasAdded;
    }

    @Override
    public boolean update(String uuid, Item item) {
        String query = String.format(
                "UPDATE item SET name=\"%s\", description=\"%s\", type=\"%s\", price=%d, updated_at=NOW() " +
                        "WHERE uuid=\"%s\";",
                item.getName(),
                item.getDescription(),
                item.getType(),
                item.getPrice(),
                uuid
        );

        boolean hasUpdated;
        try {
            hasUpdated = cursor.executeUpdate(query) != 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return hasUpdated;
    }

    @Override
    public boolean delete(String uuid) {
        String query = String.format("DELETE FROM item WHERE uuid=\"%s\";", uuid);
        boolean hasDeleted;
        try {
           hasDeleted = cursor.executeUpdate(query) != 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return hasDeleted;
    }

    public Connection getConnection() {
        return connection;
    }
}
