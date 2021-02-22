package com.example.shoppinglist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAct extends AppCompatActivity {
    private Button button;

    android.widget.EditText mEmail, mPassword;
    android.widget.Button mLoginBtn;
    com.google.firebase.auth.FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.edEmail);
        mPassword = findViewById(R.id.edPassword);
        mLoginBtn = (Button) findViewById(R.id.btnFirst);

        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null){
            startActivity(new Intent(LoginAct.this,MainActivity.class));
            finish();
        }
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                //authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginAct.this, "Logged in Successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginAct.this,MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginAct.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void bcd(View view) {
        startActivity(new Intent(this,RegisterAct.class));
        finish();
    }
}
