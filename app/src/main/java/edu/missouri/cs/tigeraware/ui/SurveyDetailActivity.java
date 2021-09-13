package edu.missouri.cs.tigeraware.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.missouri.cs.tigeraware.R;
import edu.missouri.cs.tigeraware.adapter.SurveyDetailAdapter;
import edu.missouri.cs.tigeraware.model.Question;

public class SurveyDetailActivity extends CloseKeyboardActivity {

    public static final int CREATE_NEW = 0;
    public static final int MODIFY = 1;

    public static final int CANCEL = 0;
    public static final int CUCCESS = 1;

    private EditText mSurveyNameEditText;
    private ListView mQuestionsListView;
    private Button mAddQuestionButton;
    private Button mCancelButton;
    private Button mSaveButton;

    SurveyDetailAdapter mSurveyDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_detail);

        initUI();
    }

    private void initUI() {
        mSurveyNameEditText = (EditText) findViewById(R.id.surveyNameEditText);

        mQuestionsListView = (ListView) findViewById(R.id.questionsListView);
        mSurveyDetailAdapter = new SurveyDetailAdapter();
        mQuestionsListView.setAdapter(mSurveyDetailAdapter);

        mAddQuestionButton = (Button) findViewById(R.id.addQuestionButton);
        mAddQuestionButton.setOnClickListener(addQuestionButtonClickListener);

        mCancelButton = (Button) findViewById(R.id.cancelButton);
        mCancelButton.setOnClickListener(cancelButtonClickListener);

        mSaveButton = (Button) findViewById(R.id.saveButton);
        mSaveButton.setOnClickListener(saveButtonClickListener);
    }

    private View.OnClickListener addQuestionButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SurveyDetailActivity.this, QuestionDetailActivity.class);
            startActivityForResult(intent, CREATE_NEW);
        }
    };

    private View.OnClickListener cancelButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setResult(CANCEL);
            finish();
        }
    };

    private View.OnClickListener saveButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ArrayList<Question> questions = (ArrayList<Question>) mSurveyDetailAdapter.getData();
            Intent intent = new Intent();
            intent.putExtra("questions", questions);
            String surveyName = mSurveyNameEditText.getText().toString();
            if (surveyName == null) {
                surveyName = "";
            }
            intent.putExtra("surveyName", surveyName);

            setResult(CUCCESS, intent);
            finish();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == QuestionDetailActivity.CUCCESS) {
            Question question = (Question) data.getSerializableExtra("question");
            mSurveyDetailAdapter.addQueation(question);
        }
    }
}
