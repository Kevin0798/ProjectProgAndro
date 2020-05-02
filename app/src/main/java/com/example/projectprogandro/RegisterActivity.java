package com.example.projectprogandro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import static android.text.Html.fromHtml;

public class RegisterActivity extends AppCompatActivity {
    private EditText regisEmail;
    private EditText regisPassword;
    private Button btnRegister;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        regisEmail = findViewById(R.id.textEmailReg);
        regisPassword = findViewById(R.id.textPasswordReg);
        btnRegister = findViewById(R.id.btnRegis);
        loginNow();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regisMail = regisEmail.getText().toString().trim();
                String regisPass = regisPassword.getText().toString().trim();

                if (regisMail.isEmpty() && regisPass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Email dan Password kosong", Toast.LENGTH_SHORT).show();
                }
                if (!regisMail.isEmpty() && regisPass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Password dibutuhkan", Toast.LENGTH_SHORT).show();
                }
                if (regisMail.isEmpty() && !regisPass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Email dibutuhkan", Toast.LENGTH_SHORT).show();
                }
                if (!regisMail.isEmpty() && !regisPass.isEmpty()){
                    firebaseAuth.createUserWithEmailAndPassword(regisMail, regisPass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "Error "+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void loginNow(){
        TextView textViewCreateAccount = findViewById(R.id.textHadAccount);
        textViewCreateAccount.setText(fromHtml("<font color='#000000'>Already have Account ?. </font><font color='#0c0099'>Login now</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
