package com.xys.accessibilitydemo;

import android.annotation.SuppressLint;
import android.preference.PreferenceManager;
import android.view.accessibility.AccessibilityEvent;

import com.xys.accessibilitydemo.utils.BaseAccessibilityService;
import com.xys.accessibilitydemo.utils.L;
import com.xys.accessibilitydemo.utils.PowerUtil;

import java.util.Locale;

/**
 * 自动安装
 * <p>
 * Created by xuyisheng on 16/12/10.
 */

public class MyAccessibilityService extends BaseAccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        super.onAccessibilityEvent(event);
        L.e(event.toString());

        String eventText = null;
        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventText = "TYPE_VIEW_CLICKED";
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "TYPE_VIEW_FOCUSED";
                break;
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                eventText = "TYPE_VIEW_LONG_CLICKED";
                break;
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                eventText = "TYPE_VIEW_SELECTED";
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                eventText = "TYPE_VIEW_TEXT_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                L.i("页面跳转");

                StringBuilder sb = new StringBuilder();
                sb
                        .append("======================================================").append("\n")
                        .append("action:").append(event.getAction()).append("\n")
                        .append("getEventTime:").append(event.getEventTime()).append("\n")
                        .append("getMovementGranularity:").append(event.getMovementGranularity()).append("\n")
                        .append("getPackageName:").append(event.getPackageName()).append("\n")
                        .append("getClassName:").append(event.getClassName()).append("\n")
                        .append("getSource:").append(event.getSource()).append("\n")
                        .append("getRecordCount:").append(event.getRecordCount()).append("\n")
                        .append("======================================================").append("\n")
                ;
                L.d(sb);
                eventText = "TYPE_WINDOW_STATE_CHANGED";
                break;
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                eventText = "TYPE_NOTIFICATION_STATE_CHANGED";
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_END";
                break;
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
                eventText = "TYPE_ANNOUNCEMENT";
                break;
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
                eventText = "TYPE_TOUCH_EXPLORATION_GESTURE_START";
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
                eventText = "TYPE_VIEW_HOVER_ENTER";
                break;
            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
                eventText = "TYPE_VIEW_HOVER_EXIT";
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                eventText = "TYPE_VIEW_SCROLLED";
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                eventText = "TYPE_VIEW_TEXT_SELECTION_CHANGED";
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                eventText = "TYPE_WINDOW_CONTENT_CHANGED";
                break;
        }

        L.w(eventText.toLowerCase(Locale.CHINA).replace("_", " ").replace("type", ""));

    }
}
