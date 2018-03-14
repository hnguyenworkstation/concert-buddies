package com.app.concertbud.concertbuddies.Helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by hungnguyen on 2/14/18.
 */

public class AppUtils {
    public static boolean isGooglePlayServicesAvailable(Context context) {
        final GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int googlePlayServicesAvailable = apiAvailability.isGooglePlayServicesAvailable(context);
        return googlePlayServicesAvailable == ConnectionResult.SUCCESS;
    }

    public static void startNewActivity(@NonNull final Context context,
                                        @Nullable final Activity fromActivity,
                                        @NonNull final Class toActivity) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                context.startActivity(new Intent(fromActivity, toActivity));
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 0);
    }


    @SuppressLint("SimpleDateFormat")
    public static String getCurrentUTCTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("gmt"));

        return df.format(new Date());
    }

    public static void loadCircleImageFromUri(final Uri uri,
                                              final ImageView imageView,
                                              final ProgressBar progressBar) {
        BaseApplication.getInstance().getGlide()
                .load(uri)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                        return false;
                    }
                })
                .into(imageView);
    }

    public static void loadImageFromDrawable(final Drawable drawable,
                                             final ImageView imageView) {
        BaseApplication.getInstance().getGlide()
                .load(drawable)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(imageView);
    }


    public static void startNewActivityAndFinish(@NonNull final Context context,
                                                 @NonNull final Activity fromActivity,
                                                 @NonNull final Class toActivity) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                context.startActivity(new Intent(fromActivity, toActivity).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                fromActivity.finish();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 0);
    }

    public static void hideKeyboard(@NonNull final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    private static void replaceFont(String staticTypefaceFieldName,
                                    final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
