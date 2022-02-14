package com.example.learn_english.Activity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.learn_english.Adapter.VocabularyAdapter;
import com.example.learn_english.Object.Vocabulary;
import com.example.learn_english.R;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class SearchResultActivity extends AppCompatActivity {

    String TAG = "my_" + getClass().getSimpleName();

    ListView lvSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        lvSearchResult = findViewById(R.id.lv_search_result);

        List<Vocabulary> listVocabulary = (List<Vocabulary>) getIntent().getSerializableExtra("search_result");
        VocabularyAdapter vocabularyAdapter = new VocabularyAdapter(SearchResultActivity.this, R.layout.item_vocabulary, listVocabulary, "english");
        lvSearchResult.setAdapter(vocabularyAdapter);
    }
}