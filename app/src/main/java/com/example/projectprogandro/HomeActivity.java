package com.example.projectprogandro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class HomeActivity extends AppCompatActivity {


    Button btnLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        btnLogout = findViewById(R.id.out);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Anda yakin ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                //  session.logoutUser();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            }
        });
    }


    public void bookKereta(View v) {
        Intent i = new Intent(this, BookKeretaActivity.class);
        startActivity(i);
    }

}
