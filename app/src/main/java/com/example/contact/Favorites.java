package com.example.contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {
    ArrayList<MyListData> favoriteList;

    //Attribute
    AlertDialog.Builder builder;
    FavoriteAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ArrayList<String> menuBar = new ArrayList<>();

        menuBar.add("CALL");
        menuBar.add("SMS");
        menuBar.add("REMOVE");



        myAdapter = new FavoriteAdapter(this, loadFavorites(),menuBar);


        //Recycler View configuration
        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);


    }

    //inflate de menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);

        return true;
    }

    //Verify the selected menu item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_favorite:
                displayToast("favorites");
                return true;
            case R.id.go_settings:
                displayToast("settings");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //verify the menu contact item
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 122:
                displayToast("CALLING ....");
                Intent call = myAdapter.callContact(item.getGroupId());
                startActivity(call);
                return true;
            case 123:
                Intent message = myAdapter.sendMessage(item.getGroupId());
                startActivity(message);
                displayToast("SENDING SMS ....");
                return true;
            case 124:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("alert").setMessage("Do you want o delete this contact!")
                        .setCancelable(true).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                MyListData myListData=  favoriteList.get(item.getGroupId());
                                for(int j = 0;j<DB.contactList.size();j++){
                                    MyListData contact = DB.contactList.get(j);
                                    if(myListData.getEmail() == contact.getEmail()){
                                        DB.contactList.remove(j);
                                    }
                                }
                                favoriteList.remove(item.getGroupId());
                                displayToast("CONTACT REMOVED" + myListData.getEmail());
                                myAdapter.notifyDataSetChanged();
;

                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //show a message
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();

    }

    private ArrayList<MyListData> loadFavorites(){
        //create a favorite list
        favoriteList = new ArrayList<>();
        for (int i = 0; i < DB.contactList.size(); i++) {
            if (DB.contactList.get(i).getFavorito()) {
                favoriteList.add(DB.contactList.get(i));
            }
        }
        return favoriteList;
    }
}