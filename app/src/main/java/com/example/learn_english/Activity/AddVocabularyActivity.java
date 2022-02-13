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

import com.example.learn_english.Fragment.ChineseFragment;
import com.example.learn_english.Fragment.EnglishFragment;
import com.example.learn_english.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddVocabularyActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText vocabularyEd, meanEd;
    private ImageView vocabularyImg;
    private Button addBtn;
    private String topicID;
    private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vocabulary);
        initUI();
        topicID = getIntent().getStringExtra("topic_id");
        lang = getIntent().getStringExtra("lang");
    }

    private void initUI() {
        vocabularyEd = findViewById(R.id.ed_vocabulary);
        meanEd = findViewById(R.id.ed_mean);
        vocabularyImg = findViewById(R.id.img_vocabulary);
        vocabularyImg.setOnClickListener(this);
        addBtn = findViewById(R.id.btn_add_topic);
        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_add_topic){
            addTopic();
        }
        else if(v.getId() == R.id.img_vocabulary){
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
                    vocabularyImg.setImageURI(uri);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addTopic(){
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("vocabularies").document(topicID).collection("words").document();
        HashMap<String, String> map = new HashMap<>();
        vocabularyImg.buildDrawingCache();
        Bitmap bitmap = vocabularyImg.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image = stream.toByteArray();
        map.put("image", Base64.getEncoder().encodeToString(image));
        map.put("vocabulary", vocabularyEd.getText().toString());
        map.put("mean", meanEd.getText().toString());
        docRef.set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(getBaseContext(), VocabularyActivity.class);
                intent.putExtra("topic_id", topicID);
                startActivity(intent);
            }
        });
    }
}
