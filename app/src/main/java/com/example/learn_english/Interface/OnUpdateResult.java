package com.example.learn_english.Interface;

import com.example.learn_english.Object.Exam;

import java.io.Serializable;
import java.util.List;

public interface OnUpdateResult extends Serializable {
    void onUpdateResult(List<Exam> listExam);
}
