package edu.missouri.cs.tigeraware.ui.question_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.missouri.cs.tigeraware.R;
import edu.missouri.cs.tigeraware.model.Question;
import edu.missouri.cs.tigeraware.model.TextFieldQuestion;
import edu.missouri.cs.tigeraware.model.YesOrNoQuestion;

/**
 * Created by LSY on 2017/8/6.
 */
public class TextFieldFragment extends QuestionFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_text_field, container, false);
        initUI();
        return mView;
    }

    protected void initUI() {
        super.initUI();
    }

    @Override
    public Question getQuestion() {
        String title = mQuestionTitleEditText.getText().toString();
        if (title == null) {
            title = "";
        }

        return new TextFieldQuestion("", title, "", "");
    }
}
