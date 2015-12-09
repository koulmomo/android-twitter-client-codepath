package com.codepath.apps.motwitter.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.codepath.apps.motwitter.R;
import com.codepath.apps.motwitter.helpers.Utils;
import com.codepath.apps.motwitter.services.FetchHomeFeedService;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };


    void setUpBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mReceiver, new IntentFilter(FetchHomeFeedService.ACTION));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpBroadcastReceiver();

        fetchHomeFeed();
    }

    void fetchHomeFeed() {
        Utils.showShortToast(this, "poop the shutte");
        startService(new Intent(HomeActivity.this, FetchHomeFeedService.class));
    }

    public void launchComposeTweetView(View view) {
        startActivityForResult(new Intent(HomeActivity.this, ComposeTweetActivity.class), REQUEST_CODE);
    }
}
