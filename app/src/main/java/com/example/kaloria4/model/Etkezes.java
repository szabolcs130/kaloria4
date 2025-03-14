package com.example.kaloria4.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "etkezes",
        foreignKeys = @ForeignKey(entity = Etel.class,
                parentColumns = "etelid",
                childColumns = "etkezesIdopontEtelId"))
public class Etkezes {
    @PrimaryKey(autoGenerate = true)
    private int etkezesId;
    private int etkezesIdopontEtelId;
    private String etkezesIdopontGramm;
    private long etkezesIdopontIdo;

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

    public String getEtkezesIdopontGramm() {
        return etkezesIdopontGramm;
    }

    public void setEtkezesIdopontGramm(String etkezesIdopontGramm) {
        this.etkezesIdopontGramm = etkezesIdopontGramm;
    }

    public long getEtkezesIdopontIdo() {
        return etkezesIdopontIdo;
    }

    public void setEtkezesIdopontIdo(long etkezesIdopontIdo) {
        this.etkezesIdopontIdo = etkezesIdopontIdo;
    }
}
