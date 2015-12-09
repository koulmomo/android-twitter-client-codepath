package com.codepath.apps.motwitter.helpers;

import android.content.Context;
import android.text.format.DateUtils;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by koulmomo on 12/9/15.
 */
public class Utils {

    // @see: https://gist.github.com/nesquena/f786232f5ef72f6e10a7
    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";

        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(
                    dateMillis,
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS
            ).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public static void showToast(Context context, String text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public static void showLongToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_LONG);
    }

    public static void showShortToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }
}
