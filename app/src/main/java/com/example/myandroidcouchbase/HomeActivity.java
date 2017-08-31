package com.example.myandroidcouchbase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Document;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.example.myandroidcouchbase.adapter.HomeAdapter;
import com.example.myandroidcouchbase.model.Question;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static String EXTRA_INTENT_ID = "id";
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvQuestions);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        DataManager manager = DataManager.getSharedInstance(getApplicationContext());

        // 1
        QueryEnumerator questions = null;
        try {
            questions = Question.getQuestions(manager.database).run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

        // 2
        List<Question> data = new ArrayList<>();
        for (QueryRow question : questions) {
            Document document = question.getDocument();
            Question model = ModelHelper.modelForDocument(document, Question.class);
            data.add(model);
        }

        // 3
        final HomeAdapter adapter = new HomeAdapter(data);
        mRecyclerView.setAdapter(adapter);


    }

}
