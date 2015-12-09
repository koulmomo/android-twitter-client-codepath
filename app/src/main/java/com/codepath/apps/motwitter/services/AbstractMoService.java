package com.codepath.apps.motwitter.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.codepath.apps.motwitter.MoTwitterApplication;
import com.codepath.apps.motwitter.networking.TwitterClient;
import com.loopj.android.http.RequestParams;

/**
 * Created by koulmomo on 12/9/15.
 */
public abstract class AbstractMoService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AbstractMoService(String name) {
        super(name);
    }

    Intent setResult(Intent i, int resultCode) {
        i.putExtra("resultCode", resultCode);
        return i;
    }

    Intent createIntent(String action, int resultCode) {
        return setResult(new Intent(action), resultCode);
    }

    void broadcast(Intent i) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    RequestParams createBaseParams() {
        return new RequestParams("access_token", MoTwitterApplication.getRestClient().getToken());
    }

    TwitterClient getTwitterClient() {
        return MoTwitterApplication.getRestClient();
    }
}
