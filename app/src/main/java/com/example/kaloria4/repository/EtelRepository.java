package com.example.kaloria4.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.kaloria4.database.Database;
import com.example.kaloria4.dao.EtelDao;
import com.example.kaloria4.model.Etel;

import java.util.List;

public class EtelRepository {
    private Database database;
    private EtelDao etelDao;
    private LiveData<List<Etel>> usersList;

    public EtelRepository(Application application) {
        database = Database.getDatabase(application);
        etelDao = database.etelDao();
        usersList = etelDao.getAllEtel();
    }

    public LiveData<List<Etel>> getAllEtel() {
        return etelDao.getAllEtel();
    }

    public void inserEtel(Etel etel) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                etelDao.insertEtel(etel);
                return null;
            }
        }.execute();
    }

    public void updateEtel(final Etel etel) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                etelDao.updateEtel(etel);
                return null;
            }
        }.execute();
    }

    public void deleteEtel(final Etel etel) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                etelDao.deleteEtel(etel);
                return null;
            }
        }.execute();
    }

    public LiveData<List<Etel>> searchEtel(String query) {
        return etelDao.searchEtel("%" + query + "%");
    }
}
