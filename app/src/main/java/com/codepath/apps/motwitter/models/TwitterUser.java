package com.codepath.apps.motwitter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by koulmomo on 12/7/15.
 */
public class TwitterUser extends Model {
    public long id;

    public String screenName;

    public String name;

    public String profileImageUrl;

    public boolean followRequestSent;

    public boolean following;

    public boolean verified;

    public final static char AT_CHAR = '@';

    public TwitterUser(JSONObject json) {
        try {
            id = json.getLong("id");
            name = json.getString("name");
            screenName = json.getString("screen_name");
            profileImageUrl = json.getString("profile_image_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static TwitterUser fromJson(JSONObject json) {
        return new TwitterUser(json);
    }

    public String getScreenNameForDisplay() {
        return AT_CHAR + screenName;
    }
}
