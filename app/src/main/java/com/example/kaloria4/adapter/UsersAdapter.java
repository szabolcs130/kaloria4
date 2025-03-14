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

import org.w3c.dom.Text;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersAdapterVH> {
    private List<Etel> etelList;
    private Context context;
private ClickListener clickListener;
    public UsersAdapter(ClickListener clickListener) {
    this.clickListener=clickListener;
    }
public void setData(List<Etel> usersList){
        this.etelList=usersList;
        notifyDataSetChanged();
}
    @NonNull
    @Override
    public UsersAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       context=parent.getContext();
        return new UsersAdapterVH(
                LayoutInflater.from(context).inflate(R.layout.row_items,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapterVH holder, int position) {
        Etel etel=etelList.get(position);
        String username= etel.getEtelnev();
        String kaloria= etel.getKaloria();
        String etelid= (""+etel.getEtelid());
        holder.etelNev.setText(username);
        holder.etelKaloria.setText(kaloria);
        holder.etelid.setText(etelid);
        holder.imageOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showPpUp(view,etel);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Nyomkod", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void showPpUp(View view,Etel etel){
        PopupMenu popupMenu=new PopupMenu(context,view);
        popupMenu.inflate(R.menu.menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
               int id=menuItem.getItemId();
              if(id==R.id.itUpdate) {
                  clickListener.updateClicked(etel);
              }else if (id == R.id.itDelete){
                      clickListener.deleteClicked(etel);
              }
                return false;
            }
        });
        popupMenu.show();
    }
public interface ClickListener{
        void updateClicked(Etel etel);
        void deleteClicked(Etel etel);

}
    @Override
    public int getItemCount() {
        return etelList.size();
    }
    public class UsersAdapterVH extends RecyclerView.ViewHolder
    {
        ImageView imageOptions;
        TextView etelNev,etelKaloria,etelid;
        public UsersAdapterVH(@NonNull View itemView) {
            super(itemView);
            imageOptions=itemView.findViewById(R.id.imageOptions);
            etelNev=itemView.findViewById(R.id.etelNev);
            etelKaloria=itemView.findViewById(R.id.etelKaloria);
            etelid=itemView.findViewById(R.id.etelId);

        }
    }
}
