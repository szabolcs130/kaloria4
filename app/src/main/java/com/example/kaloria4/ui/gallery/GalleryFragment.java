package com.example.kaloria4.ui.gallery;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

        EditText searchEditText = binding.searchEtel;
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String query = charSequence.toString();
                etelViewModel.searchEtel(query).observe(getViewLifecycleOwner(), new Observer<List<Etel>>() {
                    @Override
                    public void onChanged(List<Etel> etels) {
                        usersAdapter.setData(etels);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        floatingActionButton.setOnClickListener(v -> adduser());
        pickMedia = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    if (uri != null && selectedImageView != null) {
                        selectedImageUri=uri.toString();
                        Glide.with(getContext())
                                .load(uri)
                                .placeholder(R.drawable.ic_menu_gallery)
                                .error(R.drawable.baseline_more_vert_24)
                                .into(selectedImageView);
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

        selectedImageView = view.findViewById(R.id.imagePreview);
        Button btnAddUser = view.findViewById(R.id.addUserBtr);
        EditText etelNev = view.findViewById(R.id.etelNev);
        EditText etelKaloria = view.findViewById(R.id.etelKaloria);
        TextView tvDetails = view.findViewById(R.id.tvDetails);
        Button btnPickImage = view.findViewById(R.id.selectImageButton);
        TextView etelNevError = view.findViewById(R.id.error_name);
        TextView kaloriaError = view.findViewById(R.id.error_kaloria);
        etelNev.setText(etel.getEtelnev());
        etelKaloria.setText(etel.getKaloria());
        String cimSzoveg = etel.getEtelnev() + " étel adatainak módosítása";
        tvDetails.setText(cimSzoveg);
        Glide.with(getContext())
                .load(etel.getImageUri())
                .placeholder(R.drawable.ic_menu_gallery)
                .error(R.drawable.baseline_more_vert_24)
                .into(selectedImageView);
        btnAddUser.setText("Frissít");

        btnPickImage.setOnClickListener(v -> {
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        btnAddUser.setOnClickListener(v -> {
            String updatedNev = etelNev.getText().toString().trim();
            String updatedKaloria = etelKaloria.getText().toString().trim();
            String updatedImageUrl = selectedImageUri;
            boolean hasError = false;
            if (updatedNev.isEmpty()) {
                etelNevError.setVisibility(View.VISIBLE);
                hasError = true;
            } else {etelNevError.setVisibility(View.GONE);}

            if(updatedKaloria.isEmpty())
            {
                kaloriaError.setVisibility(View.VISIBLE);
                hasError = true;
            }
            else{kaloriaError.setVisibility(View.GONE);}
            if (!hasError) {
                etel.setEtelnev(updatedNev);
                etel.setKaloria(updatedKaloria);
                etel.setImageUri(updatedImageUrl);

                etelViewModel.updateUsers(etel);
                alertDialog.dismiss();
            }

        });
        alertDialog.show();
    }

    public void adduser() {
        selectedImageUri = null;

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.row_add, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        selectedImageView = view.findViewById(R.id.imagePreview);

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
