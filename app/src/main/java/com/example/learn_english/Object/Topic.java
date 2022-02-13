package com.example.learn_english.Object;

public class Topic {
    String topicID;
    String topicName;
    String topicImage;

    public Topic(String topicID, String topicName, String imageName) {
        this.topicID = topicID;
        this.topicName = topicName;
        this.topicImage = imageName;
    }

    public String getTopicID() {
        return topicID;
    }

    public void setTopicID(String topicID) {
        this.topicID = topicID;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicImage() {
        return topicImage;
    }

    public void setTopicImage(String topicImage) {
        this.topicImage = topicImage;
    }
}
