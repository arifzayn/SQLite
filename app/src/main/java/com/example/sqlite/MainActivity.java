package com.example.sqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextInputEditText etId, etFirstName, etLastName, etEmail;

    Button btnCreate, btnRead, btnUpdate, btnDelete;

    ListView lvData;
    ArrayList<String> data = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    DB_Institute instituteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etId = findViewById(R.id.etId);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);

        btnCreate = findViewById(R.id.btnCreate);
        btnRead = findViewById(R.id.btnRead);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        lvData = findViewById(R.id.lvData);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        lvData.setAdapter(arrayAdapter);

        instituteDB = new DB_Institute(MainActivity.this);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = etFirstName.getText().toString();
                String lName = etLastName.getText().toString();
                String email = etEmail.getText().toString();

                try {
                    instituteDB.open();
                    instituteDB.newStudent(fName, lName, email);
//                    Toast.makeText(MainActivity.this, "Record Create", Toast.LENGTH_SHORT).show();
                    Snackbar.make(v, "Record Created", Snackbar.LENGTH_SHORT).show();

                    etFirstName.setText("");
                    etLastName.setText("");
                    etEmail.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    instituteDB.close();
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllRecords();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etId.getText().toString());
                String fName = etFirstName.getText().toString();
                String lName = etLastName.getText().toString();
                String email = etEmail.getText().toString();

                try {
                    instituteDB.open();
                    instituteDB.updateRecord(id, fName, lName, email);
//                    Toast.makeText(MainActivity.this, "Record Updated", Toast.LENGTH_SHORT).show();
                    Snackbar.make(v, "Record Updated", Snackbar.LENGTH_SHORT).show();

                    etFirstName.setText("");
                    etLastName.setText("");
                    etEmail.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    instituteDB.close();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(etId.getText().toString());

                try {
                    instituteDB.open();
                    instituteDB.deleteRecord(id);
//                    Toast.makeText(MainActivity.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                    Snackbar.make(v, "Record Deleted", Snackbar.LENGTH_SHORT).show();

                    etFirstName.setText("");
                    etLastName.setText("");
                    etEmail.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    instituteDB.close();
                }
            }
        });
    }

    private void showAllRecords() {
        arrayAdapter.clear();
        try {
            instituteDB.open();
            arrayAdapter.add(instituteDB.getAllRecords());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            instituteDB.close();
        }
    }
}