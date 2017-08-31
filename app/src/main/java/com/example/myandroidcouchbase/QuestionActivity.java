package com.example.myandroidcouchbase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myandroidcouchbase.model.Question;

public class QuestionActivity extends AppCompatActivity {

  private TextView mTextView;
  private GridView mQuestionOptions;
  private ImageView mImageQuestion;

  private int mSelectedOption;
  private Question mQuestion;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);

    mTextView = (TextView) findViewById(R.id.question_view);
    mImageQuestion = (ImageView) findViewById(R.id.imageQuestion);

  }

}
