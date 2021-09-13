package edu.missouri.cs.tigeraware.model;

/**
 * Yes or no question
 * Created by LSY on 2017/7/27.
 */
public class YesOrNoQuestion extends Question {

    public YesOrNoQuestion() {
    }

    public YesOrNoQuestion(String id, String title, String conditionId, String on) {
        super(id, title, conditionId, on);
    }

    @Override
    public String getTypeDescription() {
        return YES_OR_NO_QUESTION_TYPE_DESCRIPTION;
    }
}
