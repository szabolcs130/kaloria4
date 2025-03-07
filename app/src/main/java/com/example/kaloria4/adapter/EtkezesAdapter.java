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

import java.util.List;

public class EtkezesAdapter extends RecyclerView.Adapter<EtkezesAdapter.EtkezesAdapterVH> {
    private List<Etkezes> etkezesList;
    private Context context;
    private ClickListener clickListener;
    public EtkezesAdapter(ClickListener clickListener) {
        this.clickListener=clickListener;
    }
    public void setData(List<Etkezes> etkezesList){
        this.etkezesList=etkezesList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public EtkezesAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        return new EtkezesAdapterVH(
                LayoutInflater.from(context).inflate(R.layout.row_items,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull EtkezesAdapterVH holder, int position) {
        Etkezes etkezes=etkezesList.get(position);
        String username= etkezes.getEtkezesIdopontGramm();
        String kaloria= etkezes.getEtkezesIdopontIdo();
        holder.etelNev.setText(username);
        holder.etelKaloria.setText(kaloria);
        holder.imageOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPpUp(view,etkezes);
            }
        });
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
        ImageView imageOptions;
        TextView etelNev,etelKaloria;
        public EtkezesAdapterVH(@NonNull View itemView) {
            super(itemView);
            imageOptions=itemView.findViewById(R.id.imageOptions);
            etelNev=itemView.findViewById(R.id.etelNev);
            etelKaloria=itemView.findViewById(R.id.etelKaloria);

        }
    }
}
