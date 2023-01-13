package com.example.contact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //Attribute
    MyAdapter myAdapter;
    AlertDialog.Builder builder;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> menuBar = new ArrayList<>();

        menuBar.add("CALL");
        menuBar.add("SMS");
        menuBar.add("FAVORITE");
        menuBar.add("REMOVE");
        menuBar.add("SHARE");

        myAdapter = new MyAdapter(this, DB.contactList,menuBar);

        //FloatingActionButton View
        FloatingActionButton fab = findViewById(R.id.add_contact);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddContact.class);
                startActivity(intent);
            }
        });

        //Recycler view configuration
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);


    }


    //inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    //verify the item menu selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.go_favorite:
                Intent intent1 = new Intent(getApplicationContext(), Favorites.class);
                startActivity(intent1);
                return true;
            case R.id.go_settings:
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.settings");
                displayToast("settings");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Verify the contact item menu selected
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
                displayToast("ADDED TO FAVORITE");
                DB.contactList.get(item.getGroupId()).setFavorito(true);
                myAdapter.notifyDataSetChanged();
                return true;
            case 125:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("alert").setMessage("Do you want o delete this contact!")
                        .setCancelable(true).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                displayToast("CONTACT REMOVED" + item.getGroupId());

                                DB.contactList.remove(item.getGroupId());
                                myAdapter.notifyDataSetChanged();

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
                Intent shareIntent = myAdapter.sendContact(item.getGroupId());
                startActivity(shareIntent);
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