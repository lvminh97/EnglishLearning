package com.example.learn_english.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.learn_english.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText emailEd, passwordEd, fullnameEd;
    private Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initUI();
    }

    private void initUI(){
        emailEd = findViewById(R.id.ed_email);
        passwordEd = findViewById(R.id.ed_password);
        fullnameEd = findViewById(R.id.ed_fullname);
        signupBtn = findViewById(R.id.btn_signup);
        signupBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_signup){
            signup();
        }
    }

    private void signup() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null)
            mAuth.signOut();

        mAuth.createUserWithEmailAndPassword(emailEd.getText().toString(), passwordEd.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                            HashMap<String, String> myMap = new HashMap<>();
                            HashMap<String, String> myMap2 = new HashMap<>();
                            myMap.put("fullname", fullnameEd.getText().toString());
                            firestore.collection("account").document(user.getUid()).set(myMap);
                            firestore.collection("topics").document(user.getUid()).set(myMap2);
                            Toast.makeText(getBaseContext(), "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
