package edu.missouri.cs.tigeraware.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import edu.missouri.cs.tigeraware.R;
import edu.missouri.cs.tigeraware.adapter.UserSurveyAdapter;
import edu.missouri.cs.tigeraware.model.Question;
import edu.missouri.cs.tigeraware.model.Survey;

/**
 * Created by LSY on 2017/8/4.
 */
public class MainUserSurveysTab extends SurveyFragment {

    public static final int CREATE_NEW = 0;
    public static final int MODIFY = 1;

    private View mView;
    private ListView mUserSurveyListView;
    private UserSurveyAdapter mUserSurveyAdapter;

    private Button mNewSurveyButton;
    private Button mSignOutButton;

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.main_user_surveys_tab, container, false);
        // Get Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();
        initUI();
        return mView;
    }

    private void initUI() {
        mUserSurveyListView = (ListView) mView.findViewById(R.id.userSurveysListView);
        mUserSurveyAdapter = new UserSurveyAdapter();
        mUserSurveyListView.setAdapter(mUserSurveyAdapter);

        mNewSurveyButton = (Button) mView.findViewById(R.id.newSurveyButton);
        mNewSurveyButton.setOnClickListener(newSurveyButtonClickListener);

        mSignOutButton = (Button) mView.findViewById(R.id.signOutButton);
        mSignOutButton.setOnClickListener(signOutButtonClickListener);
    }

    private View.OnClickListener newSurveyButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), SurveyDetailActivity.class);
            startActivityForResult(intent, CREATE_NEW);
        }
    };

    private View.OnClickListener signOutButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Sign out current account
            mAuth.signOut();
            // Start Login activity
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            // Close main activity
            getActivity().finish();
        }
    };

    @Override
    public void onDataChange(List<Survey> surveys) {
        mUserSurveyAdapter.setData(surveys);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == SurveyDetailActivity.CUCCESS) {
            List<Question> questions = (ArrayList<Question>) data.getSerializableExtra("questions");
            String surveyName = data.getStringExtra("surveyName");
            Survey survey = new Survey("1234", surveyName, questions, "Hello");

            // TODO 暂时这样用，需要存储到网络上
            MainActivity activity = (MainActivity) getActivity();
            activity.getSurveys().add(survey);
            // TODO 这里的访问权限有问题，先这样用
            for (SurveyFragment fragment : activity.mTabs) {
                fragment.onDataChange(activity.getSurveys());
            }

        }
    }
}
