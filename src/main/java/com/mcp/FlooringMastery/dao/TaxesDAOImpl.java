package com.mcp.FlooringMastery.dao;

import com.mcp.FlooringMastery.model.Tax;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

@Component
public class TaxesDAOImpl implements TaxesDAO {
    public TaxesDAOImpl() throws IOException {
        importTaxes();
    }

    /**
     * @return
     */
    @Override
    public HashMap<String, Tax> getAllTaxes() {
        return taxes;
    }

    /**
     * @param stateCode
     * @return Tax
     */
    @Override
    public Tax getTax(String stateCode) {
        return taxes.get(stateCode);
    }

    /**
     *
     */
    @Override
    public void importTaxes() throws IOException {
        //String cwd = System.getProperty("user.dir");
        //System.out.println("Current working directory: " + cwd);
        Path directoryPath = Paths.get("./Data");
        if (!Files.exists(directoryPath)) Files.createDirectories(directoryPath);
        try (Scanner scan = new Scanner(new FileReader("./Data/Taxes.txt"))){
            String header = scan.nextLine();
            while (scan.hasNextLine()) {
                String[] taxData = scan.nextLine().split(",");
                Tax tax = new Tax();
                tax.setStateCode(taxData[0]);
                tax.setState(taxData[1]);
                tax.setTaxRate(BigDecimal.valueOf(Double.parseDouble(taxData[2])));
                taxes.put(tax.getStateCode(), tax);
        }
        } catch (FileNotFoundException e) {

        }
    }
}
