package com.example.learn_english.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learn_english.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText emailEd, passwordEd;
    private Button loginBtn, signupBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        mAuth = FirebaseAuth.getInstance();
    }

    private void initUI(){
        loginBtn = findViewById(R.id.btn_login);
        signupBtn = findViewById(R.id.btn_signup);
        emailEd = findViewById(R.id.ed_email);
        passwordEd = findViewById(R.id.ed_password);

        SharedPreferences pref = getSharedPreferences("LANG_AUTH", MODE_PRIVATE);
        String username = pref.getString("username", null);
        String password = pref.getString("password", null);
        if(username != null && password != null){
            emailEd.setText(username);
            passwordEd.setText(password);
        }
        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_login){
            login();
        }
        else if(view.getId() == R.id.btn_signup){
            if(mAuth.getCurrentUser() == null) {
                Intent intent = new Intent(this, SignupActivity.class);
                startActivity(intent);
            }
        }
    }

    private void login(){
        mAuth.signInWithEmailAndPassword(emailEd.getText().toString(), passwordEd.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            SharedPreferences pref = getSharedPreferences("LANG_AUTH", MODE_PRIVATE);
                            pref.edit().putString("username", emailEd.getText().toString()).commit();
                            pref.edit().putString("password", passwordEd.getText().toString()).commit();
                            Toast.makeText(getBaseContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
