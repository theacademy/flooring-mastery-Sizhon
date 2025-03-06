package com.mcp.FlooringMastery.service;

import com.mcp.FlooringMastery.Exceptions.OrderDataPersistenceException;
import com.mcp.FlooringMastery.dao.*;
import com.mcp.FlooringMastery.model.Order;
import com.mcp.FlooringMastery.model.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public interface Service {
    <K, V> HashMap<K, V> getDAOMap(DAOType daoType);



    BigDecimal calculateMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot);
    BigDecimal calculateLaborCost(BigDecimal area, BigDecimal laborCostPerSquareFoot);
    BigDecimal calculateTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate);
    BigDecimal calculateTotal(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax);
    void addOrder(Order order, LocalDate date) throws OrderDataPersistenceException, IOException;

    void exportOrders(LocalDate date) throws OrderDataPersistenceException;

    HashMap<Integer, Order> getOrdersFrom(LocalDate date);

    Order findOrder(LocalDate date, Integer orderNumber);

    void removeOrder(LocalDate date, Integer orderNumber) throws OrderDataPersistenceException, IOException;

    Product getProduct(String newProductType);

    void updateState(Order orderFound1, String newState);

    void updateOrder(Order orderFound1, String newName, String newState, Product newProduct, BigDecimal newArea) throws OrderDataPersistenceException, IOException;
}
