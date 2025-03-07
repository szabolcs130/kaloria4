package com.example.kaloria4.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.kaloria4.dao.EtelDao;
import com.example.kaloria4.dao.EtkezesDao;
import com.example.kaloria4.model.Etel;
import com.example.kaloria4.model.Etkezes;

@androidx.room.Database(entities={Etel.class, Etkezes.class},version = 5,exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract EtelDao etelDao();
    public abstract EtkezesDao etkezesDao();
    public static volatile Database INSTANCE;
    public static  Database getDatabase(Context context){
        if (INSTANCE == null){
            synchronized (Database.class){
                if (INSTANCE == null) {
                    INSTANCE= Room.databaseBuilder(context,Database.class,"kaloria4.db").build();
                }
            }
        }
        return INSTANCE;

    }
}
