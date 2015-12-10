package com.codepath.apps.motwitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.motwitter.MoTwitterApplication;
import com.codepath.apps.motwitter.R;
import com.codepath.apps.motwitter.helpers.Constants;
import com.codepath.apps.motwitter.helpers.Utils;
import com.codepath.apps.motwitter.models.Tweet;
import com.codepath.apps.motwitter.models.TwitterUser;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.phrase.Phrase;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ComposeTweetActivity extends AppCompatActivity {

    TwitterUser mUser;

    String mCurrentTweetText = "";

    @Bind(R.id.tvCharactersLeft)
    TextView mCharactersLeftTV;

    @Bind(R.id.ivTwitterProfilePic)
    ImageView mProfilePicIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);

        ButterKnife.bind(this);
        fetchCurrentUser();

        ((EditText) this.findViewById(R.id.etTweetInput)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mCurrentTweetText = s.toString();
                renderCharacterCountLeft();
            }
        });
    }

    public void renderCharacterCountLeft() {
        if (TextUtils.isEmpty(mCurrentTweetText)) {
            return;
        }

        mCharactersLeftTV.setText(String.valueOf(Constants.MAX_TWEET_LENGTH - mCurrentTweetText.length()));
    }

    public void renderUserProfilePic() {
        if (mUser == null || TextUtils.isEmpty(mUser.profileImageUrl)) {
            return;
        }

        Picasso.with(mProfilePicIV.getContext())
                .load(mUser.profileImageUrl)
                .fit()
                .into(mProfilePicIV);
    }

    public void fetchCurrentUser() {
        MoTwitterApplication.getRestClient().getCurrentUser(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                mUser = TwitterUser.fromJson(response);
                renderUserProfilePic();
            }
        });
    }

    public void tweet(View view) {
        String tweet = mCurrentTweetText;
        int length = tweet.length();

        if (length > Constants.MAX_TWEET_LENGTH) {
            Utils.showLongToast(this,
                    Phrase.from("Cannot submit tweet. Length of {length} is larger than {max}")
                            .put("length", length)
                            .put("max", Constants.MAX_TWEET_LENGTH)
                            .format()
                            .toString()
            );
            return;
        }

        Intent data = new Intent();
        data.putExtra("tweetText", tweet);
        data.putExtra("user", mUser);

        setResult(RESULT_OK, data);
        finish();
    }

    public void clear(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
