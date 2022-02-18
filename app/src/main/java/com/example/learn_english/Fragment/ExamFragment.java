package com.example.learn_english.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.learn_english.Interface.OnUpdateResult;
import com.example.learn_english.Object.Exam;
import com.example.learn_english.R;

import java.util.Base64;
import java.util.List;

public class ExamFragment extends Fragment {
    List<Exam> listExam;
    OnUpdateResult onUpdateResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listExam = (List<Exam>) getArguments().getSerializable("list");
        onUpdateResult = (OnUpdateResult) getArguments().getSerializable("i");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam, container, false);
        int pos = getArguments().getInt("pos");
        TextView txtNumber = view.findViewById(R.id.txt_number);
        ImageView imgExam = view.findViewById(R.id.img_exam);
        TextView meanTv = view.findViewById(R.id.tv_mean);
        RadioGroup rgAns = view.findViewById(R.id.rg_ans);
        RadioButton rbAns1 = view.findViewById(R.id.rbAns1);
        RadioButton rbAns2 = view.findViewById(R.id.rbAns2);
        RadioButton rbAns3 = view.findViewById(R.id.rbAns3);
        Button btnCheck = view.findViewById(R.id.btn_check);
        final Exam exam = listExam.get(pos);
        txtNumber.setText("Câu số: " + (pos + 1));
        meanTv.setText(exam.getExamMean());

        byte[] decodedString = Base64.getDecoder().decode(exam.getExamImage());
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgExam.setImageBitmap(decodedByte);

        rbAns1.setText(exam.getAns1());
        rbAns2.setText(exam.getAns2());
        rbAns3.setText(exam.getAns3());

        rgAns.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                exam.setAnsChoose(((RadioButton)view.findViewById(checkedId)).getText().toString());
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateResult.onUpdateResult(listExam);
            }
        });

        return view;
    }
}