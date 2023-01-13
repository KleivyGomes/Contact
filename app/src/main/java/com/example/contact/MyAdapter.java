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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    //Attributes
    Context context;
    ArrayList<MyListData> myListData;
    ArrayList<String> menuContext;

    //constructor
    public MyAdapter(Context context, ArrayList<MyListData> myListData, ArrayList<String> menu) {
        this.context = context;
        this.myListData = myListData;
        this.menuContext = menu;
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
            holder.favorite.setImageResource(R.drawable.favorito);
        }

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                changeFavoriteStatus(holder);
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

    public void changeFavoriteStatus(@NonNull MyAdapter.ViewHolder holder) {
        if (myListData.get(holder.getAdapterPosition()).getFavorito()) {
            myListData.get(holder.getAdapterPosition()).setFavorito(false);
            holder.favorite.setImageResource(R.drawable.ic_baseline_star_outline_24);

        } else {
            myListData.get(holder.getAdapterPosition()).setFavorito(true);
            holder.favorite.setImageResource(R.drawable.favorito);
        }
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
            for (int i = 0; i < menuContext.size(); i++) {
                contextMenu.add(this.getAdapterPosition(), 122 + i, i, menuContext.get(i));
            }
        }

    }

    //call the contact
    public Intent callContact(int position) {
        Uri uri = Uri.parse("tel:" + myListData.get(position).getPhone());
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

    public Intent sendContact(int position) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Name: " + myListData.get(position).getName() + "\n" +
                        "Email: " + myListData.get(position).getEmail() + "\n" +
                        "Phone: " + myListData.get(position).getPhone() + "\n" +
                        "Sex: " + myListData.get(position).getSex() + "\n" +
                        "Birthday: " + myListData.get(position).getDay() + "/" +
                        myListData.get(position).getMonth() + "/" +
                        myListData.get(position).getYear());
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, "CONTACT");
        return shareIntent;
    }

    //show a message
    public void displayToast(String message) {
        Toast.makeText(context.getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }
}
