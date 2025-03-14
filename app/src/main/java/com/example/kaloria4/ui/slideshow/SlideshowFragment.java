package com.example.kaloria4.ui.slideshow;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kaloria4.R;
import com.example.kaloria4.adapter.EtkezesAdapter;
import com.example.kaloria4.databinding.FragmentSlideshowBinding;
import com.example.kaloria4.model.Etel;
import com.example.kaloria4.model.Etkezes;
import com.example.kaloria4.model.EtkezesOsszevont;
import com.example.kaloria4.viewmodel.EtelViewModel;
import com.example.kaloria4.viewmodel.EtkezesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SlideshowFragment extends Fragment implements EtkezesAdapter.ClickListener {
    RecyclerView recyclerView;
    EtkezesAdapter usersAdapter;
    EtkezesViewModel userViewModel;
    EtelViewModel etelViewModel;
    FloatingActionButton floatingActionButton;
    private FragmentSlideshowBinding binding;

    private long selectedTimestamp = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        userViewModel = new ViewModelProvider(this).get(EtkezesViewModel.class);

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        floatingActionButton = binding.btnNewUser;
        usersAdapter = new EtkezesAdapter(this);
        userViewModel.getAllEtkezes().observe(getViewLifecycleOwner(), new Observer<List<EtkezesOsszevont>>() {
            @Override
            public void onChanged(List<EtkezesOsszevont> etkezes) {
                if (etkezes.size() > 0) {
                    usersAdapter.setData(etkezes);
                    recyclerView.setAdapter(usersAdapter);
                }
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adduser();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void updateClickedEtkezes(Etkezes etkezes) {
        updateuser(etkezes);
    }

    @Override
    public void deleteClickedEtkezes(Etkezes etkezes) {
        userViewModel.deleteEtkezes(etkezes);
    }

    public void updateuser(Etkezes etkezes) {
         /*final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.row_add_etkezes, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        Button btnAddUser = view.findViewById(R.id.addUserBtr);
        EditText etelNev = view.findViewById(R.id.etelNev);
        etelNev.setText(etkezes.getEtkezesIdopontGramm());
        EditText etelKaloria = view.findViewById(R.id.etelKaloria);
        etelKaloria.setText(etkezes.getEtkezesIdopontIdo());
        TextView tvDetails = view.findViewById(R.id.tvDetails);
        tvDetails.setText("Szerkeszt");
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etelNev.getText() != null) {
                    String nev = etelNev.getText().toString().trim();
                    String kaloria = etelKaloria.getText().toString().trim();
                    etkezes.setEtkezesIdopontGramm(nev);
                    etkezes.setEtkezesIdopontIdo(kaloria);
                    userViewModel.updateUsers(etkezes);
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();*/
    }

    public void adduser() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.row_add_etkezes, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        Button btnAddUser = view.findViewById(R.id.addUserBtr);
        Spinner mySpinner = view.findViewById(R.id.etkezesIdopontEtelId);

        TextView etelgramm = view.findViewById(R.id.etkezesIdopontGramm);
        TextView ido = view.findViewById(R.id.etkezesIdopontIdo);

        ido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar selectedDate = Calendar.getInstance();
                                selectedDate.set(year, month, dayOfMonth);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                ido.setText(sdf.format(selectedDate.getTime()));
                                selectedTimestamp = selectedDate.getTimeInMillis();
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        etelViewModel = new ViewModelProvider(this).get(EtelViewModel.class);
        etelViewModel.getAllEtel().observe(getViewLifecycleOwner(), new Observer<List<Etel>>() {
            @Override
            public void onChanged(List<Etel> etel) {
                if (etel.size() > 0) {
                    ArrayAdapter<Etel> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, etel);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySpinner.setAdapter(adapter);
                }
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Etel selectedEtel = (Etel) mySpinner.getSelectedItem();
                if (selectedEtel != null && etelgramm.getText() != null) {
                    String gramm = etelgramm.getText().toString().trim();

                    Etkezes etkezes = new Etkezes();
                    etkezes.setEtkezesIdopontGramm(gramm);
                    etkezes.setEtkezesIdopontIdo(selectedTimestamp);
                    etkezes.setEtkezesIdopontEtelId(selectedEtel.getEtelid());

                    userViewModel.insertEtkezes(etkezes);
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }
}
