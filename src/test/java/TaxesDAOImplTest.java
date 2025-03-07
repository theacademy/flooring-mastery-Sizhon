import com.mcp.FlooringMastery.dao.TaxesDAOImpl;
import com.mcp.FlooringMastery.model.Product;
import com.mcp.FlooringMastery.model.Tax;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TaxesDAOImplTest {
    private TaxesDAOImpl TaxesDAO;

    @BeforeAll
    static void cwdConfirmation() {
        String currentWorkingDirectory = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentWorkingDirectory);
    }

    @BeforeEach
    void setUpEach() throws IOException {
        TaxesDAO = new TaxesDAOImpl();
    }

    @Test
    void testGetAllTaxes() {
        HashMap<String, Tax> Taxes = TaxesDAO.getAllTaxes();
        assertNotNull(Taxes);
        assertFalse(Taxes.isEmpty());
    }

    @Test
    void testGetProduct() {
        Tax tax = TaxesDAO.getTax("TX");
        assertNotNull(tax);
        assertEquals("TX", tax.getStateCode());
        assertEquals("Texas", tax.getState());
        assertEquals(new BigDecimal("4.45"), tax.getTaxRate());
    }

    @Test
    void testGetProduct2() {
        Tax tax = TaxesDAO.getTax("KY");
        assertNotNull(tax);
        assertEquals("KY", tax.getStateCode());
        assertEquals("Kentucky", tax.getState());
        assertEquals(new BigDecimal("6.0"), tax.getTaxRate());
    }
}