package com.example.myandroidcouchbase;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.couchbase.lite.Attachment;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.Revision;
import com.couchbase.lite.support.LazyJsonArray;
import com.example.myandroidcouchbase.adapter.QuestionOptionsAdapter;
import com.example.myandroidcouchbase.model.Answer;
import com.example.myandroidcouchbase.model.Question;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

        Query answers = Answer.getAnswersForQuestion(manager.database, mQuestion.get_id());
        QueryEnumerator answersQuery = null;
        try {
            answersQuery = answers.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        mQuestionOptions = (GridView) findViewById(R.id.question_options);
        mQuestionOptions.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mQuestionOptions.setNumColumns(2);
        mQuestionOptions.setSelector(R.drawable.selector_button);

        mQuestionOptions.setAdapter(new QuestionOptionsAdapter(mQuestion.getOptions(), getAnswerCounts(answersQuery)));

        mQuestionOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedOption = position;
            }
        });

        Revision revision = document.getCurrentRevision();
        Attachment attachment = revision.getAttachment("image");
        if (attachment != null) {
            InputStream is = null;
            try {
                is = attachment.getContent();
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
            Drawable drawable = Drawable.createFromStream(is, "image");
            mImageQuestion.setImageDrawable(drawable);
        }

    }

    public void onButtonClicked(View view) {
        Answer answer = new Answer(mQuestion.get_id(), "answer", mQuestion.getOptions().get(mSelectedOption));
        ModelHelper.save(DataManager.getSharedInstance(getApplicationContext()).database, answer);
    }

    private Map<String,Integer> getAnswerCounts(QueryEnumerator answers) {

        Map<String,Integer> answerCounts = new HashMap<String, Integer>();

        for (String option: mQuestion.getOptions()) {
            answerCounts.put(option, 0);
        }

        for (QueryRow row : answers) {
            LazyJsonArray<Object> key = (LazyJsonArray<Object>) row.getKey();
            String answer = (String) key.get(1);
            answerCounts.put(answer, (Integer)row.getValue());
        }

        return answerCounts;
    }

}
