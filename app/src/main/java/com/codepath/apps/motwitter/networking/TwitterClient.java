package com.codepath.apps.motwitter.networking;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "bHuaIUA3KTeba1IAUR5ur9IWO";       // Change this
	public static final String REST_CONSUMER_SECRET = "MUqUF7zlGLgwbZ6218qcL2q2pnULnwuGvqovVlEcl4WAMAwalZ"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://codepathtweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context,
				REST_API_CLASS,
				REST_URL,
				REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET,
				REST_CALLBACK_URL
		);
	}

    public String getToken() {
        return getRequestToken().toString();
    }

    public void getHomeTimeline(AsyncHttpClient pClient, RequestParams params, JsonHttpResponseHandler handler) {
		pClient.get(getHomeTimelineUrl(), params, handler);
    }

    public void getHomeTimeline(JsonHttpResponseHandler handler) {
        getHomeTimeline(getClient(), null, handler);
    }

    public void getHomeTimeline(int page, JsonHttpResponseHandler handler) {
        getClient().get(getHomeTimelineUrl(), new RequestParams("page", page), handler);
    }

	public String getHomeTimelineUrl() {
		return getApiUrl("statuses/home_timeline.json");
	}
}