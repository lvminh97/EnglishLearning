package com.example.learn_english.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.learn_english.Object.Vocabulary;
import com.example.learn_english.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class VocabularyAdapter extends ArrayAdapter<Vocabulary> {
    Context context;
    int resource;
    List<Vocabulary> listVocabulary;

    public VocabularyAdapter(@NonNull Context context, int resource, @NonNull List<Vocabulary> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.listVocabulary = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_vocabulary, parent, false);

        ImageView imgVocabulary = convertView.findViewById(R.id.img_vocabulary);
        TextView txtEnglish = convertView.findViewById(R.id.txt_english);
        TextView txtVietnamese = convertView.findViewById(R.id.txt_vietnamese);
        ImageView btnListen = convertView.findViewById(R.id.btn_listen);

        final Vocabulary vocabulary = listVocabulary.get(position);
        try {
            InputStream inputStream = context.getAssets().open("image/" + vocabulary.getVocabularyImage() + ".jpg");
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imgVocabulary.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        txtEnglish.setText(vocabulary.getEnglish());
        txtVietnamese.setText(vocabulary.getVietnamese());

        btnListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(context,
                        context.getResources().getIdentifier(vocabulary.getSound(), "raw", context.getPackageName()));
                mediaPlayer.start();
            }
        });

        return convertView;
    }
}
