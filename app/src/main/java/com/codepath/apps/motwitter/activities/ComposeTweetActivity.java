package com.codepath.apps.motwitter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.codepath.apps.motwitter.R;
import com.codepath.apps.motwitter.models.TwitterUser;

public class ComposeTweetActivity extends AppCompatActivity {

    TwitterUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
    }

    public void tweet(View view) {
    }

    public void clear(View view) {
    }
}
