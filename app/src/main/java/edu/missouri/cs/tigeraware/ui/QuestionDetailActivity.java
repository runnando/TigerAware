package edu.missouri.cs.tigeraware.ui;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import edu.missouri.cs.tigeraware.R;
import edu.missouri.cs.tigeraware.model.Question;
import edu.missouri.cs.tigeraware.ui.question_fragment.ConditionalFragment;
import edu.missouri.cs.tigeraware.ui.question_fragment.DateTimeFragment;
import edu.missouri.cs.tigeraware.ui.question_fragment.ImageCaptureFragment;
import edu.missouri.cs.tigeraware.ui.question_fragment.IntroTextFragment;
import edu.missouri.cs.tigeraware.ui.question_fragment.MultipleChoiceFragment;
import edu.missouri.cs.tigeraware.ui.question_fragment.QuestionFragment;
import edu.missouri.cs.tigeraware.ui.question_fragment.ScaleFragment;
import edu.missouri.cs.tigeraware.ui.question_fragment.TextFieldFragment;
import edu.missouri.cs.tigeraware.ui.question_fragment.TimeIntervalFragment;
import edu.missouri.cs.tigeraware.ui.question_fragment.YesOrNoFragment;

public class QuestionDetailActivity extends Activity {

    public static final int CANCEL = 0;
    public static final int CUCCESS = 1;

    public static final int YES_OR_NO_QUESTION = 0;
    public static final int INTRO_TEXT_QUESTION = 1;
    public static final int TEXT_FIELD_QUESTION = 2;
    public static final int IMAGE_CAPTURE_QUESTION = 3;
    public static final int CONDITIONAL_QUESTION = 4;
    public static final int TIME_INTERVAL_QUESTION = 5;
    public static final int DATE_TIME_QUESTION = 6;
    public static final int MULTIPLE_CHOICE_QUESTION = 7;
    public static final int SCALE_QUESTION = 8;

    private EditText mQuestionLabelEditText;
    private Spinner mQuestionTypeSpinner;
    private QuestionFragment questionBodyFragment;
    private Button mSaveQuestionButton;

    private int currentType = -1;
    List<QuestionFragment> mQuestionBodyFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        initUI();
    }

    private void initUI() {
        mQuestionLabelEditText = (EditText) findViewById(R.id.questionLabelEditText);

        mQuestionTypeSpinner = (Spinner) findViewById(R.id.questionTypeSpinner);
        mQuestionTypeSpinner.setOnItemSelectedListener(mQuestionTypeSpinnerItemSelectedListener);

        mSaveQuestionButton = (Button) findViewById(R.id.saveQuestionButton);
        mSaveQuestionButton.setOnClickListener(saveQuestionButtonClickListener);

        initQuestionBodyFragments();
    }

    private void initQuestionBodyFragments() {
        mQuestionBodyFragments = new ArrayList<>();

        QuestionFragment fragment = null;

        fragment = new YesOrNoFragment();
        mQuestionBodyFragments.add(fragment);

        fragment = new IntroTextFragment();
        mQuestionBodyFragments.add(fragment);

        fragment = new TextFieldFragment();
        mQuestionBodyFragments.add(fragment);

        fragment = new ImageCaptureFragment();
        mQuestionBodyFragments.add(fragment);

        fragment = new ConditionalFragment();
        mQuestionBodyFragments.add(fragment);

        fragment = new TimeIntervalFragment();
        mQuestionBodyFragments.add(fragment);

        fragment = new DateTimeFragment();
        mQuestionBodyFragments.add(fragment);

        fragment = new MultipleChoiceFragment();
        mQuestionBodyFragments.add(fragment);

        fragment = new ScaleFragment();
        mQuestionBodyFragments.add(fragment);

        setFragment(YES_OR_NO_QUESTION);
    }

    private void setFragment(int type) {
        if (currentType != type) {
            currentType = type;
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.questionBodyFragmentContainer, mQuestionBodyFragments.get(type));
            transaction.commit();
        }
    }

    OnItemSelectedListener mQuestionTypeSpinnerItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            setFragment(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    View.OnClickListener saveQuestionButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Question question = mQuestionBodyFragments.get(currentType).getQuestion();

            String id = mQuestionLabelEditText.getText().toString();
            if (id == null) {
                id = "";
            }
            question.setId(id);

            Intent intent = new Intent();
            intent.putExtra("question", question);
            setResult(CUCCESS, intent);
            finish();
        }
    };

}
