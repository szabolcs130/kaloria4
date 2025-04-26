package com.example.kaloria4.ui.gallery;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
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

public class GalleryFragment extends Fragment implements UsersAdapter.ClickListener {
    RecyclerView recyclerView;
    UsersAdapter usersAdapter;
    EtelViewModel etelViewModel;
    FloatingActionButton floatingActionButton;
    private FragmentGalleryBinding binding;
    private ImageView selectedImageView;

    private String selectedImageUri;
    ActivityResultLauncher<PickVisualMediaRequest> pickMedia;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        etelViewModel = new ViewModelProvider(this).get(EtelViewModel.class);
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        floatingActionButton = binding.btnNewUser;
        usersAdapter = new UsersAdapter(this);

        etelViewModel.getAllEtel().observe(getViewLifecycleOwner(), new Observer<List<Etel>>() {
            @Override
            public void onChanged(List<Etel> lista) {
                usersAdapter.setData(lista);
                recyclerView.setAdapter(usersAdapter);
            }
        });

        floatingActionButton.setOnClickListener(v -> adduser());
        pickMedia = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    if (uri != null) {
                        selectedImageUri = uri.toString();
                        Log.d("gallery uri", selectedImageUri);

                        if (selectedImageView != null) {
                            selectedImageView.setImageURI(uri);
                            selectedImageView.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );

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
        etelViewModel.deleteUsers(etel);
    }

    public void updateuser(Etel etel) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.row_add, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        selectedImageView = view.findViewById(R.id.imageView);

        Button btnAddUser = view.findViewById(R.id.addUserBtr);
        EditText etelNev = view.findViewById(R.id.etelNev);
        EditText etelKaloria = view.findViewById(R.id.etelKaloria);
        TextView tvDetails = view.findViewById(R.id.tvDetails);
        Button btnPickImage = view.findViewById(R.id.selectImageButton);
        ImageView imageView = view.findViewById(R.id.imageView);
        etelNev.setText(etel.getEtelnev());
        etelKaloria.setText(etel.getKaloria());
        tvDetails.setText("Szerkesztés");

        if (etel.getImageUri() != null) {
            Uri uri = Uri.parse(etel.getImageUri());
            imageView.setImageURI(uri);
            imageView.setVisibility(View.VISIBLE);
        }

        btnPickImage.setOnClickListener(v -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        btnAddUser.setOnClickListener(v -> {
            String nev = etelNev.getText().toString().trim();
            String kaloria = etelKaloria.getText().toString().trim();
            etel.setEtelnev(nev);
            etel.setKaloria(kaloria);
            if (selectedImageUri != null) {
                etel.setImageUri(selectedImageUri);
            }
            etelViewModel.updateUsers(etel);
            alertDialog.dismiss();
        });

        alertDialog.show();
    }

    public void adduser() {
        selectedImageUri = null;

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.row_add, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        selectedImageView = view.findViewById(R.id.imageView);

        Button btnAddUser = view.findViewById(R.id.addUserBtr);
        EditText etelNev = view.findViewById(R.id.etelNev);
        EditText etelKaloria = view.findViewById(R.id.etelKaloria);
        Button btnFromGallery = view.findViewById(R.id.selectImageButton);
        TextView etelNevError = view.findViewById(R.id.error_name);
        TextView etelKaloriaError = view.findViewById(R.id.error_kaloria);

        btnFromGallery.setOnClickListener(v -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        btnAddUser.setOnClickListener(v -> {
            btnAddUser.setText("Frissít");
            String etelNevText = etelNev.getText().toString().trim();
            String etelKaloriaText = etelKaloria.getText().toString().trim();
            String imageUrl = selectedImageUri;

            boolean hasError = false;
            if (etelNevText.isEmpty()) {
                etelNevError.setVisibility(View.VISIBLE);
                hasError = true;
            } else {
                etelNevError.setVisibility(View.GONE);
            }
            if (etelKaloriaText.isEmpty()) {
                etelKaloriaError.setVisibility(View.VISIBLE);
                hasError = true;
            } else {
                etelKaloriaError.setVisibility(View.GONE);
            }


            if (!hasError) {
                Etel etel = new Etel();
                etel.setEtelnev(etelNevText);
                etel.setKaloria(etelKaloriaText);
                etel.setImageUri(imageUrl);
                etelViewModel.insertUser(etel);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}