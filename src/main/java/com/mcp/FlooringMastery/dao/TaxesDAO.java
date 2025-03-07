package com.mcp.FlooringMastery.dao;

import com.mcp.FlooringMastery.model.Tax;

import java.io.IOException;
import java.util.HashMap;

public interface TaxesDAO {
    HashMap<String, Tax> taxes = new HashMap<>();
    HashMap<String, Tax> getAllTaxes();
    Tax getTax(String state);
    void importTaxes() throws IOException;
}
