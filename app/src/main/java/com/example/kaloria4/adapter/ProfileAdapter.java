package com.example.kaloria4.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kaloria4.R;
import com.example.kaloria4.model.Profile;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileAdapterVH> {
    private List<Profile> profileList;
    private Context context;
    private ClickListener clickListener;

    public ProfileAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setData(List<Profile> profileList) {
        this.profileList = profileList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProfileAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ProfileAdapterVH(
                LayoutInflater.from(context).inflate(R.layout.fragment_profil, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapterVH holder, int position) {
        Profile profile = profileList.get(position);
        String username = profile.getNev();
        String magassag = String.valueOf(profile.getMagassagCm());
        String suly = String.valueOf(profile.getSulyKg());
        String cel = String.valueOf(profile.getCel());
        String imageUrl = profile.getImageUrl();
        String celKaloria = String.valueOf(profile.getCelKaloria());

        holder.profilNev.setText(username);
        holder.profilMagassag.setText("Magasság: " + magassag + " cm");
        holder.profilSuly.setText("Súly: " + suly + " kg");
        holder.profilCel.setText("Cél: " + cel + " kg");
        holder.profilCelKaloria.setText("Célkalória: "+celKaloria+" g");
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_menu_gallery)
                .error(R.drawable.baseline_more_vert_24)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Profil kiválasztva: " + profile.getNev(), Toast.LENGTH_SHORT).show();
        });
    }


    public interface ClickListener {
        void updateClicked(Profile profile);
        void deleteClicked(Profile profile);
    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public class ProfileAdapterVH extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView profilNev, profilMagassag, profilSuly, profilCel, profilCelKaloria;

        public ProfileAdapterVH(@NonNull View itemView) {
            super(itemView);
            profilNev = itemView.findViewById(R.id.profil_nev);
            profilMagassag = itemView.findViewById(R.id.profil_cm);
            profilSuly = itemView.findViewById(R.id.profil_kg);
            profilCel = itemView.findViewById(R.id.profil_cel);
            profilCelKaloria = itemView.findViewById(R.id.profil_celKaloria);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
