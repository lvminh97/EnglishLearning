package com.example.learn_english.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ListView;

import com.example.learn_english.Adapter.VocabularyAdapter;
import com.example.learn_english.Database.Model;
import com.example.learn_english.Object.Vocabulary;
import com.example.learn_english.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VocabularyActivity extends AppCompatActivity {

    String TAG = "my_VocabularyActivity";

    ListView lvVocabulary;
    FloatingActionButton fabExam;
    int topicID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        topicID  = getIntent().getIntExtra("topic_id", 0);

        lvVocabulary = findViewById(R.id.lv_vocabulary);
        fabExam = findViewById(R.id.fab_exam);

        Model model = new Model(VocabularyActivity.this);
        final List<Vocabulary> listVocabulary = model.getListVocabulary(topicID);
        VocabularyAdapter vocabularyAdapter = new VocabularyAdapter(VocabularyActivity.this, R.layout.item_vocabulary, listVocabulary);
        lvVocabulary.setAdapter(vocabularyAdapter);


        fabExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VocabularyActivity.this, ExamActivity.class);
                intent.putExtra("topic_id", topicID);
                startActivity(intent);
            }
        });
    }
}