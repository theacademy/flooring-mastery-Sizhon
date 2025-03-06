package com.mcp.FlooringMastery.model;

import java.math.BigDecimal;

public class Tax {
    private String stateCode;
    private String state;
    private BigDecimal taxRate;

    public Tax(String stateCode, String state, BigDecimal taxRate) {
        this.stateCode = stateCode;
        this.state = state;
        this.taxRate = taxRate;
    }

    public Tax() {

    }

    public String getState() {
        return state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
}
