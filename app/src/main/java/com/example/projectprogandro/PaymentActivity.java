package com.example.projectprogandro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PaymentActivity extends AppCompatActivity {

    TextView textAnak;
    TextView textDewasa;
    TextView textTanggal;
    TextView textAsal;
    TextView textTujuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView textAnak = (TextView)findViewById(R.id.textanak);
        TextView textDewasa = (TextView)findViewById(R.id.textdewasa);
        TextView textTanggal = (TextView)findViewById(R.id.texttanggal);
        TextView textAsal = (TextView)findViewById(R.id.textAsal);
        TextView textTujuan = (TextView)findViewById(R.id.texttujuan);


        String anak = getIntent().getStringExtra("anak");
        String dewasa = getIntent().getStringExtra("dewasa");
        String tanggal = getIntent().getStringExtra("tanggal");
        String asal = getIntent().getStringExtra("asal");
        String tujuan = getIntent().getStringExtra("tujuan");


        textAnak.setText(anak);
        textDewasa.setText(dewasa);
        textTanggal.setText(tanggal);
        textAsal.setText(asal);
        textTujuan.setText(tujuan);

    }
}