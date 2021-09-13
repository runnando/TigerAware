package edu.missouri.cs.tigeraware.model;

/**
 * Created by LSY on 2017/7/27.
 */
public class TextFieldQuestion extends Question {

    public TextFieldQuestion() {
    }

    public TextFieldQuestion(String id, String title, String conditionId, String on) {
        super(id, title, conditionId, on);
    }

    @Override
    public String getTypeDescription() {
        return TEXT_FIELD_QUESTION_TYPE_DESCRIPTION;
    }
}
