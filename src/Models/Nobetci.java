package Models;

import java.util.Date;

public class Nobetci {
    public int nobetciId;
    public String nobetciAd;
    public String nobetciAdres;
    public String nobetciTarif;
    public String nobetciTel;
    public String nobetciHaritaAdres;
    public Date nobetciTarih;
    public int nobetciIlceId;
    public boolean nobetciDurum;

    public int getNobetciIlceId() {
        return nobetciIlceId;
    }

    public void setNobetciIlceId(int nobetciIlceId) {
        this.nobetciIlceId = nobetciIlceId;
    }

    public boolean isNobetciDurum() {
        return nobetciDurum;
    }

    public void setNobetciDurum(boolean nobetciDurum) {
        this.nobetciDurum = nobetciDurum;
    }

    public int getNobetciId() {
        return nobetciId;
    }

    public void setNobetciId(int nobetciId) {
        this.nobetciId = nobetciId;
    }

    public String getNobetciAd() {
        return nobetciAd;
    }

    public void setNobetciAd(String nobetciAd) {
        this.nobetciAd = nobetciAd;
    }

    public String getNobetciAdres() {
        return nobetciAdres;
    }

    public void setNobetciAdres(String nobetciAdres) {
        this.nobetciAdres = nobetciAdres;
    }

    public String getNobetciTarif() {
        return nobetciTarif;
    }

    public void setNobetciTarif(String nobetciTarif) {
        this.nobetciTarif = nobetciTarif;
    }

    public String getNobetciTel() {
        return nobetciTel;
    }

    public void setNobetciTel(String nobetciTel) {
        this.nobetciTel = nobetciTel;
    }

    public Date getNobetciTarih() {
        return nobetciTarih;
    }

    public void setNobetciTarih(Date nobetciTarih) {
        this.nobetciTarih = nobetciTarih;
    }

    public String getNobetciHaritaAdres() {
        return nobetciHaritaAdres;
    }

    public void setNobetciHaritaAdres(String nobetciHaritaAdres) {
        this.nobetciHaritaAdres = nobetciHaritaAdres;
    }
}
