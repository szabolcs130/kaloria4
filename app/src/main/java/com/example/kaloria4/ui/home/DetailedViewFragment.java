package com.example.kaloria4.ui.home;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kaloria4.R;
import com.example.kaloria4.adapter.EtkezesAdapter;
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
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detailed_view, container, false);
        LinearLayout containerLayout = root.findViewById(R.id.detailedContainerLayout);

        if (getArguments() != null) {
            String date = getArguments().getString("date", "Részletes nézet");

            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(date);

            tipusokraBontva = (Map<String, List<EtkezesOsszevont>>) getArguments().getSerializable(ARG_TIPUSOK);

            String[] tipusok = {"Reggeli", "Ebéd", "Vacsora", "Snack"};
            for (String tipus : tipusok) {
                List<EtkezesOsszevont> etkezesLista = tipusokraBontva.get(tipus);
                if (etkezesLista != null && !etkezesLista.isEmpty()) {

                    RecyclerView recyclerView = new RecyclerView(requireContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

                    EtkezesAdapter adapter = getEtkezesAdapter(etkezesLista);
                    recyclerView.setAdapter(adapter);
                    containerLayout.addView(recyclerView);
                }
            }
        }

        return root;
    }

    @NonNull
    private static EtkezesAdapter getEtkezesAdapter(List<EtkezesOsszevont> etkezesLista) {
        EtkezesAdapter adapter = new EtkezesAdapter(new EtkezesAdapter.ClickListener() {
            @Override
            public void updateClickedEtkezes(EtkezesOsszevont etkezes) {

            }

            @Override
            public void deleteClickedEtkezes(EtkezesOsszevont etkezes) {

            }
            @Override
            public void updateEtkezes(EtkezesOsszevont etkezes) {
            }

        },true);

        adapter.setDataWithoutDate(etkezesLista);
        return adapter;
    }
}
