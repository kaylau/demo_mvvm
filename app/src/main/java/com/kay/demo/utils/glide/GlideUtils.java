package com.kay.demo.utils.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.kay.demo.GlobalApp;
import com.kay.demo.R;

/**
 * @Author: Administrator
 * @CreateDate: 2021/2/2 11:38
 * @Description:
 */
public class GlideUtils {

    private static Context getContext() {
        return GlobalApp.getContext().getApplicationContext();
    }

    /**
     * @param imageView
     * @param url
     * @param errorImage
     * @param sizeMultiplier 加载缩略图
     */
    public static void load(ImageView imageView, String url, Drawable errorImage, float sizeMultiplier) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.color.white) //占位图
                .error(errorImage) //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(getContext()).load(url).thumbnail(sizeMultiplier).apply(options).into(imageView);
    }


    public static void load(ImageView imageView, String url, int errorResId, int placeholderResId) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholderResId) //占位图
                .error(errorResId) //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void load(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(placeholderResId) //占位图
                .error(R.mipmap.ic_launcher_round) //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(getContext()).load(url).apply(options).into(imageView);
    }

    public static void loadBitmapLocal(ImageView imageView, int resId) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.color.white) //占位图
                .error(R.mipmap.ic_launcher_round) //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(getContext()).load(resId).apply(options).into(imageView);
    }

    /**
     * 加载背景图
     */
    public static void loadBackground(ImageView imageView, String url, int errorResId, int placeholderResId) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(placeholderResId) //占位图
                .error(errorResId) //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(getContext()).asBitmap().load(url).apply(options).into(new BitmapImageViewTarget(imageView) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                super.onResourceReady(resource, transition);
                BitmapDrawable drawable = new BitmapDrawable(getContext().getResources(), resource);
                imageView.setBackground(drawable);
            }
        });
    }

    /**
     * 加载圆形图
     */
    public static void loadCircleImage(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
//                .placeholder(R.color.white) //占位图
//                .error(R.color.white) //错误图
                // .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(getContext()).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆角图片
     */
    public static void loadRoundCircleImage(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
//                .placeholder(R.color.white) //占位图
//                .error(R.color.white) //错误图
                // .priority(Priority.HIGH)
                .transform(new RoundedCornersTransformation(45, 0, RoundedCornersTransformation.CornerType.ALL))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(getContext()).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆角图片-指定任意部分圆角（图片上、下、左、右四个角度任意定义）
     */
    public static void loadCustRoundCircleImage(ImageView imageView, String url, RoundedCornersTransformation.CornerType type) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.color.white) //占位图
//                .error(R.color.white) //错误图
                // .priority(Priority.HIGH)
                .transform(new RoundedCornersTransformation(45, 0, type))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(getContext()).load(url).apply(options).into(imageView);
    }

    /**
     * 加载模糊图片（自定义透明度）loadBlurImage
     */
    public static void loadBlurImage(ImageView imageView, String url, int blur) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.color.white)
//                .error(R.color.white)
                //.priority(Priority.HIGH)
                .transform(new BlurTransformation(blur))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(getContext()).load(url).apply(options).into(imageView);
    }

    /**
     * 加载灰度(黑白)图片（自定义透明度）
     */
    public static void loadBlackImage(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.color.white)
//                .error(R.color.white)
                //.priority(Priority.HIGH)
                .transform(new GrayscaleTransformation())
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(getContext()).load(url).apply(options).into(imageView);
    }

    /**
     * 加载动态图
     */
    public static void loadGifLocal(ImageView imageView, int res) {
        if (res > 0)
            Glide.with(getContext())
                    .asGif().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .load(res)
                    .into(imageView);
    }
}