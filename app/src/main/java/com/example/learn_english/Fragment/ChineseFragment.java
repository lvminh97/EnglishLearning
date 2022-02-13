package com.example.learn_english.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.learn_english.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ChineseFragment extends Fragment {

    private Context context;
    private View view;
    private GridView grvTopic;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_chinese, container, false);
        initUI();
        return view;
    }

    private void initUI() {
        getActivity().setTitle("Chinese Learning");

        grvTopic = view.findViewById(R.id.grv_topic);

//        Model model = new Model(getContext());
//        if (!model.isCopyDB()) {
//            model.copyDB();
//        }

//        final List<Topic> listTopic = model.getListTopic("chinese");
//        TopicAdapter topicAdapter = new TopicAdapter(getContext(), R.layout.item_topic, listTopic);
//        grvTopic.setAdapter(topicAdapter);

//        grvTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getContext(), VocabularyActivity.class);
//                intent.putExtra("topic_id", listTopic.get(position).getTopicID());
//                intent.putExtra("language", "chinese");
//                startActivity(intent);
//            }
//        });
    }

}
