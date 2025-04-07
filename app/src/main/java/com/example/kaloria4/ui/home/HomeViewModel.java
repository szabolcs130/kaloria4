package com.example.kaloria4.ui.home;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kaloria4.dao.EtkezesDao;
import com.example.kaloria4.model.EtkezesOsszevont;
import com.example.kaloria4.database.Database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<Map<String, Map<String, List<EtkezesOsszevont>>>> etkezesekDatumAlapjan;
    private EtkezesDao etkezesDao;

    public HomeViewModel(Context context) {
        etkezesekDatumAlapjan = new MutableLiveData<>();
        etkezesDao = Database.getDatabase(context).etkezesDao();

        etkezesDao.getAllEtkezes().observeForever(allMeals -> {
            if (allMeals != null) {
                Map<String, Map<String, List<EtkezesOsszevont>>> groupedByDateAndType = groupMealsByDateAndType(allMeals);
                etkezesekDatumAlapjan.setValue(groupedByDateAndType);
            }
        });
    }

    public LiveData<Map<String, Map<String, List<EtkezesOsszevont>>>> getEtkezesekDatumAlapjan() {
        return etkezesekDatumAlapjan;
    }

    private Map<String, Map<String, List<EtkezesOsszevont>>> groupMealsByDateAndType(List<EtkezesOsszevont> allMeals) {
        Map<String, Map<String, List<EtkezesOsszevont>>> grouped = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        for (EtkezesOsszevont meal : allMeals) {
            String dateKey = sdf.format(meal.getEtkezesIdopontIdo());
            String tipus = meal.getEtkezesTipus();

            grouped.putIfAbsent(dateKey, new HashMap<>());
            Map<String, List<EtkezesOsszevont>> tipusMap = grouped.get(dateKey);

            tipusMap.putIfAbsent(tipus, new ArrayList<>());
            tipusMap.get(tipus).add(meal);

            Log.d("HomeViewModel", "Dátum: " + dateKey + ", Típus: " + tipus);
        }

        return grouped;
    }
}
