package com.codepath.apps.motwitter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by koulmomo on 12/7/15.
 */
@Table(name = "users")
public class TwitterUser extends Model {
    @Column(index = true)
    long id;

    @Column(index = true)
    String screenName;

    String name;

    String profileImageUrl;

    boolean followRequestSent;

    boolean following;

    boolean verified;
}
