package com.example.kaloria4.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kaloria4.model.Etel;

import java.util.List;

@Dao
public interface EtelDao {
    @Insert
void insertEtel(Etel etel);
    @Update
    void updateEtel(Etel etel);
    @Delete
    void deleteEtel(Etel etel);
    @Query("Select * from etel")
    LiveData<List<Etel>> getAllEtel();
    @Query("SELECT * FROM etel WHERE etelnev LIKE :query")
    LiveData<List<Etel>> searchEtel(String query);


}
