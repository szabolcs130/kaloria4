package com.example.kaloria4.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kaloria4.model.Etel;
import com.example.kaloria4.repository.EtelRepository;

import java.util.List;

public class EtelViewModel extends AndroidViewModel {
    private EtelRepository etelRepository;
    private LiveData<List<Etel>> etelList;
    public EtelViewModel(@NonNull Application application) {
        super(application);
        etelRepository=new EtelRepository(application);
        etelList= etelRepository.getAllEtel();

    }
//valtozas proba 2
    public LiveData<List<Etel>> getAllEtel() {
        return etelRepository.getAllEtel();
    }
    public void insertUser(Etel etel){
        etelRepository.inserEtel(etel);
    }
    public void updateUsers(Etel etel){
        etelRepository.updateEtel(etel);
    }
    public void deleteUsers(Etel etel){etelRepository.deleteEtel(etel);
    }

}
