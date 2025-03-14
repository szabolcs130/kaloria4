package com.example.kaloria4.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "etel")
public class Etel {
    @PrimaryKey(autoGenerate = true)
    private int etelid;
    private String etelnev;
    private String kaloria;

    public int getEtelid() {
        return etelid;
    }

    @Override
    public String toString() {
        return etelnev +" "+ kaloria;
    }

    public void setEtelid(int etelid) {
        this.etelid = etelid;
    }

    public String getEtelnev() {
        return etelnev;
    }

    public void setEtelnev(String etelnev) {
        this.etelnev = etelnev;
    }

    public String getKaloria() {
        return kaloria;
    }

    public void setKaloria(String kaloria) {
        this.kaloria = kaloria;
    }
}
