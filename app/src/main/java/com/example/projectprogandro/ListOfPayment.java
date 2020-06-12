package com.example.projectprogandro;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ListOfPayment extends AppCompatActivity {

     private FirebaseFirestore db = FirebaseFirestore.getInstance();
     private CollectionReference penumpangRef = db.collection("Penumpang");

     private PaymentAdapter adapter;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_list_of_payment);
         setTitle("DAFTAR TRANSAKSI");
         setUpRecylerView();
     }

     private void setUpRecylerView(){
         Query query = penumpangRef.orderBy("Tanggal",Query.Direction.DESCENDING);

         FirestoreRecyclerOptions<InfoPenumpang> options = new FirestoreRecyclerOptions.Builder<InfoPenumpang>()
             .setQuery(query, InfoPenumpang.class)
             .build();

         adapter = new PaymentAdapter(options);
         RecyclerView recyclerView = findViewById(R.id.firestore_list);
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


}

