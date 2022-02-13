package com.example.learn_english.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.learn_english.Database.Model;
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
import androidx.constraintlayout.widget.ConstraintLayout;

public class EditVocabularyActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText vocabularyEd, meanEd;
    private ImageView vocabularyImg;
    private Button updateBtn, deleteBtn, yesBtn, noBtn;
    private ConstraintLayout confirmLayout;
    private String lang, topicID, vocabularyId, vocabulary, vocabularyImage, mean;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vocabulary);
        lang = getIntent().getStringExtra("lang");
        int position = getIntent().getIntExtra("position", -1);
        topicID = Model.listVocabulary.get(position).getTopicID();
        vocabularyId = Model.listVocabulary.get(position).getVocabularyID();
        vocabulary = Model.listVocabulary.get(position).getVocabulary();
        vocabularyImage = Model.listVocabulary.get(position).getVocabularyImage();
        mean = Model.listVocabulary.get(position).getMean();
        initUI();
    }

    private void initUI() {
        vocabularyEd = findViewById(R.id.ed_vocabulary);
        vocabularyEd.setText(vocabulary);
        meanEd = findViewById(R.id.ed_mean);
        meanEd.setText(mean);

        updateBtn = findViewById(R.id.btn_update_vocabulary);
        deleteBtn = findViewById(R.id.btn_delete_vocabulary);
        yesBtn = findViewById(R.id.btn_confirm_yes);
        noBtn = findViewById(R.id.btn_confirm_no);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        yesBtn.setOnClickListener(this);
        noBtn.setOnClickListener(this);

        vocabularyImg = findViewById(R.id.img_vocabulary);
        byte[] decodedString = Base64.getDecoder().decode(vocabularyImage);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        vocabularyImg.setImageBitmap(decodedByte);
        vocabularyImg.setOnClickListener(this);

        confirmLayout = findViewById(R.id.layout_confirm_delete);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_update_vocabulary){
            updateVocabulary();
        }
        else if(v.getId() == R.id.btn_delete_vocabulary){
            confirmLayout.setVisibility(View.VISIBLE);
        }
        else if(v.getId() == R.id.btn_confirm_yes){
            deleteVocabulary();
        }
        else if(v.getId() == R.id.btn_confirm_no){
            confirmLayout.setVisibility(View.INVISIBLE);
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

    private void updateVocabulary(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("vocabularies").document(topicID).collection("words").document(vocabularyId.toString());
        HashMap<String, String> map = new HashMap<>();
        vocabularyImg.buildDrawingCache();
        Bitmap bitmap = vocabularyImg.getDrawingCache();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image = stream.toByteArray();
        map.put("vocabulary", vocabularyEd.getText().toString());
        map.put("image", Base64.getEncoder().encodeToString(image));
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

    private void deleteVocabulary() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("vocabularies").document(topicID).collection("words").document(vocabularyId);
        docRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Intent intent = new Intent(getBaseContext(), VocabularyActivity.class);
                    intent.putExtra("topic_id", topicID);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), VocabularyActivity.class);
        intent.putExtra("topic_id", topicID);
        startActivity(intent);
    }
}
