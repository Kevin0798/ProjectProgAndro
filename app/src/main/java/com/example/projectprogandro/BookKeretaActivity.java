package com.example.projectprogandro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    String harga1;
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
    private static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    Intent intent;
    TextView showDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_kereta);
        setTitle("TIKET");

        final String[] dewasa = {"0", "1", "2", "3", "4", "5"};
        final String[] anak = {"0", "1", "2", "3", "4", "5"};

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

        ArrayAdapter<CharSequence> adapterDewasa = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, dewasa);
        adapterDewasa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDewasa.setAdapter(adapterDewasa);

        ArrayAdapter<CharSequence> adapterAnak = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, anak);
        adapterAnak.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAnak.setAdapter(adapterAnak);

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

        //final String harga = spinDewasa.getSelectedItem().toString();
        Button btnBook = findViewById(R.id.book);
        etTanggal = findViewById(R.id.tanggal_berangkat);
        etTanggal.setInputType(InputType.TYPE_NULL);
        etTanggal.requestFocus();
        setDateTimeField();


        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookKeretaActivity.this, PaymentActivity.class);
                Intent listPayment = new Intent(BookKeretaActivity.this, ListOfPayment.class);
                if (spinDewasa.getSelectedItem().toString().equals("1")) {
                    harga1 = "250.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }
                if (spinDewasa.getSelectedItem().toString().equals("2")) {
                    harga1 = "500.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }
                if (spinDewasa.getSelectedItem().toString().equals("3")) {
                    harga1 = "750.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }
                if (spinDewasa.getSelectedItem().toString().equals("4")) {
                    harga1 = "1.000.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }if(spinDewasa.getSelectedItem().toString().equals("5")){
                    harga1 = "1.250.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }if (spinDewasa.getSelectedItem().toString().equals("1") && spinAnak.getSelectedItem().toString().equals("1")){
                    harga1 = "350.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }if (spinDewasa.getSelectedItem().toString().equals("1") && spinAnak.getSelectedItem().toString().equals("2")){
                    harga1 = "450.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }if (spinDewasa.getSelectedItem().toString().equals("1") && spinAnak.getSelectedItem().toString().equals("3")){
                    harga1 = "550.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }if (spinDewasa.getSelectedItem().toString().equals("1") && spinAnak.getSelectedItem().toString().equals("4")){
                    harga1 = "650.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }if (spinDewasa.getSelectedItem().toString().equals("1") && spinAnak.getSelectedItem().toString().equals("5")){
                    harga1 = "750.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }if (spinDewasa.getSelectedItem().toString().equals("2") && spinAnak.getSelectedItem().toString().equals("1")){
                    harga1 = "600.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }if (spinDewasa.getSelectedItem().toString().equals("2") && spinAnak.getSelectedItem().toString().equals("2")){
                    harga1 = "700.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }if (spinDewasa.getSelectedItem().toString().equals("2") && spinAnak.getSelectedItem().toString().equals("3")){
                    harga1 = "800.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }if (spinDewasa.getSelectedItem().toString().equals("2") && spinAnak.getSelectedItem().toString().equals("4")){
                    harga1 = "900.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }if (spinDewasa.getSelectedItem().toString().equals("2") && spinAnak.getSelectedItem().toString().equals("5")){
                    harga1 = "1.000.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }if (spinDewasa.getSelectedItem().toString().equals("3") && spinAnak.getSelectedItem().toString().equals("1")){
                    harga1 = "850.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }
                if (spinDewasa.getSelectedItem().toString().equals("3") && spinAnak.getSelectedItem().toString().equals("2")){
                    harga1 = "950.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

                }if (spinDewasa.getSelectedItem().toString().equals("3") && spinAnak.getSelectedItem().toString().equals("3")){
                    harga1 = "1.050.000";
                    i.putExtra("Harga", harga1);
                    i.putExtra("asal", String.valueOf(spinAsal.getSelectedItem()));
                    i.putExtra("tujuan", String.valueOf(spinTujuan.getSelectedItem()));
                    i.putExtra("anak", String.valueOf(spinAnak.getSelectedItemId()));
                    i.putExtra("dewasa", String.valueOf(spinDewasa.getSelectedItem()));
                    i.putExtra("tanggal", sTanggal);
                    startActivity(i);

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
