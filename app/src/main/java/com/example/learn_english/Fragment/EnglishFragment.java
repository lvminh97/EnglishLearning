package com.example.learn_english.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.learn_english.Activity.AddTopicActivity;
import com.example.learn_english.Activity.ExamActivity;
import com.example.learn_english.Activity.MainActivity;
import com.example.learn_english.Activity.VocabularyActivity;
import com.example.learn_english.Adapter.TopicAdapter;
import com.example.learn_english.Database.Model;
import com.example.learn_english.Object.Topic;
import com.example.learn_english.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class EnglishFragment extends Fragment {

    private Context context;
    private View view;
    private GridView grvTopic;
    private FloatingActionButton addTopicFab;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<Topic> listTopic = null;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_english, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        initUI();
        return view;
    }

    private void initUI() {
        getActivity().setTitle("English Learning");
        grvTopic = view.findViewById(R.id.grv_topic);
        addTopicFab = view.findViewById(R.id.fab_add_topic);
        getTopicList();
        grvTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), VocabularyActivity.class);
                intent.putExtra("topic_id", listTopic.get(position).getTopicID());
                intent.putExtra("language", "english");
                startActivity(intent);
            }
        });
        addTopicFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getBaseContext(), AddTopicActivity.class);
                intent.putExtra("lang", "english");
                startActivity(intent);
            }
        });
    }

    private void getTopicList(){
        listTopic = new ArrayList<Topic>();
        CollectionReference colRef = db.collection("topics").document(mAuth.getCurrentUser().getUid()).collection("english");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        listTopic.add(new Topic(document.getId().toString(),
                                document.get("name").toString(),
                                document.get("image").toString()));
                    }
                }

                TopicAdapter topicAdapter = new TopicAdapter(getContext(), R.layout.item_topic, listTopic);
                grvTopic.setAdapter(topicAdapter);
            }
        });
    }
}
