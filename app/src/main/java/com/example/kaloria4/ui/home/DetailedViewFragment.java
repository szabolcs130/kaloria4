package com.example.kaloria4.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kaloria4.R;
import com.example.kaloria4.adapter.EtkezesAdapter;
import com.example.kaloria4.model.Etkezes;
import com.example.kaloria4.model.EtkezesOsszevont;

import java.util.List;
import java.util.Map;

public class DetailedViewFragment extends Fragment {

    private static final String ARG_TIPUSOK = "tipusok";
    private Map<String, List<EtkezesOsszevont>> tipusokraBontva;

    public static DetailedViewFragment newInstance(Map<String, List<EtkezesOsszevont>> tipusokraBontva) {
        DetailedViewFragment fragment = new DetailedViewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIPUSOK, (java.io.Serializable) tipusokraBontva);
       // args.putString("selectedDate",selectedDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detailed_view, container, false);
        LinearLayout containerLayout = root.findViewById(R.id.detailedContainerLayout);

        String selectedDate = getArguments() != null ? getArguments().getString("selectedDate"): "Nincs dátum megadva";
        if (getArguments() != null) {
            tipusokraBontva = (Map<String, List<EtkezesOsszevont>>) getArguments().getSerializable(ARG_TIPUSOK);

            String[] tipusok = {"Reggeli", "Ebéd", "Vacsora", "Snack"};
            for (String tipus : tipusok) {
                List<EtkezesOsszevont> etkezesLista = tipusokraBontva.get(tipus);
                if (etkezesLista != null && !etkezesLista.isEmpty()) {



                    RecyclerView recyclerView = new RecyclerView(requireContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

                    EtkezesAdapter adapter = new EtkezesAdapter(new EtkezesAdapter.ClickListener() {
                        @Override
                        public void updateClickedEtkezes(Etkezes etkezes) {

                        }

                        @Override
                        public void deleteClickedEtkezes(Etkezes etkezes) {

                        }

                        @Override
                        public void updateClickedEtkezes(EtkezesOsszevont etkezes) {

                        }

                        @Override
                        public void deleteClickedEtkezes(EtkezesOsszevont etkezes) {

                        }

                        @Override
                        public void updateClickedEtkezes(Object etkezes) {

                        }

                        @Override
                        public void deleteClickedEtkezes(Object etkezes) {

                        }

                        @Override
                        public void onItemClick(int position) {

                        }

                        @Override
                        public void onItemClick(EtkezesOsszevont etkezes) {

                        }
                    });

                    adapter.setDataWithoutDate(etkezesLista);

                    recyclerView.setAdapter(adapter);
                    containerLayout.addView(recyclerView);
                }
            }
        }

        return root;
    }
}
