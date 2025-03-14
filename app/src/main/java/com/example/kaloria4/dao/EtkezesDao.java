package com.example.kaloria4.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.kaloria4.model.Etkezes;
import com.example.kaloria4.model.EtkezesOsszevont;

import java.util.List;

@Dao
public interface EtkezesDao {
    @Insert
    void insertEtkezes(Etkezes etkezes);
    @Update
    void updateEtkezes(Etkezes etkezes);
    @Delete
    void deleteEtkezes(Etkezes etkezes);
    //SELECT * FROM user_table u INNER JOIN task_table t ON u.userId = t.userOwnerId
    @Query("SELECT etkezes.etkezesId AS etkezesId, etkezes.etkezesIdopontEtelId AS etkezesIdopontEtelId,etkezes.etkezesIdopontGramm AS etkezesIdopontGramm,etkezes.etkezesIdopontIdo AS etkezesIdopontIdo,etel.etelid AS etelid,etel.etelnev AS etkezesIdopontEtelNev,etel.kaloria AS kaloria  FROM etkezes INNER JOIN etel ON etkezes.etkezesIdopontEtelId = etel.etelid")
    LiveData<List<EtkezesOsszevont>> getAllEtkezes();
}