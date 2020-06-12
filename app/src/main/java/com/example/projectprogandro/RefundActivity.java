package com.example.projectprogandro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.internal.DataCollectionConfigStorage;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RefundActivity extends AppCompatActivity implements RefundAdapter.DeleteListener {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference penumpangRef = db.collection("Penumpang");
    private RefundAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        setUpRecylerView();

        Button buttonRefund = (Button) findViewById(R.id.refundButton);

        /*buttonRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(RefundActivity.this)
                        .setTitle("Apakah anda yakin akan refund")
                        .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Permintaan anda akan kami proses",
                                        Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            }
        });\
        */

    }

    private void setUpRecylerView(){
        Query query = penumpangRef.orderBy("Tanggal",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<InfoPenumpang> options = new FirestoreRecyclerOptions.Builder<InfoPenumpang>()
                .setQuery(query, InfoPenumpang.class)
                .build();

        adapter = new RefundAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.refundList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void deleteClick(int position) {
        //Toast.makeText(getApplicationContext(),"permintaan anda akan kami proses", Toast.LENGTH_LONG).show();
    }
}