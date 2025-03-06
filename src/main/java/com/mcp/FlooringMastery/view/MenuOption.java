package com.mcp.FlooringMastery.view;

public enum MenuOption {
    DISPLAY_ORDERS(1),
    ADD_ORDER(2),
    EDIT_ORDER(3),
    REMOVE_ORDER(4),
    EXPORT(5),
    QUIT(6),
    INVALID(-1);

    private final int value;

    MenuOption(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static MenuOption fromInt(int value) {
        for (MenuOption option : MenuOption.values()) {
            if (option.getValue() == value) {
                return option;
            }
        }
        throw new IllegalArgumentException("Invalid menu option: " + value);
    }
}