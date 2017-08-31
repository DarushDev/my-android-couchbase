package com.example.myandroidcouchbase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.couchbase.lite.Document;
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

        // 1
        Intent intent = getIntent();
        String questionId = intent.getStringExtra(HomeActivity.EXTRA_INTENT_ID);

        // 2
        DataManager manager = DataManager.getSharedInstance(getApplicationContext());
        Document document = manager.database.getDocument(questionId);

        // 3
        mQuestion = ModelHelper.modelForDocument(document, Question.class);
        mTextView.setText(mQuestion.getText());

    }

}
