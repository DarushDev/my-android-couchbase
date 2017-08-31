package com.example.myandroidcouchbase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {

  public static String EXTRA_INTENT_ID = "id";
  private RecyclerView mRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    mRecyclerView = (RecyclerView) findViewById(R.id.rvQuestions);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

  }

}
