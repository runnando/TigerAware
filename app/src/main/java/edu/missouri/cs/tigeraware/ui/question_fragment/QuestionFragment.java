package edu.missouri.cs.tigeraware.ui.question_fragment;

import android.app.Fragment;
import android.view.View;
import android.widget.EditText;

import edu.missouri.cs.tigeraware.R;
import edu.missouri.cs.tigeraware.model.Question;

/**
 * Created by LSY on 2017/8/6.
 */
public abstract class QuestionFragment extends Fragment {

    protected  View mView;

    protected EditText mQuestionTitleEditText;

    public abstract Question getQuestion();

    protected void initUI() {
        mQuestionTitleEditText = (EditText) mView.findViewById(R.id.questionTitleEditText);
    };
}
