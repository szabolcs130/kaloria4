package com.example.kaloria4.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.kaloria4.dao.EtkezesDao;
import com.example.kaloria4.database.Database;
import com.example.kaloria4.model.Etkezes;
import com.example.kaloria4.model.EtkezesOsszevont;

import java.util.List;

public class EtkezesRepository {
    private Database database;
    private EtkezesDao etkezesDao;
    private LiveData<List<EtkezesOsszevont>> etkezesList;

    public EtkezesRepository(Application application) {
        database=Database.getDatabase(application);
        etkezesDao= database.etkezesDao();
        etkezesList= etkezesDao.getAllEtkezes();
    }
    public LiveData<List<EtkezesOsszevont>> getAllEtkezes(){
        return database.etkezesDao().getAllEtkezes();
    }
    public void inserEtkezes(Etkezes etkezes){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.etkezesDao().insertEtkezes(etkezes);
                return null;
            }
        }.execute();
    }
    public void updateEtkezes(final Etkezes etkezes){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.etkezesDao().updateEtkezes(etkezes);
                return null;
            }
        }.execute();
    }
    public void deleteEtkezes(final Etkezes etkezes){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.etkezesDao().deleteEtkezes(etkezes);
                return null;
            }
        }.execute();
    }
}
