package com.example.learn_english.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.learn_english.Adapter.ExamAdapter;
import com.example.learn_english.Database.Model;
import com.example.learn_english.Interface.OnUpdateResult;
import com.example.learn_english.Object.Exam;
import com.example.learn_english.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExamActivity extends AppCompatActivity implements OnUpdateResult {

    ViewPager vpExam;
    int total = 0;
    String language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        int topicID = getIntent().getIntExtra("topic_id", 0);
        language = getIntent().getStringExtra("language");

        vpExam = findViewById(R.id.vp_exam);

        List<Exam> listExam = getListExam();
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

    private List<Exam> getListExam(){
        List<Exam> list = new ArrayList<Exam>();
        int limit;
        if(Model.listVocabulary.size() < 5)
            limit = Model.listVocabulary.size();
        else
            limit = 5;

        Random rd = new Random();
        int[] quesId = new int[limit];
        for(int i = 0; i < limit; i++) {
            quesId[i] = rd.nextInt(Model.listVocabulary.size());
            for(int j = i - 1; j >= 0; j--){
                if(quesId[j] == quesId[i]) {
                    i--;
                    break;
                }
            }
        }

        for(int i = 0; i < limit; i++){
            Exam exam = new Exam(Model.listVocabulary.get(quesId[i]).getVocabularyImage(),
                    "",
                    "",
                    "",
                    Model.listVocabulary.get(quesId[i]).getVocabulary(),
                    "");

            String[] ans = new String[3];
            int[] opt = {0, 0, 0};
            opt[0] = rd.nextInt(Model.listVocabulary.size());
            while(opt[0] == quesId[i]){
                opt[0] = rd.nextInt(Model.listVocabulary.size());
            }
            opt[1] = rd.nextInt(Model.listVocabulary.size());
            while (opt[1] == quesId[i] || opt[1] == opt[0]) {
                opt[1] = rd.nextInt(Model.listVocabulary.size());
            }
            opt[2] = rd.nextInt(3);
            ans[opt[2]] = Model.listVocabulary.get(quesId[i]).getVocabulary();
            int tmp = 0;
            for(int j = 0; j < 3; j++){
                if(j == opt[2])
                    continue;
                ans[j] = Model.listVocabulary.get(opt[tmp]).getVocabulary();
                tmp++;
            }

            exam.setAns1(ans[0]);
            exam.setAns2(ans[1]);
            exam.setAns3(ans[2]);
            list.add(exam);
        }
        return list;
    }
}