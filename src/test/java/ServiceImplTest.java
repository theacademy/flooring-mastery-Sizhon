import com.mcp.FlooringMastery.Exceptions.OrderDataPersistenceException;
import com.mcp.FlooringMastery.dao.DAOType;
import com.mcp.FlooringMastery.dao.OrdersDAOFileImpl;
import com.mcp.FlooringMastery.dao.ProductsDAOImpl;
import com.mcp.FlooringMastery.model.Order;
import com.mcp.FlooringMastery.model.Product;
import com.mcp.FlooringMastery.model.Tax;
import com.mcp.FlooringMastery.service.ServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @InjectMocks
    private ServiceImpl service;

    @BeforeAll
    static void setUpAll() {
        String currentWorkingDirectory = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentWorkingDirectory);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    <K, V>
    void testGetDAOMap() throws OrderDataPersistenceException, IOException {
        HashMap<K, V> result = service.getDAOMap(DAOType.PRODUCTS);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.values().stream().allMatch(item -> item instanceof Product));

        result = service.getDAOMap(DAOType.TAXES);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.values().stream().allMatch(item -> item instanceof Tax));
    }

    @Test
    void testGetOrdersFrom() {
        LocalDate date = LocalDate.now();
        Object result = service.getOrdersFrom(date);
        assertNotNull(result);
    }

    @Test
    void testCalculateMaterialCost() {
        BigDecimal area = new BigDecimal("100");
        BigDecimal costPerSquareFoot = new BigDecimal("5.15");
        BigDecimal result = service.calculateMaterialCost(area, costPerSquareFoot);
        assertNotNull(result);
        assertEquals(new BigDecimal("515.00"), result);
    }

    @Test
    void testCalculateLaborCost() {
        BigDecimal area = new BigDecimal("100");
        BigDecimal laborCostPerSquareFoot = new BigDecimal("4.75");
        BigDecimal result = service.calculateLaborCost(area, laborCostPerSquareFoot);
        assertNotNull(result);
        assertEquals(new BigDecimal("475.00"), result);
    }

    @Test
    void testCalculateTax() {
        BigDecimal materialCost = new BigDecimal("515.00");
        BigDecimal laborCost = new BigDecimal("475.00");
        BigDecimal taxRate = new BigDecimal("6.75");
        BigDecimal result = service.calculateTax(materialCost, laborCost, taxRate);
        assertNotNull(result);
        assertEquals(new BigDecimal("66.83"), result);
    }
}