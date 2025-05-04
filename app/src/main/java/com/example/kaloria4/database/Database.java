package com.example.kaloria4.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.kaloria4.alakit;
import com.example.kaloria4.dao.EtelDao;
import com.example.kaloria4.dao.EtkezesDao;
import com.example.kaloria4.dao.ProfileDao;
import com.example.kaloria4.model.Etel;
import com.example.kaloria4.model.Etkezes;
import com.example.kaloria4.model.Profile;

@androidx.room.Database(entities={Etel.class, Etkezes.class, Profile.class}, version = 8, exportSchema = false)
@TypeConverters(alakit.class)
public abstract class Database extends RoomDatabase {

    public abstract EtelDao etelDao();
    public abstract EtkezesDao etkezesDao();
    public abstract ProfileDao profileDao();

    public static volatile Database INSTANCE;

    public static Database getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, Database.class, "kaloria8.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}