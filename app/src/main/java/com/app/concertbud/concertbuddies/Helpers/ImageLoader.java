package com.app.concertbud.concertbuddies.Helpers;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.app.concertbud.concertbuddies.CustomUI.AdjustableImageView;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;


/**
 * Created by hungnguyen on 2/27/18.
 */
public class ImageLoader {

    public static void loadSimpleCircleImage(ImageView view, String url, final ProgressBar progressBar) {
        if (view == null) {
            return;
        }

        BaseApplication.getInstance().getGlide()
                .load(url)
                .apply(RequestOptions.centerCropTransform())
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
                .into(view);
    }

    public static void loadCircleAdjustImageFromURI(AdjustableImageView view, Uri uri, final ProgressBar progressBar) {
        if (view == null) {
            return;
        }

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
                .into(view);
    }
}
