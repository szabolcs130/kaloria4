package com.example.kaloria4.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kaloria4.model.Etkezes;
import com.example.kaloria4.repository.EtkezesRepository;

import java.util.List;

public class EtkezesViewModel extends AndroidViewModel {
    private EtkezesRepository etkezesRepository;
    private LiveData<List<Etkezes>> etkezesList;
    public EtkezesViewModel(@NonNull Application application) {
        super(application);
        etkezesRepository=new EtkezesRepository(application);
        etkezesList= etkezesRepository.getAllEtkezes();

    }
    //valtozas proba 2
    public LiveData<List<Etkezes>> getAllEtkezes() {
        return etkezesRepository.getAllEtkezes();
    }
    public void insertEtkezes(Etkezes etkezes){
        etkezesRepository.inserEtkezes(etkezes);
    }
    public void updateUsers(Etkezes etkezes){
        etkezesRepository.updateEtkezes(etkezes);
    }
    public void deleteEtkezes(Etkezes etkezes){etkezesRepository.deleteEtkezes(etkezes);
    }

}