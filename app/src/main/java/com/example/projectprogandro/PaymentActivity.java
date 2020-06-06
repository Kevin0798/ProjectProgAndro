package com.example.projectprogandro;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class PaymentActivity extends AppCompatActivity {

    TextView testShow;
    //Long date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        //final Long date = calendarView.getDate();
        testShow = findViewById(R.id.apaini);
       // testShow.setText(Math.toIntExact(date);
        Intent IncomingIntent = getIntent();
        String date = IncomingIntent.getStringExtra("date");
        testShow.setText(date);
    }
}