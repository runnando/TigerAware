package edu.missouri.cs.tigeraware.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.missouri.cs.tigeraware.R;
import edu.missouri.cs.tigeraware.model.Survey;

/**
 * Created by LSY on 2017/7/28.
 */
public class SurveysAdapter extends BaseAdapter {

    private List<Survey> mData;

    public SurveysAdapter() {
        mData = new ArrayList<>();
    }

    public SurveysAdapter(List<Survey> mData) {
        this.mData = mData;
    }

    class ViewHoler {
        TextView surveyNameTextView;
        TextView surveyUserTextView;
        TextView takeTimeTextView;
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
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler holder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.surveys_lv_item_layout, null);

            holder = new ViewHoler();
            holder.surveyNameTextView = (TextView) convertView.findViewById(R.id.surveyNameTextView);
            holder.surveyUserTextView = (TextView) convertView.findViewById(R.id.surveyUserTextView);
            holder.takeTimeTextView = (TextView) convertView.findViewById(R.id.takeTimeTextView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHoler) convertView.getTag();
        }

        holder.surveyNameTextView.setText(mData.get(position).getName());
        holder.surveyUserTextView.setText(mData.get(position).getUser());
        holder.takeTimeTextView.setText(mData.get(position).getTakeTime());

        return convertView;
    }

    public void setData(List<Survey> data) {
        this.mData = data;
        notifyDataSetChanged();
    }
}
