package com.zakatapp.model;

/** Zakat Emas / Perak perhiasan simpanan. */
public class ZakatEmas extends Zakat {

    private double beratEmasGram;

    public ZakatEmas(String nama, double hargaEmas, double beratEmasGram) {
        super(nama, hargaEmas);
        setBeratEmasGram(beratEmasGram);
    }

    public double getBeratEmasGram() { return beratEmasGram; }
    public void setBeratEmasGram(double v) { validasiTidakNegatif(v, "Berat emas"); this.beratEmasGram = v; }

    @Override
    public String getJenisZakat() { return "Zakat Emas"; }

    @Override
    public double getTotalHarta() { return beratEmasGram * getHargaEmasPerGram(); }

    @Override
    public double getNisab() { return NISAB_EMAS_GRAM * getHargaEmasPerGram(); }

    @Override
    protected String detailTambahan() {
        return String.format(" Berat Emas    : %.2f gram%n", beratEmasGram);
    }
}
