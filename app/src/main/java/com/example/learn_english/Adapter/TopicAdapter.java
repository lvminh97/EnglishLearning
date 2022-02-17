package com.example.learn_english.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.learn_english.Object.Topic;
import com.example.learn_english.R;

import java.util.Base64;
import java.util.List;

public class TopicAdapter extends ArrayAdapter<Topic> {
    Context context;
    int resource;
    List<Topic> listTopic;

    public TopicAdapter(@NonNull Context context, int resource, @NonNull List<Topic> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.listTopic = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_topic, parent, false);
        ImageView imgTopic = convertView.findViewById(R.id.img_topic);
        TextView txtTopic = convertView.findViewById(R.id.txt_topic);

        Topic topic = listTopic.get(position);

        byte[] decodedString = Base64.getDecoder().decode(topic.getTopicImage());
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgTopic.setImageBitmap(decodedByte);
        txtTopic.setText(topic.getTopicName());

        return convertView;
    }
}
