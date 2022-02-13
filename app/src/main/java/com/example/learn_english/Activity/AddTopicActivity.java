package com.example.learn_english.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.learn_english.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddTopicActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText topicNameEd;
    private ImageView topicImg;
    private Button addBtn;
    private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        initUI();
        lang = getIntent().getStringExtra("lang");
    }

    private void initUI() {
        topicNameEd = findViewById(R.id.ed_topic_name);
        topicImg = findViewById(R.id.img_topic);
        addBtn = findViewById(R.id.btn_add_topic);
        addBtn.setOnClickListener(this);
        topicImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_add_topic){
            addTopic();
        }
        else if(v.getId() == R.id.img_topic){
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            try {
                startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 101);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 101:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    topicImg.setImageURI(uri);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addTopic(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("topics").document(mAuth.getCurrentUser().getUid()).collection("english").document();
        HashMap<String, String> map = new HashMap<>();
        topicImg.buildDrawingCache();
        Bitmap bitmap = topicImg.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image = stream.toByteArray();
        map.put("image", Base64.getEncoder().encodeToString(image));
        map.put("name", topicNameEd.getText().toString());
        docRef.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("tab", lang);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("lang", lang);
        startActivity(intent);
    }
}
