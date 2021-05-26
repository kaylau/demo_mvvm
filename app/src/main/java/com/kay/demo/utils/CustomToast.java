package com.kay.demo.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.kay.demo.R;


/**
 * @Author: Administrator
 * @CreateDate: 2021/2/1 15:29
 * @Description:
 */
public class CustomToast extends Toast {

    /**
     * 上下文对象
     */
    private static Context mContext;
    /**
     * Toast对象
     */
    private static CustomToast mToast;
    /**
     * Toast中显示的文本
     */
    private static TextView mShow;

    /**
     * 获得toast的单例实例，在Application初始化的时候使用
     *
     * @param context
     */
    public synchronized static  CustomToast getInstance(Context context) {
        if (mToast == null) {
            mContext = context;
            mToast = new CustomToast(mContext);
        }
        return mToast;
    }

    /**
     * 单例模式
     *
     * @param context
     */
    private CustomToast(Context context) {
        super(context);
        initView();
    }

    /**
     * 重新设置布局
     */
    private void initView() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int padding = ((int) displayMetrics.density * 15);
        mShow = new TextView(mContext);
        mShow.setBackgroundResource(R.drawable.bg_e6000000_r5);
//        //设置最小宽度
//        mShow.setMinWidth((int) (displayMetrics.density * 200));
//        //设置最大宽度
//        mShow.setMaxWidth((int) (displayMetrics.widthPixels - displayMetrics.density * 100));
        //设置内边距
        mShow.setPadding(padding, padding, padding, padding);

        mShow.setWidth((int) (displayMetrics.widthPixels - dip2px(mContext, 43 * 2)));

        //设置对其方式
        mShow.setGravity(Gravity.CENTER);
        //设置字体颜色
        mShow.setTextColor(mContext.getResources().getColor(R.color.white));
        //设置新展示样式
        setView(mShow);
    }

    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 显示toast
     *
     * @param messageId
     * @param duration  显示的时长
     */
    public void show(int messageId, int duration) {
        if (mShow == null) {
            return;
        }
        mShow.setText(messageId);
        mToast.setDuration(duration);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    /**
     * 显示toast
     *
     * @param message
     * @param duration 显示的时长
     */
    public void show(String message, int duration) {
        if (mShow == null) {
            return;
        }
        mShow.setText(message);
        mToast.setDuration(duration);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public void show(String message) {
        show(message, Toast.LENGTH_SHORT);
    }

    public void show(int messageId) {
        show(messageId, Toast.LENGTH_SHORT);
    }
}
