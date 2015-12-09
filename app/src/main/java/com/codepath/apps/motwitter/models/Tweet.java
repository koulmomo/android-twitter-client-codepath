package com.codepath.apps.motwitter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by koulmomo on 12/7/15.
 */
public class Tweet extends Model {
    long id;

    String text;

    TwitterUser user;

    int favouritesCount;

    boolean favorited;

    int retweetCount;

    boolean retweeted;
}
