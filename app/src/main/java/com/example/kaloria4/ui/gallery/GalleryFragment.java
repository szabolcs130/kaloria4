package com.example.kaloria4.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kaloria4.R;
import com.example.kaloria4.adapter.UsersAdapter;
import com.example.kaloria4.databinding.FragmentGalleryBinding;
import com.example.kaloria4.model.Etel;
import com.example.kaloria4.viewmodel.EtelViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class GalleryFragment extends Fragment  implements UsersAdapter.ClickListener {
    RecyclerView recyclerView;
    UsersAdapter usersAdapter;
    EtelViewModel userViewModel;
    FloatingActionButton floatingActionButton;

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        userViewModel = new ViewModelProvider(this).get(EtelViewModel.class);
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        floatingActionButton = binding.btnNewUser;
        usersAdapter = new UsersAdapter(this);
        userViewModel.getAllEtel().observe(getViewLifecycleOwner(), new Observer<List<Etel>>() {
            @Override
            public void onChanged(List<Etel> etel) {
                if (etel.size() > 0) {
                    usersAdapter.setData(etel);
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
    public void updateClicked(Etel etel) {
        updateuser(etel);

    }

    @Override
    public void deleteClicked(Etel etel) {
        userViewModel.deleteUsers(etel);

    }
    public void updateuser(Etel etel) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.row_add, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        Button btnAddUser = view.findViewById(R.id.addUserBtr);
        EditText etelNev = view.findViewById(R.id.etelNev);
        etelNev.setText(etel.getEtelnev());
        EditText etelKaloria = view.findViewById(R.id.etelKaloria);
        etelKaloria.setText(etel.getKaloria());
        TextView tvDetails = view.findViewById(R.id.tvDetails);
        tvDetails.setText("Szerkeszt");
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etelNev.getText() != null) {
                    String nev = etelNev.getText().toString().trim();
                    String kaloria = etelKaloria.getText().toString().trim();
                    etel.setEtelnev(nev);
                    etel.setKaloria(kaloria);
                    userViewModel.updateUsers(etel);
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }
    public void adduser() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.row_add, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        Button btnAddUser = view.findViewById(R.id.addUserBtr);
        EditText etelNev = view.findViewById(R.id.etelNev);
        TextView etelKaloria = view.findViewById(R.id.etelKaloria);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etelNev.getText() != null && etelKaloria.getText() != null) {
                    String username = etelNev.getText().toString().trim();
                    Etel etel = new Etel();
                    etel.setEtelnev(username);
                    etel.setKaloria(etelKaloria.getText().toString().trim());
                   // Toast.makeText(MainActivity.this, ""+etel.getKaloria()+" "+etel.getEtelnev(), Toast.LENGTH_SHORT).show();
                    userViewModel.insertUser(etel);
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }
}