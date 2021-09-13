package edu.missouri.cs.tigeraware.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.BooleanAnswerFormat;
import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.answerformat.DateAnswerFormat;
import org.researchstack.backbone.answerformat.DurationAnswerFormat;
import org.researchstack.backbone.answerformat.SliderAnswerFormat;
import org.researchstack.backbone.answerformat.TextAnswerFormat;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.step.InstructionStep;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.task.OrderedTask;
import org.researchstack.backbone.ui.ViewTaskActivity;
import org.researchstack.skin.model.TaskModel;
import org.researchstack.skin.task.SmartSurveyTask;

import java.util.ArrayList;
import java.util.List;

import edu.missouri.cs.tigeraware.R;
import edu.missouri.cs.tigeraware.adapter.SurveysAdapter;
import edu.missouri.cs.tigeraware.model.MultipleChoiceQuestion;
import edu.missouri.cs.tigeraware.model.Question;
import edu.missouri.cs.tigeraware.model.ScaleQuestion;
import edu.missouri.cs.tigeraware.model.Survey;

import static edu.missouri.cs.tigeraware.model.Question.DATE_TIME_QUESTION_TYPE_DESCRIPTION;
import static edu.missouri.cs.tigeraware.model.Question.INTRO_TEXT_QUESTION_TYPE_DESCRIPTION;
import static edu.missouri.cs.tigeraware.model.Question.MULTIPLE_CHOICE_QUESTION_TYPE_DESCRIPTION;
import static edu.missouri.cs.tigeraware.model.Question.SCALE_QUESTION_TYPE_DESCRIPTION;
import static edu.missouri.cs.tigeraware.model.Question.TEXT_FIELD_QUESTION_TYPE_DESCRIPTION;
import static edu.missouri.cs.tigeraware.model.Question.TIME_INTERVAL_QUESTION_TYPE_DESCRIPTION;
import static edu.missouri.cs.tigeraware.model.Question.YES_OR_NO_QUESTION_TYPE_DESCRIPTION;

/**
 * Created by LSY on 2017/8/4.
 */
public class MainSurveysTab extends SurveyFragment {

    // Activity Request Codes
    private static final int REQUEST_SURVEY = 1;

    private View mView;
    private ListView mSurveysListView;
    private SurveysAdapter mSurveysAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.main_surveys_tab, container, false);
        initUI();
        return mView;
    }

    private void initUI() {
        mSurveysListView = (ListView) mView.findViewById(R.id.surveysListView);
        mSurveysAdapter = new SurveysAdapter();
        mSurveysListView.setAdapter(mSurveysAdapter);
        mSurveysListView.setOnItemClickListener(mItemClickListener);
    }

    // Listener to handle the event for item touch
    private OnItemClickListener mItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            launchSurvey(position);
        }
    };

    // Launch the corresbonding Survey
    private void launchSurvey(int index) {
        List<Step> surveySteps = new ArrayList<>();
        Survey survey = ((MainActivity) getActivity()).getSurveys().get(index);
        for (Question question : survey.getQuestions()) {
            Step step = null;
            switch (question.getTypeDescription()) {
                case YES_OR_NO_QUESTION_TYPE_DESCRIPTION:
                    QuestionStep booleanStep = new QuestionStep(question.getId());
                    booleanStep.setStepTitle(R.string.survey);
                    booleanStep.setTitle(question.getTitle());
                    booleanStep.setAnswerFormat(new BooleanAnswerFormat(getString(R.string.rsb_yes),
                            getString(R.string.rsb_no)));
                    booleanStep.setOptional(false);
                    step = booleanStep;
                    break;

                case INTRO_TEXT_QUESTION_TYPE_DESCRIPTION:
                    InstructionStep instructionStep = new InstructionStep(question.getId(),
                            "Survey Introduction",
                            question.getTitle());
                    instructionStep.setStepTitle(R.string.survey);
                    step = instructionStep;
                    break;

                case TEXT_FIELD_QUESTION_TYPE_DESCRIPTION:
                    TextAnswerFormat format = new TextAnswerFormat();
                    QuestionStep textAnswerStep = new QuestionStep(question.getId(), question.getTitle(), format);
                    textAnswerStep.setStepTitle(R.string.survey);
                    step = textAnswerStep;
                    break;

//                case IMAGE_CAPTURE_QUESTION_TYPE_DESCRIPTION:
//                    break;
//
//                case CONDITIONAL_QUESTION_TYPE_DESCRIPTION:
//                    break;
//
                case TIME_INTERVAL_QUESTION_TYPE_DESCRIPTION:
                    // TODO How to use here
                    DurationAnswerFormat durationAnswerFormat = new DurationAnswerFormat(0, "");
                    QuestionStep durationAnswerStep = new QuestionStep(question.getId(), question.getTitle(), durationAnswerFormat);
                    durationAnswerStep.setStepTitle(R.string.survey);
                    step = durationAnswerStep;
                    break;

                case DATE_TIME_QUESTION_TYPE_DESCRIPTION:
                    DateAnswerFormat dateFormat = new DateAnswerFormat(AnswerFormat.DateAnswerStyle.Date);
                    QuestionStep dateStep = new QuestionStep(question.getId(), question.getTitle(), dateFormat);
                    dateStep.setStepTitle(R.string.survey);
                    step = dateStep;
                    break;

                case MULTIPLE_CHOICE_QUESTION_TYPE_DESCRIPTION:
                    QuestionStep multiStep = new QuestionStep("MULTI_STEP");
                    multiStep.setStepTitle(R.string.survey);
                    List<Choice<Integer>> choicesList = new ArrayList<>();
                    List<String> choicesString = ((MultipleChoiceQuestion) question).getChoices();
                    for (int i = 0; i < choicesString.size(); i++) {
                        choicesList.add(new Choice<>(choicesString.get(i), i));
                    }

                    Choice<Integer>[] choices = new Choice[choicesList.size()];
                    choicesList.toArray(choices);

                    AnswerFormat multiFormat = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.MultipleChoice, choices);

//                    AnswerFormat multiFormat = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.MultipleChoice, choices);
                    multiStep.setTitle("Select multiple");
                    multiStep.setAnswerFormat(multiFormat);
                    multiStep.setOptional(false);
                    step = multiStep;
                    break;

                case SCALE_QUESTION_TYPE_DESCRIPTION:
                    QuestionStep scaleStep = new QuestionStep("SCALE_STEP");
                    scaleStep.setStepTitle(R.string.survey);
                    List<String> scaleChoices = ((ScaleQuestion) question).getChoices();
                    int choicesCount = scaleChoices.size();
                    AnswerFormat answerFormat = new SliderAnswerFormat(0, choicesCount - 1);
                    ((SliderAnswerFormat) answerFormat).setMinText(scaleChoices.get(0));
                    ((SliderAnswerFormat) answerFormat).setMaxText(scaleChoices.get(choicesCount - 1));
                    scaleStep.setTitle("Select the scale");
                    scaleStep.setAnswerFormat(answerFormat);
                    step = scaleStep;
                    break;
            }
            if (step != null) {
                surveySteps.add(step);
            }
        }



        // Create a task wrapping the steps.
//        OrderedTask task = new OrderedTask("SAMPLE_SURVEY", surveySteps);
        TaskModel taskModel = new TaskModel();

        taskModel.identifier = "1";
        taskModel.guid = "2";
        taskModel.createdOn = "3";
        taskModel.type = "SurveyQuestion";
        taskModel.name = "5";

        TaskModel.StepModel step1 = new TaskModel.StepModel();
        step1.identifier = "identifier";
        step1.type = "SurveyQuestion";
        List<TaskModel.StepModel> stepModels = new ArrayList<>();
        stepModels.add(step1);

        taskModel.elements = stepModels;

        TaskModel.ConstraintsModel constraintsModel1 = new TaskModel.ConstraintsModel();
        constraintsModel1.type = "type";


    SmartSurveyTask task1 = new SmartSurveyTask(getActivity(), taskModel);


        // Create an activity using the task and set a delegate.
        Intent intent = ViewTaskActivity.newIntent(getActivity(), task1);
    startActivityForResult(intent, REQUEST_SURVEY);
}

    @Override
    public void onDataChange(List<Survey> surveys) {
        mSurveysAdapter.setData(surveys);
    }


    /**
     * The example of createing form stops
     * @return
     */
    /*
    @NonNull
    private FormStep createFormStep()
    {
        FormStep formStep = new FormStep(FORM_STEP, "Form", "Form groups multi-entry in one page");
        ArrayList<QuestionStep> formItems = new ArrayList<>();

        QuestionStep basicInfoHeader = new QuestionStep(BASIC_INFO_HEADER,
                "Basic Information",
                new UnknownAnswerFormat());
        formItems.add(basicInfoHeader);

        TextAnswerFormat format = new TextAnswerFormat();
        format.setIsMultipleLines(false);
        QuestionStep nameItem = new QuestionStep(FORM_NAME, "Name", format);
        formItems.add(nameItem);

        QuestionStep ageItem = new QuestionStep(FORM_AGE, "Age", new IntegerAnswerFormat(18, 90));
        formItems.add(ageItem);

        AnswerFormat genderFormat = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice,
                new Choice<>("Male", 0),
                new Choice<>("Female", 1));
        QuestionStep genderFormItem = new QuestionStep(FORM_GENDER, "Gender", genderFormat);
        formItems.add(genderFormItem);

        AnswerFormat multiFormat = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.MultipleChoice,
                new Choice<>("Zero", 0),
                new Choice<>("One", 1),
                new Choice<>("Two", 2));
        QuestionStep multiFormItem = new QuestionStep(FORM_MULTI_CHOICE, "Test Multi", multiFormat);
        formItems.add(multiFormItem);

        AnswerFormat dateOfBirthFormat = new DateAnswerFormat(AnswerFormat.DateAnswerStyle.Date);
        QuestionStep dateOfBirthFormItem = new QuestionStep(FORM_DATE_OF_BIRTH,
                "Birthdate",
                dateOfBirthFormat);
        formItems.add(dateOfBirthFormItem);

        // ... And so on, adding additional items
        formStep.setFormSteps(formItems);
        return formStep;
    }
    */


}
