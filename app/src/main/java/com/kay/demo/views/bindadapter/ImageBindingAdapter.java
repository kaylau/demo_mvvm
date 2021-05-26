package com.kay.demo.views.bindadapter;

import android.widget.ImageView;
import com.kay.demo.utils.glide.GlideUtils ;

import androidx.databinding.BindingAdapter;

/**
 * @Author: Administrator
 * @CreateDate: 2021/5/20 18:39
 * @Description:
 */
public class ImageBindingAdapter {

    @BindingAdapter(value = {"app:imgUrl"})
    public static void loadUrl(ImageView imageView, String url) {
        GlideUtils.load(imageView, url);
    }

    @BindingAdapter(value = {"app:imgUrl"})
    public static void loadUrl(ImageView imageView, int url) {
        GlideUtils.loadBitmapLocal(imageView, url);
    }
}
