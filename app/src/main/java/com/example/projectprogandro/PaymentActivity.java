package com.example.projectprogandro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class PaymentActivity extends AppCompatActivity {

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

        databaseReference = FirebaseDatabase.getInstance().getReference("UserPayment");

        textAnak.setText(anak);
        textDewasa.setText(dewasa);
        textTanggal.setText(tanggal);
        textAsal.setText(asal);
        textTujuan.setText(tujuan);
        textHarga.setText(harga1);


        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(PaymentActivity.this)
                        .setTitle("Pembayaran dilakukan dalam waktu 30 menit di atm terdekat")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseReference.push().setValue(anak);
                                databaseReference.push().setValue(dewasa);
                                databaseReference.push().setValue(asal);
                                databaseReference.push().setValue(tujuan);
                                databaseReference.push().setValue(harga1);
                                databaseReference.push().setValue(tanggal);
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }
                        })
                        .create();
                dialog.show();
            }
        });

    }
}