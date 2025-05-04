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
    @Query("SELECT etkezes.etkezesId AS etkezesId, etkezes.etkezesIdopontEtelId AS etkezesIdopontEtelId,etkezes.etkezesIdopontGramm AS etkezesIdopontGramm,etkezes.etkezesTipus as etkezesTipus,etkezes.etkezesIdopontIdo AS etkezesIdopontIdo,etel.etelid AS etelid,etel.etelnev AS etkezesIdopontEtelNev,etel.kaloria AS kaloria  FROM etkezes INNER JOIN etel ON etkezes.etkezesIdopontEtelId = etel.etelid")
    LiveData<List<EtkezesOsszevont>> getAllEtkezes();

    @Query("SELECT etkezes.etkezesId AS etkezesId, " +
            "etkezes.etkezesIdopontEtelId AS etkezesIdopontEtelId," +
            "etkezes.etkezesIdopontGramm AS etkezesIdopontGramm," +
            "etkezes.etkezesTipus as etkezesTipus," +
            "etkezes.etkezesIdopontIdo AS etkezesIdopontIdo," +
            "etel.etelid AS etelid," +
            "etel.etelnev AS etkezesIdopontEtelNev," +
            "SUM((etel.kaloria * etkezes.etkezesIdopontGramm) / 100.0) AS kaloria  " +
            "FROM etkezes INNER JOIN etel ON etkezes.etkezesIdopontEtelId = etel.etelid" +
            " GROUP BY date(etkezes.etkezesIdopontIdo / 1000, 'unixepoch')"+
            " ORDER BY date(etkezes.etkezesIdopontIdo / 1000, 'unixepoch') DESC")
        //" ORDER BY kaloria DESC")
    LiveData<List<EtkezesOsszevont>> getAllEtkezesMaxKaloria();
    @Query("SELECT MAX(nagy.kaloria) AS kaloria, " +
            "nagy.etkezesId AS etkezesId, " +
            "nagy.etkezesIdopontEtelId AS etkezesIdopontEtelId, " +
            "nagy.etkezesIdopontGramm AS etkezesIdopontGramm, " +
            "nagy.etkezesTipus AS etkezesTipus, " +
            "nagy.etkezesIdopontIdo AS etkezesIdopontIdo, " +
            "nagy.etelid AS etelid, " +
            "nagy.etkezesIdopontEtelNev AS etkezesIdopontEtelNev " +
            "FROM ( " +
            "SELECT etkezes.etkezesId AS etkezesId, " +
            "etkezes.etkezesIdopontEtelId AS etkezesIdopontEtelId, " +
            "etkezes.etkezesIdopontGramm AS etkezesIdopontGramm, " +
            "etkezes.etkezesTipus AS etkezesTipus, " +
            "etkezes.etkezesIdopontIdo AS etkezesIdopontIdo, " +
            "etel.etelid AS etelid, " +
            "etel.etelnev AS etkezesIdopontEtelNev, " +
            "SUM((etel.kaloria * etkezes.etkezesIdopontGramm) / 100.0) AS kaloria " +
            "FROM etkezes " +
            "INNER JOIN etel ON etkezes.etkezesIdopontEtelId = etel.etelid " +
            "GROUP BY date(etkezes.etkezesIdopontIdo / 1000, 'unixepoch') " +
            "ORDER BY kaloria DESC " +
            ") AS nagy")
    LiveData<List<EtkezesOsszevont>> getAllEtkezesMaxKaloriaMax();
    @Query("SELECT etkezes.etkezesId AS etkezesId," +
            " etkezes.etkezesIdopontEtelId AS etkezesIdopontEtelId," +
            "etkezes.etkezesIdopontGramm AS etkezesIdopontGramm," +
            "etkezes.etkezesTipus as etkezesTipus," +
            " etkezes.etkezesIdopontIdo  AS etkezesIdopontIdo," +
            "etel.etelid AS etelid," +
            "etel.etelnev AS etkezesIdopontEtelNev," +
            "SUM((etel.kaloria * etkezes.etkezesIdopontGramm) / 100.0) AS kaloria  " +
            "FROM etkezes INNER JOIN etel ON etkezes.etkezesIdopontEtelId = etel.etelid"+
            " Group By strftime('%Y', etkezes.etkezesIdopontIdo / 1000, 'unixepoch'),strftime('%W', etkezesIdopontIdo / 1000, 'unixepoch')")
    LiveData<List<EtkezesOsszevont>> getAllEtkezesGroupByHet();
    @Query("SELECT etkezes.etkezesId AS etkezesId, " +
            "etkezes.etkezesIdopontEtelId AS etkezesIdopontEtelId," +
            "etkezes.etkezesIdopontGramm AS etkezesIdopontGramm," +
            "etkezes.etkezesTipus as etkezesTipus," +
            "etkezes.etkezesIdopontIdo AS etkezesIdopontIdo," +
            "etel.etelid AS etelid," +
            "etel.etelnev AS etkezesIdopontEtelNev," +
            "SUM((etel.kaloria * etkezes.etkezesIdopontGramm) / 100.0) AS kaloria  " +
            "FROM etkezes INNER JOIN etel ON etkezes.etkezesIdopontEtelId = etel.etelid " +
            " WHERE date(etkezes.etkezesIdopontIdo / 1000, 'unixepoch') = date('now')")
    LiveData<List<EtkezesOsszevont>> getAllEtkezesFogyaszthato();
}
//MAX((etel.kaloria+0.0)/100*etkezes.etkezesIdopontGramm)