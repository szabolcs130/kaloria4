package com.example.kaloria4.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kaloria4.R;
import com.example.kaloria4.model.Etkezes;
import com.example.kaloria4.model.EtkezesOsszevont;

import java.text.SimpleDateFormat;
import java.util.List;

public class EtkezesAdapter extends RecyclerView.Adapter<EtkezesAdapter.EtkezesAdapterVH> {
    private List<EtkezesOsszevont> etkezesList;
    private List<EtkezesOsszevont> etkezesListWithoutDate;
    private Context context;
    private ClickListener clickListener;
    private boolean reszletes;
    public EtkezesAdapter(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
    public EtkezesAdapter(ClickListener clickListener,boolean reszletes) {
        this.clickListener = clickListener;
        this.reszletes=reszletes;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<EtkezesOsszevont> etkezesList) {
        this.etkezesList = etkezesList;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataWithoutDate(List<EtkezesOsszevont> etkezesListWithoutDate) {
        this.etkezesListWithoutDate = etkezesListWithoutDate;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EtkezesAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new EtkezesAdapterVH(
                LayoutInflater.from(context).inflate(R.layout.row_items_etkezes, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull EtkezesAdapterVH holder, int position) {
        EtkezesOsszevont etkezes = etkezesListWithoutDate != null ? etkezesListWithoutDate.get(position) : etkezesList.get(position);
        int gramm = etkezes.getEtkezesIdopontGramm();
        long idopont = etkezes.getEtkezesIdopontIdo();
        String etelNev = etkezes.getEtkezesIdopontEtelNev();
        int kaloria = etkezes.getKaloria();
        String etkezesTipus = etkezes.getEtkezesTipus();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(idopont);

        int osszKaloria = (kaloria * gramm) / 100;

        holder.etelNev.setText(etelNev);
        holder.etelKaloria.setText(kaloria + " kcal / 100g");
        holder.etkezesIdopontGramm.setText(gramm + " g");
        if (etkezesListWithoutDate == null) {
            holder.etkezesIdopontIdo.setText(formattedDate);
        } else {
            holder.etkezesIdopontIdo.setVisibility(View.GONE);
        }
        holder.etkezesIdopontOsszesKaloria.setText("Összes: " + osszKaloria + " kcal");
        holder.etkezesTipusSzoveg.setText(etkezesTipus != null ? etkezesTipus : "Ismeretlen");

        holder.imageOptions.setOnClickListener(v -> {
            showPpUp(v, etkezes);
        });
    }

    public void showPpUp(View view, EtkezesOsszevont etkezes) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu);
        popupMenu.setGravity(Gravity.END);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.itUpdate) {
                clickListener.updateClickedEtkezes(etkezes);
                return true;
            } else if (id == R.id.itDelete) {
                clickListener.deleteClickedEtkezes(etkezes);
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    public interface ClickListener {
        void updateClickedEtkezes(EtkezesOsszevont etkezes);
        void deleteClickedEtkezes(EtkezesOsszevont etkezes);

        void updateEtkezes(EtkezesOsszevont etkezes);
    }

    @Override
    public int getItemCount() {
        return etkezesListWithoutDate != null ? etkezesListWithoutDate.size() : (etkezesList == null ? 0 : etkezesList.size());
    }

    public class EtkezesAdapterVH extends RecyclerView.ViewHolder {
        TextView etkezesIdopontIdo, etelNev, etelKaloria, etkezesIdopontGramm, etkezesIdopontOsszesKaloria, etkezesTipusSzoveg;
        ImageView imageOptions;
        public EtkezesAdapterVH(@NonNull View itemView) {
            super(itemView);
            etelNev = itemView.findViewById(R.id.etelNev);
            etelKaloria = itemView.findViewById(R.id.etelKaloria);
            etkezesIdopontGramm = itemView.findViewById(R.id.etkezesIdopontGramm);
            etkezesIdopontIdo = itemView.findViewById(R.id.etkezesIdopontIdo);
            etkezesIdopontOsszesKaloria = itemView.findViewById(R.id.etkezesIdopontOsszesKaloria);
            etkezesTipusSzoveg = itemView.findViewById(R.id.etkezesIdopontTipusSzoveg);
            imageOptions = itemView.findViewById(R.id.imageOptions);
            if (reszletes==true){
                imageOptions.setVisibility(View.GONE);
                //reszletes=false;
            }
        }
    }
}