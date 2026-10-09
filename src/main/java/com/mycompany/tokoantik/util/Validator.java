/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tokoantik.util;

/**
 *
 * @author ASUS
 */

import java.util.Scanner;

public final class Validator {
    private Validator() {}
    
    public static int inputInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("-> Input tidak valid! Harap masukkan angka bulat.");
            }
        }
    }

    public static int inputIntOptional(Scanner sc, String prompt, int defaultValue) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                return defaultValue;
            }
            try {
                int val = Integer.parseInt(input);
                if (val < 0) {
                    System.out.println("-> Nilai tidak boleh negatif!");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("-> Input tidak valid! Harap masukkan angka.");
            }
        }
    }

    public static int inputIntMin(Scanner sc, String prompt, int min) {
        while (true) {
            int value = inputInt(sc, prompt);
            if (value < min) {
                System.out.println("-> Nilai tidak boleh kurang dari " + min);
                continue;
            }
            return value;
        }
    }

    public static double inputDoubleMin(Scanner sc, String prompt, double min) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                double value = Double.parseDouble(input);
                if (value < min) {
                    System.out.println("-> Nilai tidak boleh kurang dari " + min);
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("-> Input tidak valid! Harap masukkan angka.");
            }
        }
    }

    public static double inputDoubleOptional(Scanner sc, String prompt, double defaultValue) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                return defaultValue;
            }
            try {
                double val = Double.parseDouble(input);
                if (val < 0) {
                    System.out.println("-> Nilai tidak boleh negatif!");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("-> Input tidak valid! Harap masukkan angka.");
            }
        }
    }

    public static String inputString(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("-> Input tidak boleh kosong!");
                continue;
            }
            return input;
        }
    }

    public static String inputStringOptional(Scanner sc, String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = sc.nextLine().trim();
        if (input.isEmpty()) {
            return defaultValue;
        }
        return input;
    }

    public static int inputPilihanMenu(Scanner sc, String prompt, int min, int max) {
        while (true) {
            int pilihan = inputInt(sc, prompt);
            if (pilihan < min || pilihan > max) {
                System.out.println("-> Pilihan harus antara " + min + " dan " + max);
                continue;
            }
            return pilihan;
        }
    }
}