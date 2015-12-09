package com.codepath.apps.motwitter.services;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.codepath.apps.motwitter.helpers.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by koulmomo on 12/9/15.
 */
public class FetchHomeFeedService extends AbstractMoService {
    public static final String ACTION = "com.codepath.motwitter.services.FetchHomeFeedService";

    public static final int FETCH_HOME_FEED_START = 0;
    public static final int FETCH_HOME_FEED_FAIL = -1;
    public static final int FETCH_HOME_FEED_OK = 1;

    AsyncHttpClient mClient = new SyncHttpClient();

    public FetchHomeFeedService() {
        super("FetchHomeFeedService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String since_id = intent.getStringExtra("since_id");

        if (TextUtils.isEmpty(since_id)) {
            getTwitterClient().getHomeTimeline(mClient, createBaseParams(),
                    new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                            super.onSuccess(statusCode, headers, response);
                            Log.wtf("Poop", response.toString());
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                            Log.wtf("poop poop", response.toString());
                        }
                    });


            return;
        }

        getTwitterClient().getHomeTimelineSince(mClient, since_id, createBaseParams(),
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);
                    }
                });
    }
}
