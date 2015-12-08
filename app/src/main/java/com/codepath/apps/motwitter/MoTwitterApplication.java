package com.codepath.apps.motwitter;

import android.content.Context;

import com.codepath.apps.motwitter.networking.TwitterClient;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     TwitterClient client = MoTwitterApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class MoTwitterApplication extends com.activeandroid.app.Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		MoTwitterApplication.context = this;
	}

	public static TwitterClient getRestClient() {
		return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, MoTwitterApplication.context);
	}
}