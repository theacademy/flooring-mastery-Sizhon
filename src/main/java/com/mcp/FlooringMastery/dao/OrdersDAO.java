package com.mcp.FlooringMastery.dao;

import com.mcp.FlooringMastery.Exceptions.OrderDataPersistenceException;
import com.mcp.FlooringMastery.model.Order;
import com.mcp.FlooringMastery.model.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public interface OrdersDAO {
    HashMap<Integer, Order> getAllOrders();
    HashMap<Integer, Order> getGetOrdersFrom(LocalDate date);
    void addOrder(Order order, LocalDate date) throws OrderDataPersistenceException, IOException;
    Order getOrder(int orderNumber, LocalDate date);
    void importOrders() throws OrderDataPersistenceException;
    void exportOrders(HashMap<Integer, Order> orders, LocalDate date) throws OrderDataPersistenceException, IOException;
    void exportOrders(LocalDate date) throws OrderDataPersistenceException, IOException;

    void removeOrder(LocalDate date, Integer orderNumber) throws OrderDataPersistenceException, IOException;

    void findAndUpdate(Order orderFound1, String newName, String newState, Product newProduct, BigDecimal newArea, BigDecimal newTaxRate) throws OrderDataPersistenceException, IOException;
}
