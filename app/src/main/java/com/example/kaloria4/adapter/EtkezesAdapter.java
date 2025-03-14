package com.example.kaloria4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kaloria4.R;
import com.example.kaloria4.model.Etel;
import com.example.kaloria4.model.Etkezes;
import com.example.kaloria4.model.EtkezesOsszevont;

import java.text.SimpleDateFormat;
import java.util.List;

public class EtkezesAdapter extends RecyclerView.Adapter<EtkezesAdapter.EtkezesAdapterVH> {
    private List<EtkezesOsszevont> etkezesList;
    private Context context;
    private ClickListener clickListener;
    public EtkezesAdapter(ClickListener clickListener) {
        this.clickListener=clickListener;
    }
    public void setData(List<EtkezesOsszevont> etkezesList){
        this.etkezesList=etkezesList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public EtkezesAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new EtkezesAdapterVH(
                LayoutInflater.from(context).inflate(R.layout.row_items_etkezes,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull EtkezesAdapterVH holder, int position) {
        EtkezesOsszevont etkezes=etkezesList.get(position);
        int gramm= etkezes.getEtkezesIdopontGramm();
        long idopont= etkezes.getEtkezesIdopontIdo();
        //String nev= etkezes.getete();
       // holder.etelNev.setText(nev);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(idopont);
        holder.etkezesIdopontIdo.setText(String.valueOf(formattedDate));
        //holder.etelKaloria.setText(kaloria);
        holder.etkezesIdopontGramm.setText(String.valueOf(gramm));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Nyomkod", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void showPpUp(View view,Etkezes etkezes){
        PopupMenu popupMenu=new PopupMenu(context,view);
        popupMenu.inflate(R.menu.menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.itUpdate) {
                    clickListener.updateClickedEtkezes(etkezes);
                }else if (id == R.id.itDelete){
                    clickListener.deleteClickedEtkezes(etkezes);
                }
                return false;
            }
        });
        popupMenu.show();
    }
    public interface ClickListener{
        void updateClickedEtkezes(Etkezes etkezes);
        void deleteClickedEtkezes(Etkezes etkezes);

    }
    @Override
    public int getItemCount() {
        return etkezesList.size();
    }
    public class EtkezesAdapterVH extends RecyclerView.ViewHolder
    {
        TextView etkezesIdopontIdo,etelNev,etelKaloria,etkezesIdopontGramm,etkezesIdopontOsszesKaloria;
        public EtkezesAdapterVH(@NonNull View itemView) {
            super(itemView);
            //etelNev=itemView.findViewById(R.id.etelNev);
            //etelKaloria=itemView.findViewById(R.id.etelKaloria);
            etkezesIdopontGramm=itemView.findViewById(R.id.etkezesIdopontGramm);
            etkezesIdopontIdo=itemView.findViewById(R.id.etkezesIdopontIdo);

        }
    }
}
