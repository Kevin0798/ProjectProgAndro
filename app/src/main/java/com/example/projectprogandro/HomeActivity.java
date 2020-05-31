package com.example.projectprogandro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class HomeActivity extends AppCompatActivity {

    TextView usrName;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    Button btnLogout;
    String UserId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        usrName = findViewById(R.id.userName);
        firebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        UserId = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("users").document(UserId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                usrName.setText(documentSnapshot.getString("Nama"));
            }
        });

        btnLogout = findViewById(R.id.out);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Anda yakin ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                finish();
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

    public void profileMenu(View v) {
        //Toast.makeText(HomeActivity.this,"Dalam Tahap Pengembangan",Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void refund(View v) {
        Toast.makeText(HomeActivity.this,"Dalam Tahap Pengembangan",Toast.LENGTH_LONG).show();
    }

    public void paymentMenu(View v) {
        Toast.makeText(HomeActivity.this,"Dalam Tahap Pengembangan",Toast.LENGTH_LONG).show();
    }

}
