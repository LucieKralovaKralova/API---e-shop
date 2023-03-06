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
    // Metoda pro zachycení případného zadání prázdného pole pro název produktu
    public boolean validationName (String newName) {
        if (newName == "") {
            return false;
        }
        return true;
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

    public Product saveNewItem(Product product) throws SQLException {
        Statement statement = connection.createStatement();

        String sqlString =
                "INSERT INTO product (partNo, name, description, isForSale, price) VALUES" +
                        " ('" + product.getPartNo() +"', '" + product.getName() +"', '" + product.getDescription() + "', " + product.getIsForSale() + ", " + product.getPrice() +" )";
        statement.executeUpdate(sqlString, Statement.RETURN_GENERATED_KEYS);

        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();
        product.setId(generatedKeys.getInt(1));

        return product;
    }


    public void updatePriceItem(int id, BigDecimal newPrice) throws SQLException {
            Statement statement = connection.createStatement();

            statement.executeUpdate("UPDATE product " +
                    "SET price = '" + newPrice +"'  WHERE id = " + id);

    }

    public Product updateItem(Product product) throws SQLException {
            Statement statement = connection.createStatement();

            statement.executeUpdate("UPDATE product " +
                    "SET partNo = '" + product.getPartNo() + "', name = '" + product.getName() +"', description = '" + product.getDescription() +"', isForSale = "+ product.getIsForSale() +", price = " + product.getPrice() +"  WHERE id = " +product.getId());

            return product;
    }

    public Product updateById(int itemId, Product product) throws SQLException {
            Statement statement = connection.createStatement();

            statement.executeUpdate("UPDATE product " +
                    "SET partNo = '" + product.getPartNo() + "', name = '" + product.getName() +"', description = '" + product.getDescription() +"', isForSale = "+ product.getIsForSale() +", price = " + product.getPrice() +"  WHERE id = " + itemId);

            return product;
    }

    public void deleteNotForSaleItem() throws SQLException {
        Statement statement = connection.createStatement();

        statement.executeUpdate("DELETE FROM product WHERE isForSale = false");
    }

    public void deleteItem(int id) throws SQLException {
            Statement statement = connection.createStatement();

            statement.executeUpdate("DELETE FROM product WHERE isForSale = false AND id ="+id);
    }



}
