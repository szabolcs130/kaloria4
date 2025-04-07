package com.example.kaloria4.ui.home;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kaloria4.R;
import com.example.kaloria4.adapter.EtkezesAdapter;
import com.example.kaloria4.model.EtkezesOsszevont;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private LinearLayout containerLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        containerLayout = root.findViewById(R.id.containerLayout);

        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory(requireContext())).get(HomeViewModel.class);

        homeViewModel.getEtkezesekDatumAlapjan().observe(getViewLifecycleOwner(), groupedData -> {
            containerLayout.removeAllViews();

            LayoutInflater layoutInflater = LayoutInflater.from(requireContext());

            for (Map.Entry<String, Map<String, List<EtkezesOsszevont>>> entry : groupedData.entrySet()) {
                String date = entry.getKey();
                Map<String, List<EtkezesOsszevont>> tipusokraBontva = entry.getValue();

                int napiOsszKaloria = 0;
                for (List<EtkezesOsszevont> lista : tipusokraBontva.values()) {
                    for (EtkezesOsszevont e : lista) {
                        napiOsszKaloria += e.getEtkezesIdopontGramm();
                    }
                }
                TextView dateHeader = new TextView(requireContext());
                dateHeader.setText(date + " - Össz: " + napiOsszKaloria + " kcal");
                dateHeader.setTextSize(22);
                dateHeader.setPadding(0, 24, 0, 16);
                dateHeader.setTextColor(getResources().getColor(R.color.black));
                dateHeader.setGravity(Gravity.CENTER);
                containerLayout.addView(dateHeader);
                dateHeader.setOnClickListener(v -> {
                    openDetailedView(date, tipusokraBontva);
                });
            }
        });

        return root;
    }

    private void openDetailedView(String date, Map<String, List<EtkezesOsszevont>> tipusokraBontva) {
        Bundle args = new Bundle();
        args.putString("date", date);
        args.putSerializable("tipusok", (Serializable) tipusokraBontva);

        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.detailedViewFragment, args);
    }

}
