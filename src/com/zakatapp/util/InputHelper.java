package com.zakatapp.util;

import java.util.Scanner;

/** Membaca input dari pengguna dengan validasi sederhana. */
public class InputHelper {

    private final Scanner scanner = new Scanner(System.in);

    public String bacaTeks(String label) {
        System.out.print(label);
        return scanner.nextLine();
    }

    public double bacaAngka(String label) {
        while (true) {
            System.out.print(label);
            try {
                double nilai = Double.parseDouble(scanner.nextLine().trim());
                if (nilai < 0) {
                    System.out.println("  ! Nilai tidak boleh negatif.");
                    continue;
                }
                return nilai;
            } catch (NumberFormatException e) {
                System.out.println("  ! Masukkan angka yang valid.");
            }
        }
    }

    public int bacaBulat(String label) {
        return (int) bacaAngka(label);
    }

    public boolean bacaYaTidak(String label) {
        System.out.print(label + " (y/t): ");
        return scanner.nextLine().trim().equalsIgnoreCase("y");
    }
}
