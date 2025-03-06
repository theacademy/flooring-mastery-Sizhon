package com.mcp.FlooringMastery.view;

import org.springframework.stereotype.Component;

@Component
public class UserIOConsoleImp implements UserIO {

    /**
     * @param message
     */
    @Override
    public void print(String message) {
        System.out.println(message);
    }

    /**
     * @param prompt
     * @return
     */
    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return scan.nextLine().trim();
    }

    /**
     * @param prompt
     * @return
     */
    @Override
    public int readInt(String prompt) {
        System.out.println(prompt);
        try {
            return Integer.parseInt(scan.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return readInt(prompt);
        }
    }

    /**
     * @param prompt
     * @param min
     * @param max
     * @return
     */
    @Override
    public int readInt(String prompt, int min, int max) {
        System.out.println(prompt);
        try {
            int input = Integer.parseInt(scan.nextLine().trim());
            if (input < min || input > max) {
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                return readInt(prompt, min, max);
            }
            return input;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return readInt(prompt, min, max);
        }
    }

    /**
     * @param prompt
     * @return
     */
    @Override
    public double readDouble(String prompt) {
        System.out.println(prompt);
        try {
            return Double.parseDouble(scan.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return readInt(prompt);
        }
    }

    /**
     * @param prompt
     * @param min
     * @param max
     * @return
     */
    @Override
    public double readDouble(String prompt, double min, double max) {
        System.out.println(prompt);
        try {
            double input = Double.parseDouble(scan.nextLine().trim());
            if (input < min || input > max) {
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                return readDouble(prompt, min, max);
            }
            return input;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return readDouble(prompt, min, max);
        }
    }

    /**
     * @param prompt
     * @return
     */
    @Override
    public float readFloat(String prompt) {
        System.out.println(prompt);
        try {
            return Float.parseFloat(scan.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return readFloat(prompt);
        }
    }

    /**
     * @param prompt
     * @param min
     * @param max
     * @return
     */
    @Override
    public float readFloat(String prompt, float min, float max) {
        System.out.println(prompt);
        try {
            float input = Float.parseFloat(scan.nextLine().trim());
            if (input < min || input > max) {
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                return readFloat(prompt, min, max);
            }
            return input;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return readFloat(prompt, min, max);
        }
    }

    /**
     * @param prompt
     * @return
     */
    @Override
    public long readLong(String prompt) {
        System.out.println(prompt);
        try {
            return Long.parseLong(scan.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return readLong(prompt);
        }
    }

    /**
     * @param prompt
     * @param min
     * @param max
     * @return
     */
    @Override
    public long readLong(String prompt, long min, long max) {
        System.out.println(prompt);
        try {
            long input = Long.parseLong(scan.nextLine().trim());
            if (input < min || input > max) {
                System.out.println("Invalid input. Please enter a number between " + min + " and " + max + ".");
                return readLong(prompt, min, max);
            }
            return input;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return readLong(prompt, min, max);
        }
    }
}
