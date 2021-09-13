package edu.missouri.cs.tigeraware.model;

/**
 * Created by LSY on 2017/7/27.
 */
public class ConditionalQuestion extends Question {

    public ConditionalQuestion() {
    }

    public ConditionalQuestion(String id, String title, String conditionId, String on) {
        super(id, title, conditionId, on);
    }

    @Override
    public String getTypeDescription() {
        return CONDITIONAL_QUESTION_TYPE_DESCRIPTION;
    }
}
