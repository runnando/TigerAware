package edu.missouri.cs.tigeraware.util;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.missouri.cs.tigeraware.model.DateTimeQuestion;
import edu.missouri.cs.tigeraware.model.IntroTextQuestion;
import edu.missouri.cs.tigeraware.model.MultipleChoiceQuestion;
import edu.missouri.cs.tigeraware.model.Question;
import edu.missouri.cs.tigeraware.model.ScaleQuestion;
import edu.missouri.cs.tigeraware.model.Survey;
import edu.missouri.cs.tigeraware.model.TextFieldQuestion;
import edu.missouri.cs.tigeraware.model.TimeIntervalQuestion;
import edu.missouri.cs.tigeraware.model.YesOrNoQuestion;

import static edu.missouri.cs.tigeraware.model.Question.*;

/**
 * Created by LSY on 2017/7/27.
 */
public class SurveyUtil {

    private SurveyUtil() {
    }

    /**
     * Parse all the surveys from DataSnapshot to a surveys list
     *
     * @param dataSnapshot From Google firebase under "blueprints"
     * @return The list of all the surveys
     */
    public static List<Survey> parseSurveys(DataSnapshot dataSnapshot) {
        List<Survey> surveys = new ArrayList<>();

        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            String surveyId = snapshot.getKey();
            Map<String, Object> surveyBody = (Map<String, Object>) snapshot.getValue();
            String surveyName = (String) surveyBody.get("name");
            String surveyUser = (String) surveyBody.get("user");
            List<Map<String, Object>> questionsBody = (ArrayList) surveyBody.get("survey");
            List<Question> questions = parseQuestions(questionsBody);
            Survey survey = new Survey(surveyId, surveyName, questions, surveyUser);
            surveys.add(survey);
        }

        return surveys;
    }

    /**
     * Parse all the Json representation data to Question class
     *
     * @param questionsBody
     * @return The list of all the question
     */
    private static List<Question> parseQuestions(List<Map<String, Object>> questionsBody) {
        List<Question> questions = new ArrayList<>();
        for (Map<String, Object> questionBody : questionsBody) {
            Question question = null;
            List<String> choices = null;
            String conditionId = (String) questionBody.get("conditionID");
            String id = (String) questionBody.get("id");
            String on = (String) questionBody.get("on");
            String title = (String) questionBody.get("title");
            String type = (String) questionBody.get("type");
            switch (type) {
                case YES_OR_NO_QUESTION_TYPE_DESCRIPTION:
                    question = new YesOrNoQuestion(id, title, conditionId, on);
                    break;

                case INTRO_TEXT_QUESTION_TYPE_DESCRIPTION:
                    question = new IntroTextQuestion(id, title, conditionId, on);
                    break;

                case TEXT_FIELD_QUESTION_TYPE_DESCRIPTION:
                    question = new TextFieldQuestion(id, title, conditionId, on);
                    break;

//                case IMAGE_CAPTURE_QUESTION_TYPE_DESCRIPTION:
//                    break;
//
//                case CONDITIONAL_QUESTION_TYPE_DESCRIPTION:
//                    break;
//
                case TIME_INTERVAL_QUESTION_TYPE_DESCRIPTION:
                    question = new TimeIntervalQuestion(id, title, conditionId, on);
                    break;

                case DATE_TIME_QUESTION_TYPE_DESCRIPTION:
                    question = new DateTimeQuestion(id, title, conditionId, on);
                    break;

                case MULTIPLE_CHOICE_QUESTION_TYPE_DESCRIPTION:
                    choices = (List<String>) questionBody.get("choices");
                    question = new MultipleChoiceQuestion(id, title, conditionId, on, choices);
                    break;

                case SCALE_QUESTION_TYPE_DESCRIPTION:
                    choices = (List<String>) questionBody.get("choices");
                    question = new ScaleQuestion(id, title, conditionId, on, choices);
                    break;
            }
            if (question != null) {
                questions.add(question);
            }
        }
        return questions;
    }
}
