package com.example.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    database g ;
    EditText et1, et2, et3;
    Button b1, b2, b3, b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et1 = findViewById(R.id.name);
        et2 = findViewById(R.id.username);
        et3 = findViewById(R.id.password);
        b1 = findViewById(R.id.insert);
        b2 = findViewById(R.id.delete);
        b3= findViewById(R.id.view);
        b4 = findViewById(R.id.update);

         g = new database (this);
       //   g.getReadableDatabase();


        // insert
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = et1.getText().toString();
                String username1 = et2.getText().toString();
                String password1 = et3.getText().toString();

                if (name1.equals("") || username1.equals("") || password1.equals("")) {
                    Toast.makeText(MainActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean i = g.insert_data(name1, username1, password1);
                    if (i == true) {
                        Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                et1.setText("");
                et2.setText("");
                et3.setText("");

            }
        });


         // delete
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et2.getText().toString();
                Boolean i = g.delete_data(username);

                if (i == true) {
                    Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    et1.setText("");
                    et2.setText("");
                    et3.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Not deleted", Toast.LENGTH_SHORT).show();
                    et1.setText("");
                    et2.setText("");
                    et3.setText("");
                }
            }
        });


        // view
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor t = g.getinfo();
                if (t.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (t.moveToNext()) {
                    buffer.append("Name:- " + t.getString(1) + "\n");
                    buffer.append("Username:- " + t.getString(2) + "\n");
                    buffer.append("Password:- " + t.getString(3) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("SignUp Users Data");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });


        // update
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et1.getText().toString();
                String username = et2.getText().toString();
                String password = et3.getText().toString();

                Boolean i = g.update_data(name, username, password);
                if (i == true) {
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Not Successful", Toast.LENGTH_SHORT).show();
                }
                et1.setText("");
                et2.setText("");
                et3.setText("");
            }
        });


    }
}