package edu.missouri.cs.tigeraware.model;

/**
 * Created by LSY on 2017/7/27.
 */
public class TimeIntervalQuestion extends Question {

    public TimeIntervalQuestion() {
    }

    public TimeIntervalQuestion(String id, String title, String conditionId, String on) {
        super(id, title, conditionId, on);
    }

    @Override
    public String getTypeDescription() {
        return TIME_INTERVAL_QUESTION_TYPE_DESCRIPTION;
    }
}
