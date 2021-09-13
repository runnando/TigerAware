package edu.missouri.cs.tigeraware.model;

/**
 * Created by LSY on 2017/7/27.
 */
public class IntroTextQuestion extends Question {

    public IntroTextQuestion() {
    }

    public IntroTextQuestion(String id, String title, String conditionId, String on) {
        super(id, title, conditionId, on);
    }

    @Override
    public String getTypeDescription() {
        return INTRO_TEXT_QUESTION_TYPE_DESCRIPTION;
    }
}
