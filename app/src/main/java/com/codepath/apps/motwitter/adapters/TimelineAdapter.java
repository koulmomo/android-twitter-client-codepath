package com.codepath.apps.motwitter.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.motwitter.R;
import com.codepath.apps.motwitter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by koulmomo on 12/9/15.
 */
public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TweetViewHolder> {
    List<Tweet> mTweets;
    Activity mParentActivity;

    public TimelineAdapter(Activity parent, List<Tweet> tweets) {
        mParentActivity = parent;
        mTweets = tweets;
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tweetView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_tweet, parent, false);

        return new TimelineAdapter.TweetViewHolder(tweetView);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        render(holder, mTweets.get(position));
    }

    private void render(TweetViewHolder holder, Tweet tweet) {
        Picasso.with(holder.twitterProfilePicIV.getContext())
                .load(tweet.user.profileImageUrl)
                .placeholder(R.color.primary_light)
                .into(holder.twitterProfilePicIV);

        holder.nameTV.setText(tweet.user.name);
        holder.screenNameTV.setText(tweet.user.getScreenNameForDisplay());
        holder.createdAtTV.setText(tweet.getRelativeCreatedAt());
        holder.tweetTextTV.setText(tweet.text);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public static class TweetViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivTwitterProfilePic)
        public ImageView twitterProfilePicIV;

        @Bind(R.id.tvName)
        public TextView nameTV;

        @Bind(R.id.tvScreenName)
        public TextView screenNameTV;

        @Bind(R.id.tvCreatedAt)
        public TextView createdAtTV;

        @Bind(R.id.tvText)
        public TextView tweetTextTV;

        public TweetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
