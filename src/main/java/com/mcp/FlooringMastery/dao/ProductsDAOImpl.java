package com.mcp.FlooringMastery.dao;

import com.mcp.FlooringMastery.model.Product;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

public class ProductsDAOImpl implements ProductsDAO {
    public ProductsDAOImpl() {
        importProducts();
    }

    /**
     * @return HashMap<String, Product>
     */
    @Override
    public HashMap<String, Product> getAllProducts() {
        return products;
    }

    /**
     * @param productType
     * @return
     */
    @Override
    public Product getProduct(String productType) {
        return products.get(productType);
    }

    /**
     *
     */
    @Override
    public void importProducts() {
        try (Scanner scan = new Scanner(new FileReader("Products.txt"))){
            String header = scan.nextLine();
            while (scan.hasNextLine()) {
                String[] productData = scan.nextLine().split(",");
                String productType = productData[0];
                BigDecimal costPerSquareFoot = new BigDecimal(productData[1]);
                BigDecimal laborCostPerSquareFoot = new BigDecimal(productData[2]);
                Product product = new Product(productType, costPerSquareFoot, laborCostPerSquareFoot);
                products.put(product.getProductType(), product);
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
