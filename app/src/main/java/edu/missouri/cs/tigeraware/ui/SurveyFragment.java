package edu.missouri.cs.tigeraware.ui;

import java.util.List;

import edu.missouri.cs.tigeraware.model.Survey;

/**
 * Created by LSY on 2017/8/4.
 */

public abstract class SurveyFragment extends android.support.v4.app.Fragment {
    public abstract void onDataChange(List<Survey> surveys);
}
