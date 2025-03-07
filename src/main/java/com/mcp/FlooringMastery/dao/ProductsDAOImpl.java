package com.mcp.FlooringMastery.dao;

import com.mcp.FlooringMastery.model.Product;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

public class ProductsDAOImpl implements ProductsDAO {
    public ProductsDAOImpl() throws IOException {
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
    public void importProducts() throws IOException {
        Path directoryPath = Paths.get("./Data");
        if (!Files.exists(directoryPath)) Files.createDirectories(directoryPath);
        try (Scanner scan = new Scanner(new FileReader("./Data/Products.txt"))){
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
