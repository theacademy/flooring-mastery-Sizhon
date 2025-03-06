package com.mcp.FlooringMastery.view;


import com.mcp.FlooringMastery.model.Order;
import com.mcp.FlooringMastery.model.Tax;
import com.mcp.FlooringMastery.service.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Set;

import static com.mcp.FlooringMastery.service.Validators.isNameValid;

@Component
public class UserView {
    UserIO io;
    Validators validators;

    @Autowired
    public UserView(UserIO io) {
        this.io = io;
    }

    public void displayMenu() {
        String bannerEdge = "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *";
        io.print(bannerEdge);
        io.print(String.format("%-"+ (bannerEdge.length() - 1) + "s*", "* <<Flooring Program>>"));
        io.print(String.format("%-"+ (bannerEdge.length() - 1) + "s*", "* 1. Display Orders"));
        io.print(String.format("%-"+ (bannerEdge.length() - 1) + "s*", "* 2. Add an Order"));
        io.print(String.format("%-"+ (bannerEdge.length() - 1) + "s*", "* 3. Edit an Order"));
        io.print(String.format("%-"+ (bannerEdge.length() - 1) + "s*", "* 4. Remove an Order"));
        io.print(String.format("%-"+ (bannerEdge.length() - 1) + "s*", "* 5. Save Current Work"));
        io.print(String.format("%-"+ (bannerEdge.length() - 1) + "s*", "* 6. Quit"));
        io.print(bannerEdge);
    }

    public MenuOption getMenuSelection() {
        int selection = io.readInt("Please select what you would like to do!(1-6)", 1, 6);
        return MenuOption.fromInt(selection);
    }

    public void displayOrders(HashMap<Integer, Order> orders) {
        io.print("Orders:");
        for (Order order : orders.values()) {
            io.print(order.toString());
        }
    }

    public String namePrompt() {
        String name = io.readString("Please enter name:");
        if (name.isEmpty() || !isNameValid(name)) {
            io.print("Name cannot be empty or contain special characters.");
            return namePrompt();
        }
        return name;
    }

    public String statePrompt() {
        String state = io.readString("Please select state:");
        if (state.length() != 2) {
            io.print("State must be two characters.");
            return statePrompt();
        }
        return state;
    }

    public String productPrompt() {
        return io.readString("Please enter product type:");
    }

    public void productDNE(){
        io.print("Product does not exist.");
    }

    public void editOrder() {
    }

    public void removeOrder() {
    }

    public void saveCurrentWork() {
        io.print("Work saved");
    }

    public void displayUnknownCommand() {
    }

    public BigDecimal areaPrompt() {
        double area = io.readDouble("Please enter area(minimum of 100):", 100, Double.MAX_VALUE);
        return BigDecimal.valueOf(area);
    }

    public boolean promptConfirmOrder() {
        String input = io.readString("Would you like to confirm this order? Y/N");
        return input.equalsIgnoreCase("Y");
    }

    public LocalDate datePrompt() {
        String dateString = io.readString("Please enter date in the format of MM/dd/yyyy:");
        if (dateString.isEmpty()) return LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            io.print("Invalid date format. Please enter the date in the format of MM/dd/yyyy.");
            return datePrompt();
}
    }

    public void orderAdded() {
        io.print("Order added.");
    }

    public void orderRemoved() {
        io.print("Order removed.");
    }

    public void exportSuccess() {
        io.print("Orders exported.");
    }

    public void promptToReturnToMenu() {
        io.readString("Press enter to return to menu.");
    }

    public void stateDNE(HashMap<String, Tax> taxes, String state) {
        io.print("Sorry we don't sell to %s".formatted(state));
        //io.print(String.format("We only sell to %s", taxes.values().stream().map(Tax::getState).reduce((a, b) -> a + ", " + b).get()));
    }

    public Integer orderNumberPrompt() {
        return io.readInt("Please enter order number:");
    }

    public void orderDNE() {
        io.print("Order does not exist.");
    }

    public boolean confirmOrderDeletion() {
        String answer = io.readString("Are you sure you want to delete this order? Y/M");
        return (answer.equalsIgnoreCase("Y"));
    }

    public void orderDeletionError() {
        io.print("Error deleting order.");
    }

    public void displayOrder(Order orderFound) {
        io.print(orderFound.toString());
    }

    public String namePromptForEdit(String customerName) {
        String name = io.readString("Enter customer name (%s):".formatted(customerName));
        if (!name.isEmpty()) {
            return name;
        }
        return customerName;
    }

    public String statePromptForEdit(String state) {
        String newState = io.readString("Enter state (%s):".formatted(state));
        if (!newState.isEmpty()) {
            return newState;
        }
        return state;
    }

    public String productPromptForEdit(String productType) {
        String product = io.readString("Enter product type (%s):".formatted(productType));
        if (!product.isEmpty()) {
            return product;
        }
        return productType;
    }

    public BigDecimal areaPromptForEdit(BigDecimal area) {
        String newAreaString = io.readString("Enter area (%s):".formatted(area));
        if (newAreaString.isEmpty()) return area;
        BigDecimal newArea = new BigDecimal(newAreaString);
        if (newArea.compareTo(new BigDecimal(100)) < 0) {
            io.print("Area must be at least 100.");
            return areaPromptForEdit(area);
        }
        return newArea;
    }

    public void orderEditSuccess() {
        io.print("Order edited.");
    }

    public void orderUpdateError() {
        io.print("Error updating order.");
    }

    public void orderAddError() {
        io.print("Error adding order.");
    }
}
