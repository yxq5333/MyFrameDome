package com.yxq.myframdome.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.crazyhuskar.myandroidsdk.view.dialogfragment.MyDialogFragment;
import com.yxq.myframdome.R;

/**
 * @author CrazyHuskar
 * caeat at 2019-01-18  14:50
 */
public class ImageUtil {
    private static void load(Context context, ImageView imageView, Object url) {
        RequestOptions options = new RequestOptions()
                .priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(url).apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }

    public static void show(AppCompatActivity appCompatActivity, String path) {

        View view = View.inflate(appCompatActivity, R.layout.item_photo_view, null);
        PhotoView imageView = view.findViewById(R.id.photoView);
        View close = view.findViewById(R.id.iv_close);

//        PhotoView imageView = new PhotoView(appCompatActivity);
        imageView.enable();
        load(appCompatActivity, imageView, path);
        MyDialogFragment fragment = new MyDialogFragment.Builder(appCompatActivity.getSupportFragmentManager())
//                .setDialogView(imageView)
                .setDialogView(view)
                .setScreenWidthAspect(appCompatActivity, 1f)
                .setScreenHeightAspect(appCompatActivity, 1f)
                .setOnViewClickListener((bindViewHolder, view1, myDialogFragment) -> myDialogFragment.dismiss()).create().show();

        close.setOnClickListener(v -> fragment.dismiss());
    }
}
