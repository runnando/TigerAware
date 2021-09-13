package edu.missouri.cs.tigeraware.model;

import java.io.Serializable;

/**
 * Base class for survey questions
 * Created by LSY on 2017/7/27.
 */
public class Question implements Serializable {

    // Constants for question type descriptions
    public static final String BASE_QUESTION_TYPE_DESCRIPTION = "base";
    public static final String YES_OR_NO_QUESTION_TYPE_DESCRIPTION = "yesNo";
    public static final String INTRO_TEXT_QUESTION_TYPE_DESCRIPTION = "textSlide";
    public static final String TEXT_FIELD_QUESTION_TYPE_DESCRIPTION = "textField";
    public static final String IMAGE_CAPTURE_QUESTION_TYPE_DESCRIPTION = "NotImplemented";
    public static final String CONDITIONAL_QUESTION_TYPE_DESCRIPTION = "NotImplemented";
    public static final String TIME_INTERVAL_QUESTION_TYPE_DESCRIPTION = "timeInt";
    public static final String DATE_TIME_QUESTION_TYPE_DESCRIPTION = "dateTime";
    public static final String MULTIPLE_CHOICE_QUESTION_TYPE_DESCRIPTION = "MultipleChoice";
    public static final String SCALE_QUESTION_TYPE_DESCRIPTION = "Scale";

    private String id;
    private String title;
    private String conditionId;
    private String on;

    public Question() {}

    public Question(String id, String title, String conditionId, String on) {
        this.id = id;
        this.title = title;
        this.conditionId = conditionId;
        this.on = on;
    }

    public String getTypeDescription() {
        return BASE_QUESTION_TYPE_DESCRIPTION;
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }
}
