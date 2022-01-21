package com.example.learn_english.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.learn_english.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class TranslateFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Context context;
    private View view;
    private EditText sourceEd, targetEd;
    private Spinner sourceSpn, targetSpn;
    private int srcLang, tarLang;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_translate, container, false);
        initUI();
        return view;
    }

    private void initUI(){
        sourceEd = (EditText) view.findViewById(R.id.ed_sourcetext);
        sourceEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("SVMC", "Translating...");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        targetEd = (EditText) view.findViewById(R.id.ed_targettext);

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
        srcLang = 0;
        tarLang = 2;
        sourceSpn.setSelection(srcLang);
        targetSpn.setSelection(tarLang);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spn_source:
                if(position == tarLang){
                    tarLang = srcLang;
                }
                srcLang = position;
                break;
            case R.id.spn_target:
                if(position == srcLang){
                    srcLang = tarLang;
                }
                tarLang = position;
                break;
        }
        sourceSpn.setSelection(srcLang);
        targetSpn.setSelection(tarLang);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
