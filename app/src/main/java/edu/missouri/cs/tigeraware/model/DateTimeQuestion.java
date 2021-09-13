package edu.missouri.cs.tigeraware.model;

/**
 * Created by LSY on 2017/7/27.
 */
public class DateTimeQuestion extends Question {

    public DateTimeQuestion() {
    }

    public DateTimeQuestion(String id, String title, String conditionId, String on) {
        super(id, title, conditionId, on);
    }

    @Override
    public String getTypeDescription() {
        return DATE_TIME_QUESTION_TYPE_DESCRIPTION;
    }
}
