package edu.missouri.cs.tigeraware.model;

/**
 * Created by LSY on 2017/7/27.
 */
public class ImageCaptureQuestion extends Question {

    public ImageCaptureQuestion() {
    }

    public ImageCaptureQuestion(String id, String title, String conditionId, String on) {
        super(id, title, conditionId, on);
    }

    @Override
    public String getTypeDescription() {
        return IMAGE_CAPTURE_QUESTION_TYPE_DESCRIPTION;
    }
}
