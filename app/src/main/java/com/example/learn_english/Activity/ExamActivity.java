package com.example.learn_english.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.learn_english.Adapter.ExamAdapter;
import com.example.learn_english.Database.Model;
import com.example.learn_english.Object.Exam;
import com.example.learn_english.Interface.OnUpdateResult;
import com.example.learn_english.R;

import java.util.List;

public class ExamActivity extends AppCompatActivity implements OnUpdateResult {

    ViewPager vpExam;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        int topicID = getIntent().getIntExtra("topic_id", 0);

        vpExam = findViewById(R.id.vp_exam);
        Model model = new Model(ExamActivity.this);
        List<Exam> listExam = model.getListExam(topicID);
        total = listExam.size();
        ExamAdapter examAdapter = new ExamAdapter(getSupportFragmentManager(), listExam, ExamActivity.this);
        vpExam.setAdapter(examAdapter);
    }

    @Override
    public void onUpdateResult(List<Exam> listExam) {
        int correct = 0;
        for (Exam exam : listExam) {
            if (exam.getAnsChoose().equals(exam.getAnsCorrect())) {
                correct++;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ExamActivity.this);
        builder.setTitle("Thông báo");
        builder.setMessage("Số câu làm đúng: " + correct + "/" + total);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}