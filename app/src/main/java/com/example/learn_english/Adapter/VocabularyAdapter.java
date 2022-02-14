package com.example.learn_english.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.learn_english.Object.Vocabulary;
import com.example.learn_english.R;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

public class VocabularyAdapter extends ArrayAdapter<Vocabulary> {
    Context context;
    int resource;
    List<Vocabulary> listVocabulary;
    String lang;

    public VocabularyAdapter(@NonNull Context context, int resource, @NonNull List<Vocabulary> objects, String lang) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.listVocabulary = objects;
        this.lang = lang;
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
                JSONObject jsonData = new JSONObject();
                JSONObject tmp = new JSONObject();
                try {
                    jsonData.put("engine", "Google");
                    tmp.put("voice", lang.equals("english") ? "en-US" : "cmn-Hant-TW");
                    tmp.put("text", vocabulary.getVocabulary());
                    jsonData.put("data", tmp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestQueue queue = Volley.newRequestQueue(getContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://api.soundoftext.com/sounds", jsonData,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                MediaPlayer mediaPlayer = new MediaPlayer();
                                try {
                                    mediaPlayer.setDataSource("https://storage.soundoftext.com/" + response.getString("id") + ".mp3");
                                    mediaPlayer.prepare();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mediaPlayer.start();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("MyApp", error.toString());
                            }
                        })
                {
                    @Override
                    public Map<String,String> getHeaders(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("Content-type", "application/json");
                        return params;
                    }
                };
                queue.add(jsonObjectRequest);
            }
        });

        return convertView;
    }
}
