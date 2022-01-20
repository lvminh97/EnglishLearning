package com.example.learn_english.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.learn_english.Adapter.TopicAdapter;
import com.example.learn_english.Database.Model;
import com.example.learn_english.Object.Topic;
import com.example.learn_english.Object.Vocabulary;
import com.example.learn_english.R;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String TAG = "my_MainActivity";
    GridView grvTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grvTopic = findViewById(R.id.grv_topic);

        Model model = new Model(MainActivity.this);
        if (!model.isCopyDB()) {
            model.copyDB();
        }

        final List<Topic> listTopic = model.getListTopic();
        TopicAdapter topicAdapter = new TopicAdapter(MainActivity.this, R.layout.item_topic, listTopic);
        grvTopic.setAdapter(topicAdapter);

        grvTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, VocabularyActivity.class);
                intent.putExtra("topic_id", listTopic.get(position).getTopicID());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.it_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: " + query);

                Model model = new Model(MainActivity.this);
                List<Vocabulary > listVocabulary = model.getListVocabularyByKeyword(query);

                Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                intent.putExtra("search_result", (Serializable) listVocabulary);
                startActivity(intent);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}