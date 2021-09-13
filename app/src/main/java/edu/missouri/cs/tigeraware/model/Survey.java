package edu.missouri.cs.tigeraware.model;

import java.util.List;

/**
 * Created by LSY on 2017/7/27.
 */
public class Survey {

    private static final double MINUTE_PER_QUESTION = 0.5;

    private String id;
    private String name;
    private List<Question> questions;
    private String user;

    public Survey(String id, String name, List<Question> questions, String user) {
        this.id = id;
        this.name = name;
        this.questions = questions;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTakeTime() {
        double takeTime = questions.size() * MINUTE_PER_QUESTION;
        if (takeTime < 1.0) {
            return "<1 min";
        } else  {
            return takeTime + " min";
        }
    }
}
