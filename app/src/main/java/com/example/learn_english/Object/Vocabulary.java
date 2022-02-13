package com.example.learn_english.Object;

import java.io.Serializable;

public class Vocabulary implements Serializable {
    String vocabularyID;
    String topicID;
    String vocabularyImage;
    String vocabulary;
    String mean;

    public Vocabulary(String vocabularyID, String topicID, String vocabularyImage, String vocabulary, String mean) {
        this.vocabularyID = vocabularyID;
        this.topicID = topicID;
        this.vocabularyImage = vocabularyImage;
        this.vocabulary = vocabulary;
        this.mean = mean;
    }

    public String getVocabularyID() {
        return vocabularyID;
    }

    public void setVocabularyID(String vocabularyID) {
        this.vocabularyID = vocabularyID;
    }

    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }

    public String getVocabularyImage() {
        return vocabularyImage;
    }

    public void setVocabularyImage(String vocabularyImage) {
        this.vocabularyImage = vocabularyImage;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }
}
