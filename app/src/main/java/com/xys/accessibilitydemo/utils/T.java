package com.xys.accessibilitydemo.utils;

import android.content.Context;
import android.os.Handler;

import com.xys.accessibilitydemo.MyApplication;

/**
 * @Copyright © 2015 sanbo Inc. All rights reserved.
 * @Description: Toast同一管理类 多log堆积时候 显示最后一个
 * @Version: 1.0
 * @Create: 2015年6月28日 下午1:41:16
 * @Author: sanbo
 */
public class T {
    public static final int LENGTH_SHORT = android.widget.Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = android.widget.Toast.LENGTH_LONG;

    private static android.widget.Toast mToast;
    private static Handler mHandler = new Handler();
    private static int mDelay = 0;
    private static Runnable run = new Runnable() {
        public void run() {
            mToast.cancel();
        }
    };

    private T() {
    }

    public static boolean isShow = true;

    public static void show(CharSequence message) {
        if (isShow) {
            toast(MyApplication.getContext(), message, LENGTH_SHORT);
        }
    }

    public static void show(CharSequence message, int duration) {
        if (isShow) {
            toast(MyApplication.getContext(), message, duration);
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  anroid上下文
     * @param message  toast内容
     * @param duration toast显示时常
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow) {
            if (legitimateAble(context, duration)) {
                toast(context, message, duration);
            }
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  anroid上下文
     * @param resId    toast内容对应的ID
     * @param duration toast显示时常
     */
    public static void show(Context context, int resId, int duration) {
        if (isShow) {
            if (legitimateAble(context, duration)) {
                toast(context, context.getResources().getString(resId), duration);
            }
        }
    }

    /**
     * 校验toast传递参数是否正确
     *
     * @param context
     * @param duration
     * @return
     */
    private static boolean legitimateAble(Context context, int duration) {
        boolean res = false;
        // 1.check context
        if (context == null) {
            new Exception("The Context is Null!").printStackTrace();
            return res;
        } else {
            res = true;
        }
        // 2.process duration
        if (duration < 1) {
            duration = LENGTH_SHORT;
        } else {
            duration = LENGTH_LONG;
        }

        return res;
    }

    /**
     * 清理堆积消息，处理最新消息
     *
     * @param ctx
     * @param msg
     * @param duration
     */
    private static void toast(Context ctx, CharSequence msg, int duration) {
        if (mHandler != null) {
            mHandler.removeCallbacks(run);
        } else {
            new RuntimeException("Handler is Null!").printStackTrace();
        }

        // handler的duration不能直接对应Toast的常量时长，在此针对Toast的常量相应定义时长
        switch (duration) {
            case LENGTH_SHORT:// Toast.LENGTH_SHORT值为0，对应的持续时间大概为1s
                mDelay = 1000;
                break;
            case LENGTH_LONG:// Toast.LENGTH_LONG值为1，对应的持续时间大概为3s
                mDelay = 3000;
                break;
            default:
                break;
        }
        if (null != mToast) {
            mToast.setText(msg);
        } else {
            mToast = android.widget.Toast.makeText(ctx, msg, duration);
        }
        mHandler.postDelayed(run, mDelay);
        mToast.show();
    }
}