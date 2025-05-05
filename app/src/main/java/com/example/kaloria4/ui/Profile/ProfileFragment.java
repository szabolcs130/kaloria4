package com.example.kaloria4.ui.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.kaloria4.R;
import com.example.kaloria4.databinding.FragmentProfilBinding;
import com.example.kaloria4.model.Profile;
import com.example.kaloria4.viewmodel.ProfileViewModel;

import java.util.List;

public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private ImageView profileImageViewDisplay;
    private EditText profileNameDisplay;
    private EditText profileHeightDisplay;
    private EditText profileWeightDisplay;
    private EditText profileGoalDisplay;
    private EditText profileGoalCalories;
    private String selectedImageUri;
    private FragmentProfilBinding binding;
    private ActivityResultLauncher<PickVisualMediaRequest> pickMedia;
    private Profile currentProfile;
    private ImageView currentImageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileNameDisplay = root.findViewById(R.id.profil_nev);
        profileHeightDisplay = root.findViewById(R.id.profil_cm);
        profileWeightDisplay = root.findViewById(R.id.profil_kg);
        profileGoalDisplay = root.findViewById(R.id.profil_cel);
        profileImageViewDisplay = root.findViewById(R.id.imageView);
        profileGoalCalories = root.findViewById(R.id.profil_celKaloria);
        Button btnModify = root.findViewById(R.id.modifyButton);

        pickMedia = registerForActivityResult(
                new ActivityResultContracts.PickVisualMedia(),
                uri -> {
                    if (uri != null && currentImageView != null) {
                        selectedImageUri = uri.toString();
                        Glide.with(getContext())
                                .load(uri)
                                .placeholder(R.drawable.ic_menu_gallery)
                                .error(R.drawable.baseline_more_vert_24)
                                .into(currentImageView);
                    }
                }
        );

        profileViewModel.getProfile().observe(getViewLifecycleOwner(), new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
                if (!profiles.isEmpty()) {
                    currentProfile = profiles.get(0);
                    updateUI(currentProfile);
                }
                else {
                    btnModify.setText("Még nem állítottad be a fiókod? Kattints ide");
                }

            }
        });

        btnModify.setOnClickListener(v -> {
            showEditProfileDialog();
        });

        return root;
    }

    private void showEditProfileDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getLayoutInflater().inflate(R.layout.row_modify_profile, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        EditText profileNameEditText = view.findViewById(R.id.profil_nev);
        EditText profileHeightEditText = view.findViewById(R.id.profil_cm);
        EditText profileWeightEditText = view.findViewById(R.id.profil_kg);
        EditText profileGoalEditText = view.findViewById(R.id.profil_cel);
        EditText profileGoalCalories = view.findViewById(R.id.profil_celKaloria);
        ImageView editImageView = view.findViewById(R.id.imagePreview);
        Button btnPickImage = view.findViewById(R.id.selectImageButton);
        Button btnSave = view.findViewById(R.id.profil_mentes);

        if (currentProfile != null) {
            profileNameEditText.setText(currentProfile.getNev());
            profileHeightEditText.setText(String.valueOf(currentProfile.getMagassagCm()));
            profileWeightEditText.setText(String.valueOf(currentProfile.getSulyKg()));
            profileGoalEditText.setText(String.valueOf(currentProfile.getCel()));
            profileGoalCalories.setText(String.valueOf(currentProfile.getCelKaloria()));
            Glide.with(getContext())
                    .load(currentProfile.getImageUrl())
                    .placeholder(R.drawable.ic_menu_gallery)
                    .error(R.drawable.baseline_more_vert_24)
                    .into(editImageView);
            selectedImageUri = currentProfile.getImageUrl();
        }

        btnPickImage.setOnClickListener(v -> {
            currentImageView = editImageView;
            pickMedia.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });

        btnSave.setOnClickListener(v -> {
            String updatedName = profileNameEditText.getText().toString().trim();
            String updatedHeight = profileHeightEditText.getText().toString().trim();
            String updatedWeight = profileWeightEditText.getText().toString().trim();
            String updatedGoal = profileGoalEditText.getText().toString().trim();
            String updatedGoalCalories = profileGoalCalories.getText().toString().trim();
            String finalImageUrl = selectedImageUri;



                if (currentProfile != null) {
                    currentProfile.setNev(updatedName);
                    currentProfile.setMagassagCm(Integer.parseInt(updatedHeight));
                    currentProfile.setSulyKg(Integer.parseInt(updatedWeight));
                    currentProfile.setCel(Integer.parseInt(updatedGoal));
                    currentProfile.setImageUrl(finalImageUrl);
                    currentProfile.setCelKaloria(Integer.parseInt(updatedGoalCalories));
                    profileViewModel.updateUsers(currentProfile);
                } else {
                    Profile newProfile = new Profile();
                    newProfile.setNev(updatedName);
                    newProfile.setMagassagCm(Integer.parseInt(updatedHeight));
                    newProfile.setSulyKg(Integer.parseInt(updatedWeight));
                    newProfile.setCel(Integer.parseInt(updatedGoal));
                    newProfile.setImageUrl(finalImageUrl);
                    newProfile.setCelKaloria(Integer.parseInt(updatedGoalCalories));
                    profileViewModel.insertUser(newProfile);

                    Button btnModify = binding.modifyButton;
                    btnModify.setText("Módosítás");
                }
                alertDialog.dismiss();

        });

        alertDialog.show();
    }

    private void updateUI(Profile profile) {

        currentProfile = profile;
        profileNameDisplay.setText(profile.getNev());
        profileHeightDisplay.setText(String.valueOf(profile.getMagassagCm()));
        profileWeightDisplay.setText(String.valueOf(profile.getSulyKg()));
        profileGoalDisplay.setText(String.valueOf(profile.getCel()));
        profileGoalCalories.setText(String.valueOf(profile.getCelKaloria()));
        Glide.with(getContext())
                .load(profile.getImageUrl())
                .placeholder(R.drawable.ic_menu_gallery)
                .error(R.drawable.baseline_more_vert_24)
                .into(profileImageViewDisplay);
        selectedImageUri = profile.getImageUrl();
    }
}