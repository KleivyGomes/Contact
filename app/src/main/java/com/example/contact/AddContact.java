package com.example.contact;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {
    //attributes
    private EditText name, phone, email;
    private DatePicker datePicker;
    private Button btn_add;
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
        name = findViewById(R.id.addName);
        phone = findViewById(R.id.addPhone);
        email = findViewById(R.id.addEmail);
        btn_add = findViewById(R.id.btn_add);
        datePicker = findViewById(R.id.datePicker1);
        masculine = findViewById(R.id.masculine);
        feminine = findViewById(R.id.feminine);
        others = findViewById(R.id.others);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        builder = new AlertDialog.Builder(this);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("ALERT").setMessage("Do you want o create this contact!")
                        .setCancelable(true).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //verify empty view
                                if (name.getText().toString().isEmpty() || phone.getText().toString().isEmpty() ||
                                        email.getText().toString().isEmpty() || masculine.isChecked() == false
                                        && feminine.isChecked() == false && others.isChecked() == false) {
                                    displayToast("Empty value verified, search and try again!");
                                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                                    displayToast("Verify or email and try again!");
                                } else {
                                    //verify sex
                                    if (masculine.isChecked()) {
                                        sex = "masculine";
                                    } else if (feminine.isChecked()) {
                                        sex = "feminine";
                                    } else {
                                        sex = "others";
                                    }
                                    MyListData contact = new MyListData(name.getText().toString(), email.getText().toString(),
                                            phone.getText().toString(), String.valueOf(datePicker.getDayOfMonth()),
                                            String.valueOf(datePicker.getMonth()),
                                            String.valueOf(datePicker.getYear()), sex);
                                    DB.contactList.add(contact);
                                    displayToast("New contact create!");
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();

            }
        });

    }

    //Show a message
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }
}