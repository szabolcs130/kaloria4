package com.example.kaloria4.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kaloria4.model.Etkezes;

import java.util.List;

@Dao
public interface EtkezesDao {
    @Insert
    void insertEtkezes(Etkezes etkezes);
    @Update
    void updateEtkezes(Etkezes etkezes);
    @Delete
    void deleteEtkezes(Etkezes etkezes);
    @Query("Select * from etkezes")
    LiveData<List<Etkezes>> getAllEtkezes();
}