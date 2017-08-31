package com.example.myandroidcouchbase.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myandroidcouchbase.R;

import java.util.List;
import java.util.Map;

public class QuestionOptionsAdapter extends BaseAdapter {

    private final List<String> mOptions;
    private Map<String, Integer> mAnswerCounts;

    public QuestionOptionsAdapter(List<String> options, Map<String, Integer> answerCounts) {
        mOptions = options;
        mAnswerCounts = answerCounts;
    }

    public void setAnswerCounts(Map<String, Integer> answerCounts) {
        mAnswerCounts = answerCounts;
    }

    @Override
    public int getCount() {
        return mOptions.size();
    }

    @Override
    public String getItem(int position) {
        return mOptions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_answer_option, parent, false);
        }

        TextView answerView = convertView.findViewById(R.id.textOption);
        TextView countView = convertView.findViewById(R.id.textNum);

        String text = getItem(position);
        answerView.setText(text);

        Integer count = 0;

        if (mAnswerCounts != null) {
            count = mAnswerCounts.get(text);
        }

        countView.setText(count.toString());

        return convertView;
    }
}
