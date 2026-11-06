package com.zakatapp.model;

/** Zakat Maal (harta simpanan). Wajib bila mencapai nisab dan sudah 1 tahun (haul). */
public class ZakatMaal extends Zakat {

    private double tabungan;
    private double investasi;
    private double utangJatuhTempo;
    private boolean sudahSatuTahun;

    public ZakatMaal(String nama, double hargaEmas, double tabungan,
                     double investasi, double utangJatuhTempo, boolean sudahSatuTahun) {
        super(nama, hargaEmas);
        setTabungan(tabungan);
        setInvestasi(investasi);
        setUtangJatuhTempo(utangJatuhTempo);
        this.sudahSatuTahun = sudahSatuTahun;
    }

    public double getTabungan() { return tabungan; }
    public void setTabungan(double v) { validasiTidakNegatif(v, "Tabungan"); this.tabungan = v; }

    public double getInvestasi() { return investasi; }
    public void setInvestasi(double v) { validasiTidakNegatif(v, "Investasi"); this.investasi = v; }

    public double getUtangJatuhTempo() { return utangJatuhTempo; }
    public void setUtangJatuhTempo(double v) { validasiTidakNegatif(v, "Utang"); this.utangJatuhTempo = v; }

    public boolean isSudahSatuTahun() { return sudahSatuTahun; }
    public void setSudahSatuTahun(boolean v) { this.sudahSatuTahun = v; }

    @Override
    public String getJenisZakat() { return "Zakat Maal"; }

    @Override
    public double getTotalHarta() {
        return Math.max(0, tabungan + investasi - utangJatuhTempo);
    }

    @Override
    public double getNisab() { return NISAB_EMAS_GRAM * getHargaEmasPerGram(); }

    /** Override: selain nisab, harta harus sudah dimiliki satu tahun (haul). */
    @Override
    public boolean isWajibZakat() {
        return sudahSatuTahun && super.isWajibZakat();
    }

    @Override
    protected String detailTambahan() {
        return String.format(" Tabungan      : %s%n Investasi      : %s%n Utang          : %s%n Haul (1 thn)   : %s%n",
                rupiah(tabungan), rupiah(investasi), rupiah(utangJatuhTempo),
                sudahSatuTahun ? "Ya" : "Belum");
    }
}
