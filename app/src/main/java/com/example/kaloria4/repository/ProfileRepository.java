package com.example.kaloria4.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.kaloria4.dao.ProfileDao;
import com.example.kaloria4.database.Database;
import com.example.kaloria4.model.Profile;

import java.util.List;

public class ProfileRepository {
    private Database database;
    private ProfileDao profileDao;
    private LiveData<List<Profile>> profileList;

    public ProfileRepository(Application application) {
        database = Database.getDatabase(application);
        profileDao = database.profileDao();
        profileList = profileDao.getAllProfiles();
    }

    public LiveData<List<Profile>> getAllProfiles() {
        return profileDao.getAllProfiles();
    }

    public void insertProfile(Profile profile) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                profileDao.insertProfile(profile);
                return null;
            }
        }.execute();
    }

    public void updateProfile(final Profile profile) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                profileDao.updateProfile(profile);
                return null;
            }
        }.execute();
    }

    public void deleteProfile(final Profile profile) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                profileDao.deleteProfile(profile);
                return null;
            }
        }.execute();
    }
}