package com.codepath.apps.motwitter.models;

import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.codepath.apps.motwitter.helpers.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by koulmomo on 12/7/15.
 */
public class Tweet extends Model {
    public long id;

    public String mClientId;

    public String text;

    public TwitterUser user;

    public int favouritesCount;

    public boolean favorited;

    public long retweetCount;

    public boolean retweeted;

    public String createdAt;

    public Tweet(JSONObject json) {
        try {
            id = json.getLong("id");
            text = json.getString("text");
            user = new TwitterUser(json.getJSONObject("user"));
            favorited = json.getBoolean("favorited");
            createdAt = json.getString("created_at");
            retweetCount = json.getLong("retweet_count");
            retweeted = json.getBoolean("retweeted");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Tweet() {
        mClientId = "MoTwitterApp:" + UUID.randomUUID();
    }

    public static Tweet fromJSON(JSONObject json) {
        return new Tweet(json);
    }

    public static List<Tweet> fromJSON(JSONArray tweetsJSON) {
        List<Tweet> tweets = new ArrayList<>(tweetsJSON.length());

        for (int i = 0; i < tweetsJSON.length(); i++) {
            try {
                tweets.add(new Tweet(tweetsJSON.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return tweets;
    }

    public String getRelativeCreatedAt() {
        if (TextUtils.isEmpty(createdAt)) {
            return "now";
        }
        return Utils.getRelativeTimeAgo(createdAt);
    }
}
