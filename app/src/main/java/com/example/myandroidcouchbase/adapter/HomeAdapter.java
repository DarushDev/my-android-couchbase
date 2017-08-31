package com.example.myandroidcouchbase.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myandroidcouchbase.R;
import com.example.myandroidcouchbase.model.Question;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<Question> mQuestions;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void OnClick(View view, int position);
    }

    public HomeAdapter(List<Question> questions) {
        this.mQuestions = questions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View questionView = inflater.inflate(R.layout.row_questions, parent, false);
        return new ViewHolder(questionView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // 1
        Question question = mQuestions.get(position);

        // 2
        switch (question.getTag()) {
            case "science":
                holder.itemView.setBackgroundColor(Color.parseColor("#EC5766"));
                break;
            case "geography":
                holder.itemView.setBackgroundColor(Color.parseColor("#FFC857"));
                break;
            case "android":
                holder.itemView.setBackgroundColor(Color.parseColor("#63ADF2"));
                break;
            case "logic":
                holder.itemView.setBackgroundColor(Color.parseColor("#86CD82"));
        }

        // 3
        holder.mQuestion.setText(question.getText());
        holder.mTag.setText(question.getTag());

        // 4
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.OnClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mQuestion;
        private TextView mTag;

        public ViewHolder(View itemView) {
            super(itemView);
            mQuestion = itemView.findViewById(R.id.question);
            mTag = itemView.findViewById(R.id.tagText);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }
}
