package com.example.learn_english.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.learn_english.Adapter.TopicAdapter;
import com.example.learn_english.Adapter.VocabularyAdapter;
import com.example.learn_english.Database.Model;
import com.example.learn_english.Object.Topic;
import com.example.learn_english.Object.Vocabulary;
import com.example.learn_english.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VocabularyActivity extends AppCompatActivity implements View.OnClickListener{

    String TAG = "my_VocabularyActivity";

    ListView lvVocabulary;
    FloatingActionButton examFab, addVocabularyFab;
    String topicID;
    String lang;

    FirebaseAuth mAuth = null;
    FirebaseFirestore db = null;
    List<Vocabulary> listVocabulary = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        topicID  = getIntent().getStringExtra("topic_id");
        lang = getIntent().getStringExtra("lang");

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        initUI();
    }

    private void initUI(){
        lvVocabulary = findViewById(R.id.lv_vocabulary);
        examFab = findViewById(R.id.fab_exam);
        addVocabularyFab = findViewById(R.id.fab_add_vocabulary);

        getVocabularyList();

        examFab.setOnClickListener(this);
        addVocabularyFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab_exam){
            Intent intent = new Intent(VocabularyActivity.this, ExamActivity.class);
            intent.putExtra("topic_id", topicID);
            intent.putExtra("lang", lang);
            startActivity(intent);
        }
        else if(v.getId() == R.id.fab_add_vocabulary){
            Intent intent = new Intent(getBaseContext(), AddVocabularyActivity.class);
            intent.putExtra("lang", "english");
            intent.putExtra("topic_id", topicID);
            startActivity(intent);
        }
    }

    private void getVocabularyList(){
        listVocabulary = new ArrayList<Vocabulary>();
        CollectionReference colRef = db.collection("vocabularies").document(topicID).collection("words");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Log.d("MyApp", document.getId() + "");
                        listVocabulary.add(new Vocabulary(document.getId(),
                                                            topicID,
                                                            document.get("image").toString(),
                                                            document.get("vocabulary").toString(),
                                                            document.get("mean").toString()));
                    }
                }

                VocabularyAdapter vocabularyAdapter = new VocabularyAdapter(getBaseContext(), R.layout.item_vocabulary, listVocabulary);
                lvVocabulary.setAdapter(vocabularyAdapter);
            }
        });
    }
}