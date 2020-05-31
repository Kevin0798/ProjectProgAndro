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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.text.Html.fromHtml;

public class RegisterActivity extends AppCompatActivity {
    private EditText regisEmail;
    private EditText regisPassword;
    private EditText regisNama;
    private Button btnRegister;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore fstore;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        regisEmail = findViewById(R.id.textEmailReg);
        regisPassword = findViewById(R.id.textPasswordReg);
        regisNama = findViewById(R.id.namaUser);
        btnRegister = findViewById(R.id.btnRegis);
        fstore = FirebaseFirestore.getInstance();


        loginNow();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String regisMail = regisEmail.getText().toString().trim();
                String regisPass = regisPassword.getText().toString().trim();
                final String regisNma = regisNama.getText().toString().trim();

                if (regisMail.isEmpty() && regisPass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Email dan Password kosong", Toast.LENGTH_SHORT).show();
                }
                if (!regisMail.isEmpty() && regisPass.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Password dibutuhkan", Toast.LENGTH_SHORT).show();
                }
                if (regisMail.isEmpty() && !regisPass.isEmpty() && !regisNma.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Nama dibutuhkan", Toast.LENGTH_SHORT).show();
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
                                        userID = firebaseAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = fstore.collection("users").document(userID);
                                        Map<String,Object> user = new HashMap<>();
                                        user.put("Nama", regisNma);
                                        user.put("Email", regisMail);
                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("TAG", "onSuccess: user telah dibuat " + userID);
                                            }
                                        });
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
        textViewCreateAccount.setText(fromHtml("<font color='#000000'>Already have Account? </font><font color='#0c0099'>Login now</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
