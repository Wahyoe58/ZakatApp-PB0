package com.zakatapp.model;

/** Zakat Fitrah: 2,5 kg beras per jiwa, dibayar dalam rupiah. */
public class ZakatFitrah extends Zakat {

    private static final double BERAS_PER_JIWA_KG = 2.5;

    private int jumlahJiwa;
    private double hargaBerasPerKg;

    public ZakatFitrah(String nama, double hargaEmas, int jumlahJiwa, double hargaBerasPerKg) {
        super(nama, hargaEmas);
        setJumlahJiwa(jumlahJiwa);
        setHargaBerasPerKg(hargaBerasPerKg);
    }

    public int getJumlahJiwa() { return jumlahJiwa; }
    public void setJumlahJiwa(int v) {
        if (v <= 0) throw new IllegalArgumentException("Jumlah jiwa minimal 1.");
        this.jumlahJiwa = v;
    }

    public double getHargaBerasPerKg() { return hargaBerasPerKg; }
    public void setHargaBerasPerKg(double v) {
        if (v <= 0) throw new IllegalArgumentException("Harga beras harus lebih dari 0.");
        this.hargaBerasPerKg = v;
    }

    @Override
    public String getJenisZakat() { return "Zakat Fitrah"; }

    @Override
    public double getTotalHarta() { return jumlahJiwa * BERAS_PER_JIWA_KG * hargaBerasPerKg; }

    /** Fitrah tidak memakai nisab. */
    @Override
    public double getNisab() { return 0; }

    /** Override: fitrah = total beras x harga, bukan 2,5%. */
    @Override
    public double hitungZakat() { return getTotalHarta(); }

    @Override
    protected String detailTambahan() {
        return String.format(" Jumlah Jiwa   : %d orang%n Beras         : %.1f kg%n Harga Beras/kg: %s%n",
                jumlahJiwa, jumlahJiwa * BERAS_PER_JIWA_KG, rupiah(hargaBerasPerKg));
    }
}
