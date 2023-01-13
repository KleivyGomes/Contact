package com.example.contact;

import android.annotation.SuppressLint;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    //Attributes
    Context context;
    ArrayList<MyListData> myListData;
    ArrayList<String> menuContext;

    //constructor
    public FavoriteAdapter(Context context, ArrayList<MyListData> myListData,ArrayList<String> menu) {
        this.context = context;
        this.myListData = myListData;
        this.menuContext = menu;
    }

    //inflate a single_item
    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);

        return new ViewHolder(view);
    }

    //Setting the single_item views
    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        holder.name.setText(myListData.get(position).getName());
        holder.phone.setText(myListData.get(position).getPhone());
        holder.imageView.setImageResource(R.drawable.perfil);
        holder.email.setText(myListData.get(position).getEmail());
        if (myListData.get(position).getFavorito()) {
            holder.favorite.setImageResource(R.drawable.favorito);
        }

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                for (int n=0;n<DB.contactList.size();n++){
                    if(myListData.get(holder.getAdapterPosition()).getEmail()==DB.contactList.get(n).getEmail()){
                        DB.contactList.get(n).setFavorito(false);
                    };
                }
                myListData.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
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
        ImageView imageView, favorite;
        ConstraintLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //single item Views
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.contato);
            email = itemView.findViewById(R.id.email);
            imageView = itemView.findViewById(R.id.imageView);
            favorite = itemView.findViewById(R.id.favorito);
            item = itemView.findViewById(R.id.item);
            item.setOnCreateContextMenuListener(this);
        }



        //Create a menu item
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            for(int i=0;i<menuContext.size();i++){
                contextMenu.add(this.getAdapterPosition(), 122+i, i,menuContext.get(i));
            }
        }

    }

    //call the contact
    public Intent callContact(int position) {
        Uri uri = Uri.parse("tel:" + myListData.get(position).getPhone().toString());
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        return intent;
    }

    public Intent sendMessage(int position){
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", myListData.get(position).getPhone());
        smsIntent.putExtra("sms_body","");
        return smsIntent;
    }

    //show a message
    public void displayToast(String message) {
        Toast.makeText(context.getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }
}
