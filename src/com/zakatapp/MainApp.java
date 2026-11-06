package com.zakatapp;

import com.zakatapp.model.*;
import com.zakatapp.util.InputHelper;

import java.util.ArrayList;
import java.util.List;

/** Program utama: Aplikasi Penghitung Zakat (menu berbasis console). */
public class MainApp {

    private static final double HARGA_EMAS_DEFAULT = 1_500_000; // ubah sesuai harga terbaru
    private static final double HARGA_BERAS_DEFAULT = 15_000;

    private final InputHelper in = new InputHelper();
    // Polymorphism: satu list untuk semua jenis zakat
    private final List<Zakat> riwayat = new ArrayList<>();
    private double hargaEmas = HARGA_EMAS_DEFAULT;

    public static void main(String[] args) {
        new MainApp().jalankan();
    }

    private void jalankan() {
        boolean lanjut = true;
        while (lanjut) {
            tampilMenu();
            int pilihan = in.bacaBulat("Pilih menu: ");
            try {
                switch (pilihan) {
                    case 1 -> prosesPenghasilan();
                    case 2 -> prosesMaal();
                    case 3 -> prosesEmas();
                    case 4 -> prosesFitrah();
                    case 5 -> tampilRiwayat();
                    case 6 -> ubahHargaEmas();
                    case 0 -> { lanjut = false; System.out.println("Terima kasih. Semoga berkah!"); }
                    default -> System.out.println("Menu tidak tersedia.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Data tidak valid: " + e.getMessage());
            }
        }
    }

    private void tampilMenu() {
        System.out.println("\n===== APLIKASI PENGHITUNG ZAKAT =====");
        System.out.println("1. Zakat Penghasilan");
        System.out.println("2. Zakat Maal");
        System.out.println("3. Zakat Emas");
        System.out.println("4. Zakat Fitrah");
        System.out.println("5. Riwayat Perhitungan");
        System.out.println("6. Ubah Harga Emas (saat ini Rp" + (long) hargaEmas + "/gram)");
        System.out.println("0. Keluar");
    }

    private void simpanDanTampil(Zakat z) {
        riwayat.add(z);
        System.out.println();
        System.out.print(z.cetakRingkasan());
    }

    private void prosesPenghasilan() {
        String nama = in.bacaTeks("Nama muzaki           : ");
        double gaji = in.bacaAngka("Gaji per bulan (Rp)   : ");
        double lain = in.bacaAngka("Penghasilan lain (Rp) : ");
        double pokok = in.bacaAngka("Kebutuhan pokok (Rp)  : ");
        simpanDanTampil(new ZakatPenghasilan(nama, hargaEmas, gaji, lain, pokok));
    }

    private void prosesMaal() {
        String nama = in.bacaTeks("Nama muzaki           : ");
        double tabungan = in.bacaAngka("Tabungan/deposito (Rp): ");
        double investasi = in.bacaAngka("Investasi (Rp)        : ");
        double utang = in.bacaAngka("Utang jatuh tempo (Rp): ");
        boolean haul = in.bacaYaTidak("Harta sudah dimiliki 1 tahun?");
        simpanDanTampil(new ZakatMaal(nama, hargaEmas, tabungan, investasi, utang, haul));
    }

    private void prosesEmas() {
        String nama = in.bacaTeks("Nama muzaki           : ");
        double berat = in.bacaAngka("Berat emas (gram)     : ");
        simpanDanTampil(new ZakatEmas(nama, hargaEmas, berat));
    }

    private void prosesFitrah() {
        String nama = in.bacaTeks("Nama muzaki           : ");
        int jiwa = in.bacaBulat("Jumlah jiwa          : ");
        double beras = in.bacaAngka("Harga beras/kg (Rp)  : ");
        if (beras == 0) beras = HARGA_BERAS_DEFAULT;
        simpanDanTampil(new ZakatFitrah(nama, hargaEmas, jiwa, beras));
    }

    private void tampilRiwayat() {
        if (riwayat.isEmpty()) {
            System.out.println("Belum ada perhitungan.");
            return;
        }
        System.out.println("\n--- RIWAYAT PERHITUNGAN ---");
        double total = 0;
        int no = 1;
        for (Zakat z : riwayat) {
            System.out.println(no++ + ". " + z);   // toString() polymorphic
            total += z.hitungZakat();
        }
        System.out.printf("Total zakat seluruhnya: Rp%,.0f%n", total);
    }

    private void ubahHargaEmas() {
        double baru = in.bacaAngka("Harga emas baru per gram (Rp): ");
        if (baru > 0) {
            hargaEmas = baru;
            System.out.println("Harga emas diperbarui.");
        } else {
            System.out.println("Harga harus lebih dari 0.");
        }
    }
}
