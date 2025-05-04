package com.example.kaloria4.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Profile {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nev;
    private int magassagCm;
    private int sulyKg;
    private int Cel;
    private String ImageUrl;
    private int CelKaloria;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }
    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getMagassagCm() {
        return magassagCm;
    }
    public void setMagassagCm(int magassagCm) {
        this.magassagCm = magassagCm;
    }

    public int getSulyKg() {
        return sulyKg;
    }
    public void setSulyKg(int sulyKg) {
        this.sulyKg = sulyKg;
    }

    public int getCel() {
        return Cel;
    }

    public void setCel(int cel) {
        Cel = cel;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public int getCelKaloria() {
        return CelKaloria;
    }

    public void setCelKaloria(int celKaloria) {
        CelKaloria = celKaloria;
    }
}