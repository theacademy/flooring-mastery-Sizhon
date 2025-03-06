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
}