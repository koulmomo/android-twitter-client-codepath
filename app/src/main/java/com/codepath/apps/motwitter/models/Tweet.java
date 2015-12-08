package com.codepath.apps.motwitter.models;

import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by koulmomo on 12/7/15.
 */
@Table(name = "tweets")
public class Tweet extends Model {
    @Column(index = true)
    long id;

    @Column
    String text;

    TwitterUser user;

    int favouritesCount;

    @Column
    boolean favorited;

    @Column
    int retweetCount;

    @Column
    boolean retweeted;
}
