package com.engeto.projektAPI;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    Connection connection;

    public ProductService() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/product",
                "product_crud",
                "ASdf1234"
        );



    }



    public List<Product> getAllItems() throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM product");

        List<Product> resultList = new ArrayList<>();

        while (resultSet.next()) {
            Product product = extractItemData(resultSet);

            resultList.add(product);
        }

        return resultList;
    }

    public List<Product> getAllAvailableItems() throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM product WHERE isForSale = true");

        List<Product> actualProductList = new ArrayList<>();

        while (resultSet.next()) {
            Product product = extractItemData(resultSet);

            actualProductList.add(product);
        }

        return actualProductList;
    }

    private Product extractItemData(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getLong("partNo"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getBoolean("isForSale"),
                resultSet.getBigDecimal("price"));
    }


    public Product getItemById(int itemId) throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM product WHERE id = " + itemId);

        if (resultSet.next()) {
            return extractItemData(resultSet);
        }

        return null;
    }

    public Product getItemByPartNo(Long itemPartNo) throws SQLException {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM product WHERE partNo = " + itemPartNo);

        if (resultSet.next()) {
            return extractItemData(resultSet);
        }

        return null;
    }

    public Integer saveNewItem(Product product) throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate(
                "INSERT INTO product (partNo, name, description, isForSale, price) VALUES" +
                        " ('" + product.getPartNo() +"', '" + product.getName() +"', '" + product.getDescription() + "', " + product.getIsForSale() + ", " + product.getPrice() +" )",
                Statement.RETURN_GENERATED_KEYS);

        return statement.getGeneratedKeys().getInt("id");
    }


    public void updatePriceItem(int id, BigDecimal newPrice) throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate("UPDATE product " +
                "SET price = '" + newPrice +"'  WHERE id = " + id);
    }

    public void deleteNotForSaleItem() throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate("DELETE FROM product WHERE isForSale = false");
    }



}
