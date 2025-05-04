package com.example.kaloria4.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.kaloria4.model.Profile;

import java.util.List;

@Dao
public interface ProfileDao {

    @Insert
    void insertProfile(Profile profile);

    @Update
    void updateProfile(Profile profile);

    @Delete
    void deleteProfile(Profile profile);

    @Query("SELECT * FROM Profile")
    LiveData<List<Profile>> getAllProfiles();
}