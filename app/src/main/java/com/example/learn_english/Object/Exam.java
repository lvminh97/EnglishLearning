package com.example.learn_english.Object;

import java.io.Serializable;

public class Exam implements Serializable {
    String examImage;
    String examMean;
    String ans1;
    String ans2;
    String ans3;
    String ansCorrect;
    String ansChoose;

    public Exam(String examImage, String examMean, String ans1, String ans2, String ans3, String ansCorrect, String ansChoose) {
        this.examImage = examImage;
        this.examMean = examMean;
        this.ans1 = ans1;
        this.ans2 = ans2;
        this.ans3 = ans3;
        this.ansCorrect = ansCorrect;
        this.ansChoose = ansChoose;
    }

    public String getExamImage() {
        return examImage;
    }

    public void setExamImage(String examImage) {
        this.examImage = examImage;
    }

    public String getExamMean() {
        return examMean;
    }

    public void setExamMean(String examMean) {
        this.examMean = examMean;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public String getAnsCorrect() {
        return ansCorrect;
    }

    public void setAnsCorrect(String ansCorrect) {
        this.ansCorrect = ansCorrect;
    }

    public String getAnsChoose() {
        return ansChoose;
    }

    public void setAnsChoose(String ansChoose) {
        this.ansChoose = ansChoose;
    }
}
