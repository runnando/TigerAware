package edu.missouri.cs.tigeraware.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.missouri.cs.tigeraware.model.Question;
import edu.missouri.cs.tigeraware.model.Survey;

/**
 * Created by LSY on 2017/8/4.
 */
public class SurveyDetailAdapter extends BaseAdapter {

    private List<Question> mData;

    public SurveyDetailAdapter() {
        mData = new ArrayList<>();
    }

    public SurveyDetailAdapter(List<Question> mData) {
        this.mData = mData;
    }

    class ViewHolder {
        TextView questionNameTextView;
        TextView questionTypeTextView;
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
            holder.questionNameTextView = (TextView) convertView.findViewById(android.R.id.text1);
            holder.questionTypeTextView = (TextView) convertView.findViewById(android.R.id.text2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.questionNameTextView.setText(mData.get(position).getId());
        holder.questionTypeTextView.setText(mData.get(position).getTypeDescription());

        return convertView;
    }

    public void setData(List<Question> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public List<Question> getData() {
        return mData;
    }

    public void addQueation(Question question) {
        mData.add(question);
        notifyDataSetChanged();
    }
}
