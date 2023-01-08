package com.example.contact;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    //Attributes
    Context context;
    ArrayList<MyListData> myListData;

    //constructor
    public MyAdapter(Context context, ArrayList<MyListData> myListData) {
        this.context = context;
        this.myListData = myListData;
    }

    //inflate a single_item
    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(view);
    }

    //Setting the single_item views
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.name.setText(myListData.get(position).getName());
        holder.phone.setText(myListData.get(position).getPhone());
        holder.imageView.setImageResource(R.drawable.perfil);
        holder.email.setText(myListData.get(position).getEmail());
        if (myListData.get(position).getFavorito()) {
            holder.favorito.setImageResource(R.drawable.favorito);
        }
        holder.favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myListData.get(holder.getAdapterPosition()).getFavorito()) {
                    myListData.get(holder.getAdapterPosition()).setFavorito(false);
                    holder.favorito.setImageResource(R.drawable.ic_baseline_star_outline_24);
                } else {
                    myListData.get(holder.getAdapterPosition()).setFavorito(true);
                    holder.favorito.setImageResource(R.drawable.favorito);
                }
            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayToast("UPDATE");
                Intent intent = new Intent(context.getApplicationContext(), UpDateContact.class);
                intent.putExtra("position", String.valueOf(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    //DB size
    @Override
    public int getItemCount() {
        return myListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView name, phone, email;
        ImageView imageView, favorito;
        ConstraintLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //single item Views
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.contato);
            email = itemView.findViewById(R.id.email);
            imageView = itemView.findViewById(R.id.imageView);
            favorito = itemView.findViewById(R.id.favorito);
            item = itemView.findViewById(R.id.item);
            item.setOnCreateContextMenuListener(this);
        }

        //Create a menu item
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(this.getAdapterPosition(), 122, 0, "CALL");
            contextMenu.add(this.getAdapterPosition(), 123, 1, "SMS");
            contextMenu.add(this.getAdapterPosition(), 124, 2, "FAVORITE");
            contextMenu.add(this.getAdapterPosition(), 125, 3, "REMOVE");
            contextMenu.add(this.getAdapterPosition(), 126, 4, "SHARE");
        }

    }

    //remove a item from DB
    public void removeItem(int position) {
        myListData.remove(position);

    }

    //call the contact
    public Intent callContact(int position) {
        Uri uri = Uri.parse("tel:" + myListData.get(position).getPhone().toString());
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        return intent;
    }

    //show a message
    public void displayToast(String message) {
        Toast.makeText(context.getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }
}
