package com.mcp.FlooringMastery.service;

import com.mcp.FlooringMastery.Exceptions.OrderDataPersistenceException;
import com.mcp.FlooringMastery.dao.*;
import com.mcp.FlooringMastery.model.Order;
import com.mcp.FlooringMastery.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;

@Component
public class ServiceImpl implements Service {
    private final OrdersDAO ordersDAO;
    private final ProductsDAO productsDAO;
    private final TaxesDAO taxesDAO;

    @Autowired
    public ServiceImpl(OrdersDAO ordersDAO, ProductsDAO productsDAO, TaxesDAO taxesDAO) {
        this.ordersDAO = ordersDAO;
        this.productsDAO = productsDAO;
        this.taxesDAO = taxesDAO;
    }


    /**
     * @param <K>
     * @param <V>
     * @param daoType
     * @return
     */
    @Override
    public <K, V> HashMap<K, V> getDAOMap(DAOType daoType) {
        return switch (daoType) {
            case ORDERS -> (HashMap<K, V>) ordersDAO.getAllOrders();
            case PRODUCTS -> (HashMap<K, V>) productsDAO.getAllProducts();
            case TAXES -> (HashMap<K, V>) taxesDAO.getAllTaxes();
        };
    }

    public HashMap<Integer, Order> getOrdersFrom(LocalDate date) {
        return ordersDAO.getGetOrdersFrom(date);
    }

    /**
     * @param date
     * @param orderNumber
     * @return
     */
    @Override
    public Order findOrder(LocalDate date, Integer orderNumber) {
        return ordersDAO.getOrder(orderNumber, date);
    }

    /**
     * @param date
     * @param orderNumber
     */
    @Override
    public void removeOrder(LocalDate date, Integer orderNumber) throws OrderDataPersistenceException, IOException {
        ordersDAO.removeOrder(date, orderNumber);
    }

    /**
     * @param newProductType
     * @return
     */
    @Override
    public Product getProduct(String newProductType) {
       return productsDAO.getProduct(newProductType);
    }

    /**
     * @param orderFound1
     * @param newState
     */
    @Override
    public void updateState(Order orderFound1, String newState) {
        orderFound1.setState(newState);
        orderFound1.setTaxRate(taxesDAO.getTax(newState).getTaxRate());
    }

    /**
     * @param orderFound1
     * @param newName
     * @param newState
     * @param newProduct
     * @param newArea
     */
    @Override
    public void updateOrder(Order orderFound1, String newName, String newState, Product newProduct, BigDecimal newArea) throws OrderDataPersistenceException, IOException {
        BigDecimal newTaxRate = taxesDAO.getTax(newState).getTaxRate();
        ordersDAO.findAndUpdate(orderFound1, newName, newState, newProduct, newArea, newTaxRate);
    }

    /**
     * @param area
     * @param costPerSquareFoot
     * @return
     */
    @Override
    public BigDecimal calculateMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot) {
        return area.multiply(costPerSquareFoot);
    }

    /**
     * @param area
     * @param laborCostPerSquareFoot
     * @return
     */
    @Override
    public BigDecimal calculateLaborCost(BigDecimal area, BigDecimal laborCostPerSquareFoot) {
        return area.multiply(laborCostPerSquareFoot);
    }

    /**
     * @param materialCost
     * @param laborCost
     * @param taxRate
     * @return
     */
    @Override
    public BigDecimal calculateTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate) {
        taxRate = taxRate.setScale(10, RoundingMode.HALF_UP);
        return (materialCost.add(laborCost))
                .multiply(taxRate.divide(new BigDecimal("100"), RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * @param materialCost
     * @param laborCost
     * @param tax
     * @return
     */
    @Override
    public BigDecimal calculateTotal(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax) {
        return materialCost.add(laborCost).add(tax);
    }

    /**
     * @param order
     */
    @Override
    public void addOrder(Order order, LocalDate date) throws OrderDataPersistenceException, IOException {
        ordersDAO.addOrder(order, date);
    }

    /**
     *
     */
    @Override
    public void exportOrders(LocalDate date) throws OrderDataPersistenceException {
        try {
            ordersDAO.exportOrders(date);
        } catch (IOException e) {
            throw new OrderDataPersistenceException("Could not export orders.", e);
        }
    }

    /**
     * @param orderNumber
     * @param date
     * @return
     */
    public Order getOrder(int orderNumber, LocalDate date) {
        return ordersDAO.getOrder(orderNumber, date);
    }
}
