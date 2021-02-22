package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterAct extends AppCompatActivity {
    private Button button;

    android.widget.EditText mFullName, mEmail, mPassword, mPhone;
    android.widget.Button mRegitsterBtn;
    com.google.firebase.auth.FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.edName);
        mEmail = findViewById(R.id.edEmail);
        mPassword = findViewById(R.id.edPassword);
        mPhone = findViewById(R.id.edPhone);
        mRegitsterBtn = (Button) findViewById(R.id.btnFirst);

        fAuth = FirebaseAuth.getInstance();


        mRegitsterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required for the Registration.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Please enter you Password.");
                    return;
                }

                if(password.length() < 6) {
                    mPassword.setError("Password must be at least 6 characters.");
                    return;
                }

                // Register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterAct.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterAct.this,MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterAct.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void abc(View view) {
        startActivity(new Intent(this,LoginAct.class));
        finish();
    }
}