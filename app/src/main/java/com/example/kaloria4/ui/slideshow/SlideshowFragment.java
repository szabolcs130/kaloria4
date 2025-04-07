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
import android.widget.Toast;

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
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        userViewModel = new ViewModelProvider(this).get(EtkezesViewModel.class);

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        if (usersAdapter == null) {
            usersAdapter = new EtkezesAdapter(this);
            recyclerView.setAdapter(usersAdapter);
        }
        userViewModel.getAllEtkezes().observe(getViewLifecycleOwner(), new Observer<List<EtkezesOsszevont>>() {
            @Override
            public void onChanged(List<EtkezesOsszevont> etkezes) {
                if (etkezes != null && !etkezes.isEmpty()) {
                    usersAdapter.setData(etkezes);
                } else {
                    usersAdapter.setData(null);
                }
            }
        });
        floatingActionButton = binding.btnNewUser;
        floatingActionButton.setOnClickListener(v -> adduser());
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

    public void updateuser(Etkezes etkezes) {
    }

    public void adduser() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.row_add_etkezes, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        Button btnAddUser = view.findViewById(R.id.addUserBtr);
        Spinner mealSpinner = view.findViewById(R.id.etkezesIdopontEtelId);
        Spinner typeSpinner = view.findViewById(R.id.etkezesIdopontTipus);
        EditText grammEditText = view.findViewById(R.id.etkezesIdopontGramm);
        TextView idoTextView = view.findViewById(R.id.etkezesIdopontIdo);

        idoTextView.setOnClickListener(new View.OnClickListener() {
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
                                idoTextView.setText(sdf.format(selectedDate.getTime()));
                                selectedTimestamp = selectedDate.getTimeInMillis();
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        etelViewModel = new ViewModelProvider(this).get(EtelViewModel.class);
        etelViewModel.getAllEtel().observe(getViewLifecycleOwner(), new Observer<List<Etel>>() {
            @Override
            public void onChanged(List<Etel> etelList) {
                if (etelList != null && !etelList.isEmpty()) {
                    ArrayAdapter<Etel> mealAdapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_spinner_item, etelList);
                    mealAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mealSpinner.setAdapter(mealAdapter);
                } else {
                    Toast.makeText(getContext(), "Nincs elérhető étel!", Toast.LENGTH_SHORT).show();
                    mealSpinner.setAdapter(null);
                }
            }
        });

        String[] mealTypes = getResources().getStringArray(R.array.meal_types);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, mealTypes);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Etel selectedEtel = (Etel) mealSpinner.getSelectedItem();
                String grammText = grammEditText.getText().toString().trim();
                int gramm = 0;
                if (!grammText.isEmpty()) {
                    gramm = Integer.parseInt(grammText);
                }

                if (selectedEtel != null) {
                    Etkezes etkezes = new Etkezes();
                    etkezes.setEtkezesIdopontGramm(gramm);
                    etkezes.setEtkezesIdopontIdo(selectedTimestamp);
                    etkezes.setEtkezesIdopontEtelId(selectedEtel.getEtelid());
                    String selectedMealType = (String) typeSpinner.getSelectedItem();
                    etkezes.setEtkezesTipus(selectedMealType);
                    userViewModel.insertEtkezes(etkezes);
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }
}
