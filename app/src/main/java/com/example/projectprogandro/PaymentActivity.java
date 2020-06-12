package com.example.projectprogandro;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = PaymentActivity.class.getSimpleName();
    TextView textAnak;
    TextView textDewasa;
    TextView textTanggal;
    TextView textAsal;
    TextView textTujuan;
    TextView textHarga;
    DatabaseReference databaseReference;
    DatabaseReference dtbs;

    String anak = "";
    String dewasa = "";
    String tanggal = "";
    String asal = "";
    String tujuan = "";
    String harga1 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView textAnak = (TextView)findViewById(R.id.textanak);
        TextView textDewasa = (TextView)findViewById(R.id.textdewasa);
        TextView textTanggal = (TextView)findViewById(R.id.texttanggal);
        TextView textAsal = (TextView)findViewById(R.id.textAsal);
        TextView textTujuan = (TextView)findViewById(R.id.texttujuan);
        TextView textHarga = (TextView) findViewById(R.id.txtHarga);
        Button bayar = (Button)findViewById(R.id.btnBayar);

        anak = getIntent().getStringExtra("anak");
        dewasa = getIntent().getStringExtra("dewasa");
        tanggal = getIntent().getStringExtra("tanggal");
        asal = getIntent().getStringExtra("asal");
        tujuan = getIntent().getStringExtra("tujuan");
        harga1 = getIntent().getStringExtra("Harga");

        textAnak.setText(anak);
        textDewasa.setText(dewasa);
        textTanggal.setText(tanggal);
        textAsal.setText(asal);
        textTujuan.setText(tujuan);
        textHarga.setText(harga1);


        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(PaymentActivity.this)
                        .setTitle("Pembayaran dilakukan dalam waktu 30 menit di atm terdekat")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Map<String, Object> penumpang = new HashMap<>();
                                penumpang.put("Asal",asal);
                                penumpang.put("Tujuan",tujuan);
                                penumpang.put("Tanggal",tanggal);
                                penumpang.put("Dewasa",dewasa);
                                penumpang.put("Anak",anak);
                                penumpang.put("Harga",harga1);
                        
                                //Add a new document with a generated ID
                                db.collection("Penumpang")
                                        .add(penumpang)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d(TAG, "DocumentSnapshot added with ID : " + documentReference.getId());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error adding document", e);
                                            }
                                        });
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }
                        })
                        .create();
                dialog.show();
            }
        });

    }
}


