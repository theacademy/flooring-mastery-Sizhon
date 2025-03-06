package com.mcp.FlooringMastery.service;

import java.time.LocalDate;

public class Validators {

    public static boolean isNameValid(String name) {
        return name.matches("[a-z A-Z,.]+");
    }

    public static boolean isValidDate(LocalDate date) {
        return !date.isBefore(LocalDate.now());
    }

    public static boolean isValidArea(double area) {
        return area > 100;
    }
}
