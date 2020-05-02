package com.example.projectprogandro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import static android.text.Html.fromHtml;

public class MainActivity extends AppCompatActivity {
    private EditText EmailText;
    private EditText PasswrodText;
    private TextView signNow;
    private Button LoginBtn;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        EmailText = findViewById(R.id.textEmail);
        PasswrodText = findViewById(R.id.textPassword);
        signNow = findViewById(R.id.textCreateAcc);
        LoginBtn = findViewById(R.id.btnLogin);
        signInNow();
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = EmailText.getText().toString().trim();
                String password = PasswrodText.getText().toString().trim();

                if (mail.isEmpty() && !password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Email is Required", Toast.LENGTH_SHORT).show();
                }
                if (!mail.isEmpty() && password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
                }
                if (mail.isEmpty() && password.isEmpty()){
                    Toast.makeText(MainActivity.this, " Kolom tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                if (!mail.isEmpty() && !password.isEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(mail,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                    }else{
                                        Toast.makeText(MainActivity.this, "Error "+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    private void signInNow(){
        TextView textViewCreateAccount = findViewById(R.id.textCreateAcc);
        textViewCreateAccount.setText(fromHtml("<font color='#000000'>Not a member ?. </font><font color='#0c0099'>Sign Up</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
