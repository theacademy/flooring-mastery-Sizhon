package com.mcp.FlooringMastery.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Order {
    int orderNumber;
    String customerName;
    String state;
    BigDecimal taxRate;
    Product product;


    BigDecimal materialCost;
    BigDecimal area;
    BigDecimal costPerSquareFoot;
    BigDecimal laborCostPerSquareFoot;
    BigDecimal laborCostTotal;

    BigDecimal tax;
    BigDecimal totalCost;

    LocalDate date;

    public Order() {
    }

    private void finalizeOrder() {
        this.materialCost = calculateMaterialCost(area, costPerSquareFoot);
        this.laborCostTotal = calculateLaborCost(area, laborCostPerSquareFoot);
        this.tax = calculateTax(materialCost, laborCostTotal, taxRate);
        this.totalCost = calculateTotalCost();
        this.date = LocalDate.now();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        updateTaxRate();
    }

    private void updateTaxRate() {

    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.costPerSquareFoot = product.getCostPerSquareFoot();
        this.laborCostPerSquareFoot = product.getLaborCostPerSquareFoot();
    }

    public void setArea(BigDecimal area) {
        this.area = area;
        finalizeOrder();
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return product.getProductType();
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public BigDecimal calculateMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot) {
        materialCost = area.multiply(costPerSquareFoot)
                .setScale(2, RoundingMode.CEILING);
        return materialCost;
    }

    public BigDecimal calculateLaborCost(BigDecimal area, BigDecimal laborCostPerSquareFoot) {
        laborCostTotal = area.multiply(laborCostPerSquareFoot)
                .setScale(2, RoundingMode.CEILING);
        return laborCostTotal;
    }

    public BigDecimal calculateTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate) {
        BigDecimal totalBeforeTax = materialCost.add(laborCost);
        taxRate = taxRate.setScale(10, RoundingMode.HALF_UP)
                .divide(new BigDecimal("100"), RoundingMode.HALF_UP);
        tax = totalBeforeTax
                .multiply(taxRate)
                .setScale(2, RoundingMode.CEILING);
        return tax;
    }

    public BigDecimal calculateTotalCost() {
        totalCost = materialCost.add(laborCostTotal)
                .add(tax)
                .setScale(2, RoundingMode.CEILING);
        return totalCost;
    }

    public String toString(){
        return "Order Number: " + orderNumber + "\n" +
                "Customer Name: " + customerName + "\n" +
                "State: " + state + "\n" +
                "Tax Rate: %" + taxRate + "\n" +
                "Product Type: " + product.getProductType() + "\n" +
                "Area: " + area + "sqft.\n" +
                "Cost Per Square Foot: $" + costPerSquareFoot + "\n" +
                "Labor Cost Per Square Foot: $" + laborCostPerSquareFoot + "\n" +
                "Material Cost: $" + materialCost + "\n" +
                "Labor Cost: $" + laborCostTotal + "\n" +
                "Tax: $" + tax + "\n" +
                "Total: $" + totalCost + "\n";
    }

    public BigDecimal getArea() {
        return area;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLaborCostTotal() {
        return laborCostTotal;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return totalCost;
    }

    public LocalDate getDate() {
        return date;
    }
}
