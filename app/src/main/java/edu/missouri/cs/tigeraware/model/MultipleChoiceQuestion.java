package edu.missouri.cs.tigeraware.model;

import java.util.List;

/**
 * Created by LSY on 2017/7/27.
 */
public class MultipleChoiceQuestion extends Question {

    public MultipleChoiceQuestion() {
    }

    public MultipleChoiceQuestion(String id, String title, String conditionId, String on, List<String> choices) {
        super(id, title, conditionId, on);
        this.choices = choices;
    }

    // The choices for the question
    private List<String> choices;

    @Override
    public String getTypeDescription() {
        return MULTIPLE_CHOICE_QUESTION_TYPE_DESCRIPTION;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
}
