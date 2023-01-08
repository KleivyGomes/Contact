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
    public static ArrayList<MyListData> favoriteList;
    //Attribute
    AlertDialog.Builder builder;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        //create a favorite list
        favoriteList = new ArrayList<>();
        for (int i = 0; i < DB.contactList.size(); i++) {
            if (DB.contactList.get(i).getFavorito()) {
                favoriteList.add(DB.contactList.get(i));
            }
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        myAdapter = new MyAdapter(this, favoriteList);

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
                displayToast("SENDING ....");
                return true;
            case 124:
                displayToast("REMOVED FROM FAVORITE");
                return true;
            case 125:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("alert").setMessage("Do you want o delete this contact!")
                        .setCancelable(true).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                displayToast("CONTACT REMOVED");
                                myAdapter.removeItem(item.getGroupId());
                                recreate();

                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();
                return true;
            case 126:
                displayToast("SHARING ....");
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
}