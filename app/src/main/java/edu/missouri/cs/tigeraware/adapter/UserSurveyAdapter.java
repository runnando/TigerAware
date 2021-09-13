package edu.missouri.cs.tigeraware.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.missouri.cs.tigeraware.model.Survey;

/**
 * Created by LSY on 2017/8/4.
 */
public class UserSurveyAdapter extends BaseAdapter {

    private List<Survey> mData;

    public UserSurveyAdapter() {
        mData = new ArrayList<>();
    }

    public UserSurveyAdapter(List<Survey> mData) {
        this.mData = mData;
    }

    class ViewHolder {
        TextView surveyNameTextView;
        TextView questionCountTextView;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(android.R.layout.simple_list_item_activated_2, null);

            holder = new ViewHolder();
            holder.surveyNameTextView = (TextView) convertView.findViewById(android.R.id.text1);
            holder.questionCountTextView = (TextView) convertView.findViewById(android.R.id.text2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.surveyNameTextView.setText(mData.get(position).getName());
        int questionCount = mData.get(position).getQuestions().size();
        holder.questionCountTextView.setText(questionCount + " question" + (questionCount == 1 ? "" : "s"));

        return convertView;
    }

    public void setData(List<Survey> data) {
        this.mData = data;
        notifyDataSetChanged();
    }
}
