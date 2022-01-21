package com.example.learn_english.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.learn_english.Fragment.ExamFragment;
import com.example.learn_english.Object.Exam;
import com.example.learn_english.Interface.OnUpdateResult;

import java.io.Serializable;
import java.util.List;

public class ExamAdapter extends FragmentStatePagerAdapter {

    List<Exam> listExam;
    OnUpdateResult onUpdateResult;
    String language;

    public ExamAdapter(@NonNull FragmentManager fm, List<Exam> listExam, OnUpdateResult onUpdateResult) {
        super(fm);
        this.listExam = listExam;
        this.onUpdateResult = onUpdateResult;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        ExamFragment testFragment = new ExamFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable)listExam);
        bundle.putInt("pos", position);
        bundle.putSerializable("i", onUpdateResult);
        testFragment.setArguments(bundle);
        return testFragment;
    }

    @Override
    public int getCount() {
        return listExam.size();
    }
}
