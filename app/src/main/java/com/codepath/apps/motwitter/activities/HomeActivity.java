package com.codepath.apps.motwitter.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.codepath.apps.motwitter.MoTwitterApplication;
import com.codepath.apps.motwitter.R;
import com.codepath.apps.motwitter.adapters.TimelineAdapter;
import com.codepath.apps.motwitter.helpers.Utils;
import com.codepath.apps.motwitter.listeners.EndlessRecyclerViewScrollListener;
import com.codepath.apps.motwitter.models.Tweet;
import com.codepath.apps.motwitter.services.FetchHomeFeedService;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;

//    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//        }
//    };
//
//
//    void setUpBroadcastReceiver() {
//        LocalBroadcastManager.getInstance(this)
//                .registerReceiver(mReceiver, new IntentFilter(FetchHomeFeedService.ACTION));
//    }

    private static final List<Tweet> mTweets = new ArrayList<>();
    private TimelineAdapter mTimelineAdapter;

    @Bind(R.id.rvTimeline)
    RecyclerView mTimelineRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTimelineAdapter = new TimelineAdapter(this, mTweets);
        mTimelineRV.setAdapter(mTimelineAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mTimelineRV.setLayoutManager(layoutManager);

        mTimelineRV.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                fetchMoreHomeContent(page);
            }
        });

        // TODO: switch to using an intent service to fetch data
        // setUpBroadcastReceiver();

        fetchHomeFeed();
    }

    void fetchMoreHomeContent(int page) {
        MoTwitterApplication.getRestClient().getHomeTimeline(page, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                mTweets.addAll(Tweet.fromJSON(response));
                mTimelineAdapter.notifyItemRangeInserted(mTimelineAdapter.getItemCount(), mTweets.size() - 1);

            }
        });
    }

    void fetchHomeFeed() {
        MoTwitterApplication.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // Utils.showShortToast(HomeActivity.this, response.toString());
                    mTweets.clear();
                    mTweets.addAll(Tweet.fromJSON(response));
                    mTimelineAdapter.notifyDataSetChanged();
                    super.onSuccess(statusCode, headers, response);
                }
            });
    }

    public void launchComposeTweetView(View view) {
        startActivityForResult(new Intent(HomeActivity.this, ComposeTweetActivity.class), REQUEST_CODE);
    }
}
