package edu.missouri.cs.tigeraware.model;

import java.util.List;

/**
 * Created by LSY on 2017/7/27.
 */
public class ScaleQuestion extends Question {

    // The descriptions for scale
    private List<String> choices;

    public ScaleQuestion() {
    }

    public ScaleQuestion(String id, String title, String conditionId, String on, List<String> choices) {
        super(id, title, conditionId, on);
        this.choices = choices;
    }

    @Override
    public String getTypeDescription() {
        return SCALE_QUESTION_TYPE_DESCRIPTION;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
}
