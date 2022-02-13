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

import com.example.learn_english.Object.Vocabulary;
import com.example.learn_english.R;

import java.util.Base64;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

        byte[] decodedString = Base64.getDecoder().decode(vocabulary.getVocabularyImage());
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgVocabulary.setImageBitmap(decodedByte);
        txtEnglish.setText(vocabulary.getVocabulary());
        txtVietnamese.setText(vocabulary.getMean());

        btnListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String sound = vocabulary.getSound();
//                if(language.equals("chinese"))
//                    sound += "_";
//                MediaPlayer mediaPlayer = MediaPlayer.create(context,
//                        context.getResources().getIdentifier(sound, "raw", context.getPackageName()));
//                mediaPlayer.start();
            }
        });

        return convertView;
    }
}
