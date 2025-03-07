package com.mcp.FlooringMastery.dao;

import com.mcp.FlooringMastery.Exceptions.OrderDataPersistenceException;
import com.mcp.FlooringMastery.model.Order;
import com.mcp.FlooringMastery.model.Product;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class OrdersDAOFileImpl implements OrdersDAO {
    HashMap<Integer, Order> orders = new HashMap<>();
    Integer orderNumber = 0;

    /**
     * @return
     */
    @Override
    public HashMap<Integer, Order> getAllOrders() {
        return orders;
    }

    /**
     * @param date
     * @return
     */
    @Override
    public HashMap<Integer, Order> getGetOrdersFrom(LocalDate date) {
        return readFileFromDateAsHashMap(date);
    }

    private HashMap<Integer, Order> readFileFromDateAsHashMap(LocalDate date) {
        HashMap<Integer, Order> map = new HashMap<>();
        orderNumber = 0;
        try (Scanner scan = new Scanner(new FileReader("./Orders/Orders_%s.txt"
                     .formatted(date.format(DateTimeFormatter.ofPattern("MMddyyyy")))))) {
             String header = scan.nextLine();
             while (scan.hasNextLine()) {
                 String[] orderData = scan.nextLine().split(",");
                 Order order = new Order();
                 int readOrderNumber = Integer.parseInt(orderData[0]);
                 order.setOrderNumber(readOrderNumber);
                 orderNumber = Math.max(readOrderNumber, orderNumber);
                 order.setCustomerName(orderData[1]);
                 order.setState(orderData[2]);
                 order.setTaxRate(BigDecimal.valueOf(Double.parseDouble(orderData[3])));

                 BigDecimal costPerSquareFoot = new BigDecimal(orderData[6]);
                 BigDecimal laborCostPerSquareFoot = new BigDecimal(orderData[7]);

                 order.setProduct(new Product(orderData[4], costPerSquareFoot, laborCostPerSquareFoot));
                 order.setArea(BigDecimal.valueOf(Double.parseDouble(orderData[5])));
                 map.put(order.getOrderNumber(), order);
             }
        } catch (FileNotFoundException e) {
             return new HashMap<>();
         }
        return map;
    }

    /**
     * @param order
     */
    @Override
    public void addOrder(Order order, LocalDate date) throws OrderDataPersistenceException, IOException {
        orders = readFileFromDateAsHashMap(date);
        order.setOrderNumber(++orderNumber);
        orders.put(orderNumber, order);
        exportOrders(orders, date);
    }

    /**
     * @param orderNumber
     * @param date
     * @return
     */
    @Override
    public Order getOrder(int orderNumber, LocalDate date) {
        HashMap<Integer, Order> map = readFileFromDateAsHashMap(date);
        return map.get(orderNumber);
    }

    /**
     * @throws OrderDataPersistenceException
     */
    @Override
    public void importOrders() throws OrderDataPersistenceException {
        orders = readFileFromDateAsHashMap(LocalDate.now());
    }

    /**
     * @throws OrderDataPersistenceException
     */
    @Override
    public void exportOrders(HashMap<Integer, Order> orders, LocalDate date) throws OrderDataPersistenceException, IOException {
        writeToFile(date, orders);
    }

    /**
     * @param date
     * @throws OrderDataPersistenceException
     * @throws IOException
     */
    @Override
    public void exportOrders(LocalDate date) throws OrderDataPersistenceException, IOException {
        writeToFile(date, this.orders);
    }

    private void writeToFile(LocalDate date, HashMap<Integer, Order> orders) throws IOException {
        Path directoryPath = Paths.get("./Orders");
        if (!Files.exists(directoryPath)) Files.createDirectories(directoryPath);
        PrintWriter out = new PrintWriter(new FileWriter("./Orders/Orders_%s.txt".
                formatted(date.format(DateTimeFormatter.ofPattern("MMddyyyy")))));
        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        for (Order order : orders.values()) {
            out.println(order.getOrderNumber() + ","
                    + order.getCustomerName() + ","
                    + order.getState() + ","
                    + order.getTaxRate() + ","
                    + order.getProductType() + ","
                    + order.getArea() + ","
                    + order.getCostPerSquareFoot() + ","
                    + order.getLaborCostPerSquareFoot() + ","
                    + order.getMaterialCost() + ","
                    + order.getLaborCostTotal() + ","
                    + order.getTax() + ","
                    + order.getTotal());
        }
        out.close();
    }

    /**
     * @param date
     * @param orderNumber
     */
    @Override
    public void removeOrder(LocalDate date, Integer orderNumber) throws OrderDataPersistenceException, IOException {
        HashMap<Integer, Order> map = getGetOrdersFrom(date);
        map.remove(orderNumber);
        exportOrders(map, date);
    }

    /**
     * @param orderFound1
     * @param newName
     * @param newState
     * @param newProduct
     * @param newArea
     * @param newTaxRate
     */
    @Override
    public void findAndUpdate(Order orderFound1, String newName, String newState, Product newProduct, BigDecimal newArea, BigDecimal newTaxRate) throws OrderDataPersistenceException, IOException {
        HashMap<Integer, Order> map = getGetOrdersFrom(orderFound1.getDate());
        Order order = map.get(orderFound1.getOrderNumber());
        order.setState(newState);
        order.setCustomerName(newName);
        order.setProduct(newProduct);
        order.setTaxRate(newTaxRate);
        order.setArea(newArea);

        map.put(orderFound1.getOrderNumber(), order);
        exportOrders(map, order.getDate());
    }
}
