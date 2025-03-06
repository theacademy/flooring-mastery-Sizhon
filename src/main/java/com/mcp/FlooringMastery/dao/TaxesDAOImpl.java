package com.mcp.FlooringMastery.dao;

import com.mcp.FlooringMastery.model.Tax;

import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class TaxesDAOImpl implements TaxesDAO {
    public TaxesDAOImpl() {
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
    public void importTaxes() {
        //String cwd = System.getProperty("user.dir");
        //System.out.println("Current working directory: " + cwd);
        try (Scanner scan = new Scanner(new FileReader("Taxes.txt"))){
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
            e.printStackTrace();
        }
    }
}
