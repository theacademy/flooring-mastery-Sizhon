import com.mcp.FlooringMastery.dao.ProductsDAOImpl;
import com.mcp.FlooringMastery.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ProductsDAOImplTest {
    private ProductsDAOImpl productsDAO;

    @BeforeAll
    static void cwdConfirmation() {
        String currentWorkingDirectory = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentWorkingDirectory);
    }

    @BeforeEach
    void setUpEach() {
        productsDAO = new ProductsDAOImpl();
    }

    @Test
    void testGetAllProducts() {
        HashMap<String, Product> products = productsDAO.getAllProducts();
        assertNotNull(products);
        assertFalse(products.isEmpty());
    }

    @Test
    void testGetProduct() {
        Product product = productsDAO.getProduct("Wood");
        assertNotNull(product);
        assertEquals("Wood", product.getProductType());
        assertEquals(new BigDecimal("5.15"), product.getCostPerSquareFoot());
        assertEquals(new BigDecimal("4.75"), product.getLaborCostPerSquareFoot());
    }

    @Test
    void testGetProduct2() {
        Product product = productsDAO.getProduct("Laminate");
        assertNotNull(product);
        assertEquals("Laminate", product.getProductType());
        assertEquals(new BigDecimal("1.75"), product.getCostPerSquareFoot());
        assertEquals(new BigDecimal("2.10"), product.getLaborCostPerSquareFoot());
    }
}