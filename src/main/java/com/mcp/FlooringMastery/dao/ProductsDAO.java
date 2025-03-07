package com.mcp.FlooringMastery.dao;

import com.mcp.FlooringMastery.model.Product;

import java.io.IOException;
import java.util.HashMap;

public interface ProductsDAO {
    HashMap<String, Product> products = new HashMap<>();
    HashMap<String, Product> getAllProducts();
    Product getProduct(String productType);
    void importProducts() throws IOException;
}
