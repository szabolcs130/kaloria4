package com.example.kaloria4.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.kaloria4.model.Profile;
import com.example.kaloria4.repository.ProfileRepository;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel {
    private ProfileRepository profileRepository;
    private LiveData<List<Profile>> profile;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new ProfileRepository(application);
        profile = profileRepository.getAllProfiles();
    }

    public LiveData<List<Profile>> getProfile() {
        return profile;
    }

    public void insertUser(Profile profile){
        profileRepository.insertProfile(profile);
    }

    public void updateUsers(Profile profile){
        profileRepository.updateProfile(profile);
    }

    public void deleteUsers(Profile profile){
        profileRepository.deleteProfile(profile);
    }
}
