package com.zakatapp.model;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Class induk (superclass) abstrak untuk semua jenis zakat.
 * Menerapkan ENCAPSULATION: field bersifat private, diakses lewat getter/setter.
 */
public abstract class Zakat {

    // ===== Konstanta yang dapat diakses subclass (protected) =====
    protected static final double TARIF_ZAKAT = 0.025;   // 2,5%
    protected static final double NISAB_EMAS_GRAM = 85;  // 85 gram emas

    // ===== Data yang dilindungi (private) =====
    private String namaMuzaki;
    private double hargaEmasPerGram;

    public Zakat(String namaMuzaki, double hargaEmasPerGram) {
        setNamaMuzaki(namaMuzaki);
        setHargaEmasPerGram(hargaEmasPerGram);
    }

    // ===== Getter & Setter dengan validasi =====
    public String getNamaMuzaki() {
        return namaMuzaki;
    }

    public void setNamaMuzaki(String namaMuzaki) {
        if (namaMuzaki == null || namaMuzaki.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama muzaki tidak boleh kosong.");
        }
        this.namaMuzaki = namaMuzaki.trim();
    }

    public double getHargaEmasPerGram() {
        return hargaEmasPerGram;
    }

    public void setHargaEmasPerGram(double hargaEmasPerGram) {
        if (hargaEmasPerGram <= 0) {
            throw new IllegalArgumentException("Harga emas harus lebih dari 0.");
        }
        this.hargaEmasPerGram = hargaEmasPerGram;
    }

    // ===== Method abstrak: wajib di-override oleh subclass =====
    public abstract String getJenisZakat();

    /** Total harta yang dihitung (dasar perhitungan zakat). */
    public abstract double getTotalHarta();

    /** Batas minimum harta (nisab) untuk jenis zakat ini. */
    public abstract double getNisab();

    // ===== Method yang dapat diwarisi apa adanya (atau di-override) =====
    public boolean isWajibZakat() {
        return getTotalHarta() >= getNisab();
    }

    public double hitungZakat() {
        return isWajibZakat() ? getTotalHarta() * TARIF_ZAKAT : 0;
    }

    // ===== Method bantu yang hanya boleh dipakai subclass =====
    protected void validasiTidakNegatif(double nilai, String namaField) {
        if (nilai < 0) {
            throw new IllegalArgumentException(namaField + " tidak boleh negatif.");
        }
    }

    protected String rupiah(double nilai) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
        nf.setMaximumFractionDigits(0);
        return nf.format(nilai);
    }

    /** Detail khusus tiap jenis zakat (bisa di-override). */
    protected String detailTambahan() {
        return "";
    }

    public String cetakRingkasan() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append(" HASIL PERHITUNGAN ").append(getJenisZakat().toUpperCase()).append("\n");
        sb.append("========================================\n");
        sb.append(String.format(" Nama Muzaki   : %s%n", namaMuzaki));
        sb.append(String.format(" Harga Emas/gr : %s%n", rupiah(hargaEmasPerGram)));
        sb.append(detailTambahan());
        sb.append(String.format(" Total Harta   : %s%n", rupiah(getTotalHarta())));
        sb.append(String.format(" Nisab         : %s%n", rupiah(getNisab())));
        if (isWajibZakat()) {
            sb.append(" Status        : WAJIB ZAKAT\n");
            sb.append(String.format(" Zakat Dibayar : %s%n", rupiah(hitungZakat())));
        } else {
            sb.append(" Status        : Belum wajib zakat (belum mencapai nisab)\n");
        }
        sb.append("========================================\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("%-18s | %-15s | %s", getJenisZakat(), namaMuzaki, rupiah(hitungZakat()));
    }
}
