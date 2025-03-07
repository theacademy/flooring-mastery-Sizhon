package com.mcp.FlooringMastery.controller;

import com.mcp.FlooringMastery.Exceptions.OrderDataPersistenceException;
import com.mcp.FlooringMastery.dao.DAOType;
import com.mcp.FlooringMastery.model.Order;
import com.mcp.FlooringMastery.model.Product;
import com.mcp.FlooringMastery.model.Tax;
import com.mcp.FlooringMastery.service.Service;
import com.mcp.FlooringMastery.view.MenuOption;
import com.mcp.FlooringMastery.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

@Component
public class FlooringMasteryController {
    UserView view;
    Service service;

    @Autowired
    public FlooringMasteryController(UserView view, Service service) {
        this.view = view;
        this.service = service;
    }

    public void run() {

        boolean keepGoing = true;
        while (keepGoing) {
            view.displayMenu();
            MenuOption menuSelection = view.getMenuSelection();
            switch (menuSelection) {
                case DISPLAY_ORDERS:
                    view.displayOrders(service.getOrdersFrom(view.datePrompt()));
                    break;
                case ADD_ORDER:
                    HashMap<String, Product> products = service.getDAOMap(DAOType.PRODUCTS);
                    HashMap<String, Tax> taxes = service.getDAOMap(DAOType.TAXES);
                    Order order = new Order();
                    LocalDate dateToAdd = view.datePrompt();
                    String name = view.namePrompt();
                    order.setCustomerName(name);
                    String state = view.statePrompt();
                    if (!taxes.containsKey(state)) {
                        view.stateDNE(taxes, state);
                        break;
                    }
                    order.setState(state);
                    order.setTaxRate(taxes.get(state).getTaxRate());
                    String productType = view.productPrompt();
                    if (products.containsKey(productType)) {
                        order.setProduct(products.get(productType));
                    } else {
                        view.productDNE();
                        break;
                    }
                    BigDecimal area = view.areaPrompt();
                    order.setArea(area);
                    boolean makeOrder = view.promptConfirmOrder();
                    if (makeOrder) {
                        try {
                            service.addOrder(order, dateToAdd);
                            view.orderAdded();
                        } catch (OrderDataPersistenceException | IOException e) {
                            e.printStackTrace();
                            view.orderAddError();
                        }

                    };
                    break;
                case EDIT_ORDER:
                    // name state product area
                    LocalDate date1 = view.datePrompt();
                    Integer orderNumber1 = view.orderNumberPrompt();
                    Order orderFound1 = service.findOrder(date1, orderNumber1);
                    if (orderFound1 == null) {
                        view.orderDNE();
                        break;
                    }
                    String newName = view.namePromptForEdit(orderFound1.getCustomerName());
                    String newState = view.statePromptForEdit(orderFound1.getState());
                    String newProductType = view.productPromptForEdit(orderFound1.getProductType());
                    Product newProduct = service.getProduct(newProductType);
                    BigDecimal newArea = view.areaPromptForEdit(orderFound1.getArea());

                    try {
                        service.updateOrder(orderFound1, newName, newState, newProduct, newArea);
                    } catch (OrderDataPersistenceException | IOException e) {
                        view.orderUpdateError();
                    }
                    view.displayOrder(orderFound1);
                    view.orderEditSuccess();
                    break;
                case REMOVE_ORDER:
                    LocalDate date = view.datePrompt();
                    Integer orderNumber = view.orderNumberPrompt();
                    Order orderFound = service.findOrder(date, orderNumber);
                    if (orderFound == null) {
                        view.orderDNE();
                        break;
                    }
                    view.displayOrder(orderFound);
                    if (!view.confirmOrderDeletion()) {
                        break;
                    }
                    try {
                        service.removeOrder(date, orderNumber);
                    } catch (OrderDataPersistenceException | IOException e) {
                        view.orderDeletionError();
                    }
                    view.orderRemoved();
                    break;
                case EXPORT:
                    /*try {
                        //service.exportOrders(LocalDate.now());
                        view.exportSuccess();
                    } catch (OrderDataPersistenceException e) {
                        e.printStackTrace();
                    }*/
                    view.saveCurrentWork();
                    break;
                case QUIT:
                    keepGoing = false;
                    break;
                default:
                    view.displayUnknownCommand();
            }
            view.promptToReturnToMenu();
        }
    }
}
