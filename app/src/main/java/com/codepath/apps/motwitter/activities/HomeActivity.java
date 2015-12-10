package com.codepath.apps.motwitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.codepath.apps.motwitter.MoTwitterApplication;
import com.codepath.apps.motwitter.R;
import com.codepath.apps.motwitter.adapters.TimelineAdapter;
import com.codepath.apps.motwitter.listeners.EndlessRecyclerViewScrollListener;
import com.codepath.apps.motwitter.models.Tweet;
import com.codepath.apps.motwitter.models.TwitterUser;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    private static final int COMPOSE_TWEET_REQUEST_CODE = 100;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case COMPOSE_TWEET_REQUEST_CODE:
                handleComposeTweetResult(resultCode, data);
                return;
        }
    }

    private void handleComposeTweetResult(int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                Tweet newTweet = new Tweet();
                newTweet.text = data.getStringExtra("tweetText");
                newTweet.user = (TwitterUser) data.getSerializableExtra("user");

                postNewTweet(newTweet);
                return;

        }
    }

    protected void postNewTweet(final Tweet newTweet) {
        // optimistic update
        mTweets.add(0, newTweet);
        mTimelineAdapter.notifyItemInserted(0);
        mTimelineRV.smoothScrollToPosition(0);

        MoTwitterApplication.getRestClient().postNewTweet(newTweet.text, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                int previousTweetIndex = mTweets.indexOf(newTweet);
                mTweets.set(previousTweetIndex, Tweet.fromJSON(response));
                mTimelineAdapter.notifyItemChanged(previousTweetIndex);
            }
        });
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
        startActivityForResult(new Intent(HomeActivity.this, ComposeTweetActivity.class), COMPOSE_TWEET_REQUEST_CODE);
    }
}
