package com.example.kaloria4.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "etkezes",
        foreignKeys = @ForeignKey(entity = Etel.class,
                parentColumns = "etelid",
                childColumns = "etkezesIdopontEtelId"))
public class Etkezes {
    @PrimaryKey(autoGenerate = true)
    private int etkezesId;

    private int etkezesIdopontEtelId;
    private int etkezesIdopontGramm;
    private long etkezesIdopontIdo;

    private String etkezesTipus;

    public Etkezes() {
    }

    public Etkezes(int etkezesId, int etkezesIdopontEtelId, int etkezesIdopontGramm, long etkezesIdopontIdo, String etkezesTipus) {
        this.etkezesId = etkezesId;
        this.etkezesIdopontEtelId = etkezesIdopontEtelId;
        this.etkezesIdopontGramm = etkezesIdopontGramm;
        this.etkezesIdopontIdo = etkezesIdopontIdo;
        this.etkezesTipus = etkezesTipus;
    }

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

    public String getEtkezesTipus() {
        return etkezesTipus;
    }

    public void setEtkezesTipus(String etkezesTipus) {
        this.etkezesTipus = etkezesTipus;
    }
}
