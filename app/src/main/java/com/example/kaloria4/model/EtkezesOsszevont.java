package com.example.kaloria4.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;


public class EtkezesOsszevont {
    public int etkezesId;
    public int etkezesIdopontEtelId;
    public int etkezesIdopontGramm;
    public long etkezesIdopontIdo;
    private String etkezesTipus;

    public int etelid;
    public String etkezesIdopontEtelNev;
    public double kaloria;

    public int getEtkezesId() {
        return etkezesId;
    }

    public void setEtkezesId(int etkezesId) {
        this.etkezesId = etkezesId;
    }

    public int getEtkezesIdopontEtelId() {
        return etkezesIdopontEtelId;
    }

    public void setEtkezesIdopontEtelId(int etkezesIdopontEtelId) {
        this.etkezesIdopontEtelId = etkezesIdopontEtelId;
    }

    public int getEtkezesIdopontGramm() {
        return etkezesIdopontGramm;
    }

    public void setEtkezesIdopontGramm(int etkezesIdopontGramm) {
        this.etkezesIdopontGramm = etkezesIdopontGramm;
    }

    public long getEtkezesIdopontIdo() {
        return etkezesIdopontIdo;
    }

    public void setEtkezesIdopontIdo(long etkezesIdopontIdo) {
        this.etkezesIdopontIdo = etkezesIdopontIdo;
    }

    public int getEtelid() {
        return etelid;
    }

    public void setEtelid(int etelid) {
        this.etelid = etelid;
    }

    public String getEtkezesIdopontEtelNev() {
        return etkezesIdopontEtelNev;
    }

    public void setEtkezesIdopontEtelNev(String etkezesIdopontEtelNev) {
        this.etkezesIdopontEtelNev = etkezesIdopontEtelNev;
    }

    public double getKaloria() {
        return kaloria;
    }

    public void setKaloria(double kaloria) {
        this.kaloria = kaloria;
    }

    public String getEtkezesTipus() {
        return etkezesTipus;
    }

    public void setEtkezesTipus(String etkezesTipus) {
        this.etkezesTipus = etkezesTipus;
    }
    @Override
    public String toString() {
        return "EtkezesOsszevont{" +
                "etkezesId=" + etkezesId +
                ", etkezesIdopontGramm=" + etkezesIdopontGramm +
                ", etkezesIdopontIdo=" + etkezesIdopontIdo +
                ", etkezesIdopontEtelNev='" + etkezesIdopontEtelNev + '\'' +
                ", kaloria=" + kaloria +
                ", etkezesTipus='" + etkezesTipus + '\'' +
                '}';
    }
}
