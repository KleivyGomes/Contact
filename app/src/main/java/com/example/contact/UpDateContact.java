package com.example.contact;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class UpDateContact extends AppCompatActivity {
    private TextView title;
    private EditText name, phone, email;
    private DatePicker datePicker;
    private Button btn_update;
    private MyListData contact;
    private AlertDialog.Builder builder;
    private String sex;
    private RadioButton masculine;
    private RadioButton feminine;
    private RadioButton others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        //Views
        title = findViewById(R.id.novo_title);
        name = findViewById(R.id.addName);
        phone = findViewById(R.id.addPhone);
        email = findViewById(R.id.addEmail);
        btn_update = findViewById(R.id.btn_add);
        datePicker = findViewById(R.id.datePicker1);
        masculine = findViewById(R.id.masculine);
        feminine = findViewById(R.id.feminine);
        others = findViewById(R.id.others);

        //back button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Set Views
        title.setText("Update contact");
        btn_update.setText("UPDATE CONTACT");

        //get contact for update
        int position = Integer.parseInt(getIntent().getExtras().getString("position"));
        contact = DB.contactList.get(position);

        //setting de EditViews text
        name.setText(contact.getName());
        phone.setText(contact.getPhone());
        email.setText(contact.getEmail());
        if (contact.getSex().equals("masculine")) {
            masculine.setChecked(true);
        } else if (contact.getSex().equals("feminine")) {
            feminine.setChecked(true);
        } else {
            others.setChecked(true);
        }


        builder = new AlertDialog.Builder(this);

        //update the contact
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verify empty view
                if (name.getText().toString().isEmpty() || phone.getText().toString().isEmpty() ||
                        email.getText().toString().isEmpty() || masculine.isChecked() == false
                        && feminine.isChecked() == false && others.isChecked() == false) {
                    displayToast("Empty value verified, search and try again!");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    displayToast("Verify or email and try again!");
                } else {
                    builder.setTitle("ALERT").setMessage("Do you want o update this contact!")
                            .setCancelable(true).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (masculine.isChecked()) {
                                        sex = "masculine";
                                    } else if (feminine.isChecked()) {
                                        sex = "feminine";
                                    } else {
                                        sex = "others";
                                    }

                                    DB.contactList.get(position).setName(name.getText().toString());
                                    DB.contactList.get(position).setPhone(phone.getText().toString());
                                    DB.contactList.get(position).setEmail(email.getText().toString());
                                    DB.contactList.get(position).setDay(String.valueOf(datePicker.getDayOfMonth()));
                                    DB.contactList.get(position).setMonth(String.valueOf(datePicker.getMonth()));
                                    DB.contactList.get(position).setYear(String.valueOf(datePicker.getYear()));
                                    DB.contactList.get(position).setSex(sex);

                                    displayToast("CONTACT UPDATED");

                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            }
        });
    }

    //verify if de user want to cancel de update
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        builder = new AlertDialog.Builder(this);
        switch (id) {
            case android.R.id.home:
                builder.setTitle("ALERT").setMessage("Do you want to cancel update this contact!")
                        .setCancelable(true).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();

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
                return super.onOptionsItemSelected(item);
        }
    }

    //show a toast
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();

    }
}
