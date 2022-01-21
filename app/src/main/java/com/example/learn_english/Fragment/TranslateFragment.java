package com.example.learn_english.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.learn_english.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class TranslateFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private final String url = "https://google-translate1.p.rapidapi.com/language/translate/v2";

    private Context context;
    private View view;
    private EditText sourceEd, targetEd;
    private Spinner sourceSpn, targetSpn;
    private Button translateBtn;
    private int srcLangId, tarLangId;
    private String[] lang = {"en", "zh", "vi"};

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_translate, container, false);
        initUI();
        return view;
    }

    private void initUI(){
        getActivity().setTitle("Translate");

        sourceSpn = (Spinner) view.findViewById(R.id.spn_source);
        ArrayAdapter<String> sourceSpnAdpt = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"Tiếng Anh", "Tiếng Trung", "Tiếng Việt"});
        sourceSpnAdpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceSpn.setAdapter(sourceSpnAdpt);
        sourceSpn.setOnItemSelectedListener(this);
        targetSpn = (Spinner) view.findViewById(R.id.spn_target);
        ArrayAdapter<String> targetSpnAdpt = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"Tiếng Anh", "Tiếng Trung", "Tiếng Việt"});
        targetSpnAdpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        targetSpn.setAdapter(targetSpnAdpt);
        targetSpn.setOnItemSelectedListener(this);
        srcLangId = 0;
        tarLangId = 2;
        sourceSpn.setSelection(srcLangId);
        targetSpn.setSelection(tarLangId);

        sourceEd = (EditText) view.findViewById(R.id.ed_sourcetext);
        targetEd = (EditText) view.findViewById(R.id.ed_targettext);

        translateBtn = (Button) view.findViewById(R.id.btn_translate);
        translateBtn.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spn_source:
                if(position == tarLangId){
                    tarLangId = srcLangId;
                }
                srcLangId = position;
                break;
            case R.id.spn_target:
                if(position == srcLangId){
                    srcLangId = tarLangId;
                }
                tarLangId = position;
                break;
        }
        sourceSpn.setSelection(srcLangId);
        targetSpn.setSelection(tarLangId);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_translate){
            RequestQueue queue = Volley.newRequestQueue(getActivity().getBaseContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject json = new JSONObject(response);
                                targetEd.setText(json.getJSONObject("data").getJSONArray("translations").getJSONObject(0).getString("translatedText"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("TranslateFrag", error.networkResponse.statusCode + "");
                        }
                    })
            {
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("source", lang[srcLangId]);
                    params.put("target", lang[tarLangId]);
                    params.put("q", sourceEd.getText().toString());
                    return params;
                }

                @Override
                public Map<String,String> getHeaders(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("content-type", "application/x-www-form-urlencoded");
                    params.put("accept-encoding", "application/gzip");
                    params.put("x-rapidapi-key", "75f3c3b974msh04a31742239c85cp127132jsnaa1bba8f909c");
                    params.put("x-rapidapi-host", "google-translate1.p.rapidapi.com");
                    return params;
                }
            };
            queue.add(stringRequest);
        }
    }
}
