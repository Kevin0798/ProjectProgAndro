package com.example.projectprogandro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BookKeretaActivity extends AppCompatActivity {

    FirebaseFirestore fstore;
    String keId;
    protected Cursor cursor;
    FirebaseAuth fauth;
    SQLiteDatabase db;
    FirebaseDatabase firebaseDatabase;
    Spinner spinAsal, spinTujuan, spinDewasa, spinAnak;
    String email;

    public String sAsal, sTujuan, sTanggal, sDewasa, sAnak;

    private EditText etTanggal;
    private DatePickerDialog dpTanggal;
    Calendar newCalendar = Calendar.getInstance();
    private DocumentReference documentReference;
    DatabaseReference databaseReference;
    DatabaseReference dtbs;
    ValueEventListener valueEventListener;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> SpinnerDataList;

    ValueEventListener valueEventListener2;
    ArrayAdapter<String> arrayAdapter2;
    ArrayList<String> spinnerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_kereta);
        fstore = FirebaseFirestore.getInstance();

        spinAsal = findViewById(R.id.asal);
        spinTujuan = findViewById(R.id.tujuan);
        spinDewasa = findViewById(R.id.dewasa);
        spinAnak = findViewById(R.id.anak);

        databaseReference = FirebaseDatabase.getInstance().getReference("Destination");
        dtbs = FirebaseDatabase.getInstance().getReference("From");

        SpinnerDataList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(BookKeretaActivity.this, android.R.layout.simple_spinner_dropdown_item, SpinnerDataList);
        spinAsal.setAdapter(arrayAdapter);

        spinnerList = new ArrayList<>();
        arrayAdapter2 = new ArrayAdapter<String>(BookKeretaActivity.this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
        spinTujuan.setAdapter(arrayAdapter2);
        retrieveDataAsal();
        retrieveDataTujuan();










        spinAsal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sAsal = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinTujuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sTujuan = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinDewasa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sDewasa = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinAnak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sAnak = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btnBook = findViewById(R.id.book);
        etTanggal = findViewById(R.id.tanggal_berangkat);
        etTanggal.setInputType(InputType.TYPE_NULL);
        etTanggal.requestFocus();
        setDateTimeField();

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perhitunganHarga();
                if (sAsal != null && sTujuan != null && sTanggal != null && sDewasa != null && sAnak !=null ) {
                    if ((sAsal.equalsIgnoreCase("jakarta") && sTujuan.equalsIgnoreCase("jakarta"))
                            || (sAsal.equalsIgnoreCase("bandung") && sTujuan.equalsIgnoreCase("bandung"))
                            || (sAsal.equalsIgnoreCase("purwokerto") && sTujuan.equalsIgnoreCase("purwokerto"))
                            || (sAsal.equalsIgnoreCase("yogyakarta") && sTujuan.equalsIgnoreCase("yogyakarta"))
                            || (sAsal.equalsIgnoreCase("surabaya") && sTujuan.equalsIgnoreCase("surabaya"))) {
                        Toast.makeText(BookKeretaActivity.this, "Asal dan Tujuan tidak boleh sama !", Toast.LENGTH_LONG).show();
                    }
                    else
                        {
                        AlertDialog dialog = new AlertDialog.Builder(BookKeretaActivity.this)
                                .setTitle("Ingin booking kereta sekarang?")
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {

                                        } catch (Exception e) {
                                            Toast.makeText(BookKeretaActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .setNegativeButton("Tidak", null)
                                .create();
                        dialog.show();
                    }
                } else {
                    Toast.makeText(BookKeretaActivity.this, "Mohon lengkapi data pemesanan!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void retrieveDataAsal(){
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()){
                    SpinnerDataList.add(item.getValue().toString());

                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void retrieveDataTujuan(){
        valueEventListener2 = dtbs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item: dataSnapshot.getChildren()){
                    spinnerList.add(item.getValue().toString());

                }
                arrayAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void perhitunganHarga() {

    }

    private void setDateTimeField() {
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpTanggal.show();
            }
        });

        dpTanggal = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei",
                        "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
                sTanggal = dayOfMonth + " " + bulan[monthOfYear] + " " + year;
                etTanggal.setText(sTanggal);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
