package com.zakatapp.model;

/** Zakat Penghasilan (profesi) - dihitung per bulan. Turunan dari Zakat. */
public class ZakatPenghasilan extends Zakat {

    private double gajiPerBulan;
    private double penghasilanLain;
    private double kebutuhanPokok;

    public ZakatPenghasilan(String nama, double hargaEmas,
                            double gajiPerBulan, double penghasilanLain, double kebutuhanPokok) {
        super(nama, hargaEmas);
        setGajiPerBulan(gajiPerBulan);
        setPenghasilanLain(penghasilanLain);
        setKebutuhanPokok(kebutuhanPokok);
    }

    public double getGajiPerBulan() { return gajiPerBulan; }
    public void setGajiPerBulan(double v) { validasiTidakNegatif(v, "Gaji"); this.gajiPerBulan = v; }

    public double getPenghasilanLain() { return penghasilanLain; }
    public void setPenghasilanLain(double v) { validasiTidakNegatif(v, "Penghasilan lain"); this.penghasilanLain = v; }

    public double getKebutuhanPokok() { return kebutuhanPokok; }
    public void setKebutuhanPokok(double v) { validasiTidakNegatif(v, "Kebutuhan pokok"); this.kebutuhanPokok = v; }

    @Override
    public String getJenisZakat() { return "Zakat Penghasilan"; }

    @Override
    public double getTotalHarta() {
        return Math.max(0, gajiPerBulan + penghasilanLain - kebutuhanPokok);
    }

    /** Nisab penghasilan = (85 gram emas) / 12 bulan. */
    @Override
    public double getNisab() {
        return (NISAB_EMAS_GRAM * getHargaEmasPerGram()) / 12;
    }

    @Override
    protected String detailTambahan() {
        return String.format(" Gaji/Bulan    : %s%n Peng. Lain     : %s%n Keb. Pokok     : %s%n",
                rupiah(gajiPerBulan), rupiah(penghasilanLain), rupiah(kebutuhanPokok));
    }
}
