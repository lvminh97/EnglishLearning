package com.example.learn_english.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.learn_english.Activity.AddTopicActivity;
import com.example.learn_english.Activity.EditTopicActivity;
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

import java.util.ArrayList;

public class ChineseFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener{

    private Context context;
    private View view;
    private GridView grvTopic;
    private FloatingActionButton addTopicFab;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_english, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        initUI();
        return view;
    }

    private void initUI() {
        getActivity().setTitle("Chinese Learning");
        grvTopic = view.findViewById(R.id.grv_topic);
        addTopicFab = view.findViewById(R.id.fab_add_topic);
        getTopicList();
        grvTopic.setOnItemClickListener(this);
        grvTopic.setOnItemLongClickListener(this);
        addTopicFab.setOnClickListener(this);
    }

    private void getTopicList(){
        Model.listTopic = new ArrayList<Topic>();
        CollectionReference colRef = db.collection("topics").document(mAuth.getCurrentUser().getUid()).collection("chinese");
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Model.listTopic.add(new Topic(document.getId().toString(),
                                document.get("name").toString(),
                                document.get("image").toString()));
                    }
                }

                TopicAdapter topicAdapter = new TopicAdapter(getContext(), R.layout.item_topic, Model.listTopic);
                grvTopic.setAdapter(topicAdapter);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab_add_topic){
            Intent intent = new Intent(getActivity().getBaseContext(), AddTopicActivity.class);
            intent.putExtra("lang", "chinese");
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), VocabularyActivity.class);
        intent.putExtra("topic_id", Model.listTopic.get(position).getTopicID());
        intent.putExtra("lang", "chinese");
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), EditTopicActivity.class);
        intent.putExtra("lang", "chinese");
        intent.putExtra("topic_position", position);
        startActivity(intent);
        return true;
    }
}
