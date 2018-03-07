package com.xys.accessibilitydemo.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Formatter;
import java.util.Locale;

/**
 * @Copyright © 2015 Sanbo Inc. All rights reserved.
 * @Description <pre>
 * Log统一管理类,提供功能：
 * 1.log工具类支持全部打印
 * 2.支持类似C的格式化输出或Java的String.format
 * 3.支持Java堆栈打印
 * 4.支持键入和不键入TAG(不键入tag,tag是sanbo)
 * 5.支持shell控制log是否打印
 * 6.格式化输出.
 *              </pre>
 * @Version: 2.0
 * @Create: 2015年6月18日 下午4:14:01
 * @Author: sanbo
 */
public class L {

    private L() {
    }


    private static final int JSON_INDENT = 2;
    // 是否打印bug，可以在application的onCreate函数里面初始化
    private static boolean isShowLog = true;
    // 是否接受shell控制打印
    private static boolean isShellControl = false;
    // 是否打印详细log
    private static boolean isNeedCallstackInfo = false;
    // 是否按照条形框输出
    private static boolean isNeedWrapper = false;
    //是否格式化展示,主要针对JSON
    private static boolean isFormat = false;

    // 默认tag
    private static String DEFAULT_TAG = "sanbo";
    // 规定每段显示的长度
    private static int LOG_MAXLENGTH = 2000;

    private static String content_title_begin = "╔══════════════════════════════════════════════════════════════════════════════════════════════";
    private static String content_title_info_callstack = "╔═══════════════════════════════════════════调用详情═════════ ═════════════════════════════════════";
    private static String content_title_info_log = "╔═══════════════════════════════════════════日志详情══════════════════════════════════════════════";
    private static String content_title_info_error = "╔═══════════════════════════════════════════异常详情══════════════════════════════════════════════";
    private static String content_title_end = "╚══════════════════════════════════════════════════════════════════════════════════════════════";

    // 类名(getClassName).方法名(getMethodName)[行号(getLineNumber)]
    private static String content_simple_callstack = "%s.%s[%d]";

    private static String content_line = "║ ";

    public static final class MLEVEL {
        public static final int VERBOSE = 0x1;
        public static final int DEBUG = 0x2;
        public static final int INFO = 0x3;
        public static final int WARN = 0x4;
        public static final int ERROR = 0x5;
        public static final int WTF = 0x6;
    }

    /**
     * 初始化接口
     *
     * @param showLog           是否展示log，默认展示
     * @param shellControl      是否使用shell控制log动态打印.默认不使用. shell设置方式：setprop log.tag.sanbo INFO
     *                          最后一个参数为log等级,可选项目：VERBOSE/DEBUG/INFO/WARN/ERROR/ASSERT
     * @param needWarpper       是否需要格式化输出
     * @param needCallStackInfo 是否需要打印详细的堆栈调用信息.
     * @param defaultTag        android logcat的tag一个意义,不设置默认的tag为"sanbo"
     */
    public static void init(boolean showLog, boolean shellControl, boolean needWarpper, boolean needCallStackInfo, String defaultTag) {
        isShowLog = showLog;
        isShellControl = shellControl;
        isNeedCallstackInfo = needCallStackInfo;
        if (defaultTag != null && defaultTag.length() > 0) {
            DEFAULT_TAG = defaultTag;
        }
    }

    public static void v(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                v(DEFAULT_TAG, (String) obj, null);
            } else if (obj instanceof StringBuffer) {
                v(DEFAULT_TAG, ((StringBuffer) obj).toString(), null);
            } else if (obj instanceof StringBuilder) {
                v(DEFAULT_TAG, ((StringBuilder) obj).toString(), null);
            } else if (obj instanceof Throwable) {
                v(DEFAULT_TAG, null, (Throwable) obj);
            } else {
                print(MLEVEL.VERBOSE, obj, null, null);
            }
        }
    }

    public static void d(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                d(DEFAULT_TAG, (String) obj, null);
            } else if (obj instanceof StringBuffer) {
                d(DEFAULT_TAG, ((StringBuffer) obj).toString(), null);
            } else if (obj instanceof StringBuilder) {
                d(DEFAULT_TAG, ((StringBuilder) obj).toString(), null);
            } else if (obj instanceof Throwable) {
                d(DEFAULT_TAG, null, (Throwable) obj);
            } else {
                print(MLEVEL.DEBUG, obj, null, null);
            }
        }
    }

    public static void w(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                w(DEFAULT_TAG, (String) obj, null);
            } else if (obj instanceof StringBuffer) {
                w(DEFAULT_TAG, ((StringBuffer) obj).toString(), null);
            } else if (obj instanceof StringBuilder) {
                w(DEFAULT_TAG, ((StringBuilder) obj).toString(), null);
            } else if (obj instanceof Throwable) {
                w(DEFAULT_TAG, null, (Throwable) obj);
            } else {
                print(MLEVEL.WARN, obj, null, null);
            }
        }
    }

    public static void i(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                i(DEFAULT_TAG, (String) obj, null);
            } else if (obj instanceof StringBuffer) {
                i(DEFAULT_TAG, ((StringBuffer) obj).toString(), null);
            } else if (obj instanceof StringBuilder) {
                i(DEFAULT_TAG, ((StringBuilder) obj).toString(), null);
            } else if (obj instanceof Throwable) {
                i(DEFAULT_TAG, null, (Throwable) obj);
            } else {
                print(MLEVEL.INFO, obj, null, null);
            }
        }
    }

    public static void e(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                e(DEFAULT_TAG, (String) obj, null);
            } else if (obj instanceof StringBuffer) {
                e(DEFAULT_TAG, ((StringBuffer) obj).toString(), null);
            } else if (obj instanceof StringBuilder) {
                e(DEFAULT_TAG, ((StringBuilder) obj).toString(), null);
            } else if (obj instanceof Throwable) {
                Log.e(DEFAULT_TAG, " throable");
                e(DEFAULT_TAG, null, (Throwable) obj);
            } else {
                print(MLEVEL.ERROR, obj, null, null);
            }
        }
    }

    public static void wtf(Object obj) {
        if (obj != null) {
            if (obj instanceof String) {
                wtf(DEFAULT_TAG, (String) obj, null);
            } else if (obj instanceof StringBuffer) {
                wtf(DEFAULT_TAG, ((StringBuffer) obj).toString(), null);
            } else if (obj instanceof StringBuilder) {
                wtf(DEFAULT_TAG, ((StringBuilder) obj).toString(), null);
            } else if (obj instanceof Throwable) {
                wtf(DEFAULT_TAG, null, (Throwable) obj);
            } else {
                print(MLEVEL.WTF, obj, null, null);
            }
        }
    }

    public static void i(String msg, Throwable e) {
        i(DEFAULT_TAG, msg, e);
    }

    public static void v(String msg, Throwable e) {
        v(DEFAULT_TAG, msg, e);
    }

    public static void w(String msg, Throwable e) {
        w(DEFAULT_TAG, msg, e);
    }

    public static void d(String msg, Throwable e) {
        d(DEFAULT_TAG, msg, e);
    }

    public static void e(String msg, Throwable e) {
        e(DEFAULT_TAG, msg, e);
    }

    public static void wtf(String msg, Throwable e) {
        wtf(DEFAULT_TAG, msg, e);
    }

    public static void v(String tag, Object obj, Throwable e) {
        if (obj != null) {
            if (obj instanceof String) {
                String src = (String) obj;
                try {
                    JSONObject oo = new JSONObject(src);
                    print(MLEVEL.VERBOSE, oo, e, tag);
                } catch (JSONException e1) {
                    try {
                        JSONArray arr = new JSONArray(src);
                        print(MLEVEL.VERBOSE, arr, e, tag);
                    } catch (JSONException e2) {
                        print(MLEVEL.VERBOSE, src, e, tag);
                    }
                }
            } else {
                if (e == null) {
                    print(MLEVEL.VERBOSE, obj, e, tag);
                } else {
                    print(MLEVEL.VERBOSE, obj, e, tag);
                }
            }
        } else {
            if (e != null) {
                print(MLEVEL.VERBOSE, null, e, tag);
            }
        }

    }

    public static void wtf(String tag, Object obj, Throwable e) {
        if (obj != null) {
            if (obj instanceof String) {
                String src = (String) obj;
                try {
                    JSONObject oo = new JSONObject(src);
                    print(MLEVEL.WTF, oo, e, tag);
                } catch (JSONException e1) {
                    try {
                        JSONArray arr = new JSONArray(src);
                        print(MLEVEL.WTF, arr, e, tag);
                    } catch (JSONException e2) {
                        print(MLEVEL.WTF, src, e, tag);
                    }
                }
            } else {
                if (e == null) {
                    print(MLEVEL.WTF, obj, e, tag);
                } else {
                    print(MLEVEL.WTF, obj, e, tag);
                }
            }
        } else {
            if (e != null) {
                print(MLEVEL.WTF, null, e, tag);
            }
        }
    }

    public static void d(String tag, Object obj, Throwable e) {
        if (obj != null) {
            if (obj instanceof String) {
                String src = (String) obj;
                try {
                    JSONObject oo = new JSONObject(src);
                    print(MLEVEL.DEBUG, oo, e, tag);
                } catch (JSONException e1) {
                    try {
                        JSONArray arr = new JSONArray(src);
                        print(MLEVEL.DEBUG, arr, e, tag);
                    } catch (JSONException e2) {
                        print(MLEVEL.DEBUG, src, e, tag);
                    }
                }
            } else {
                if (e == null) {
                    print(MLEVEL.DEBUG, obj, e, tag);
                } else {
                    print(MLEVEL.DEBUG, obj, e, tag);
                }
            }
        } else {
            if (e != null) {
                print(MLEVEL.DEBUG, null, e, tag);
            }
        }
    }

    public static void i(String tag, Object obj, Throwable e) {
        if (obj != null) {
            if (obj instanceof String) {

                String src = (String) obj;
                try {
                    JSONObject oo = new JSONObject(src);
                    print(MLEVEL.INFO, oo, e, tag);
                } catch (JSONException e1) {
                    try {
                        JSONArray arr = new JSONArray(src);
                        print(MLEVEL.INFO, arr, e, tag);
                    } catch (JSONException e2) {
                        print(MLEVEL.INFO, src, e, tag);
                    }
                }
            } else {
                if (e == null) {
                    print(MLEVEL.INFO, obj, e, tag);
                } else {
                    print(MLEVEL.INFO, obj, e, tag);
                }
            }
        } else {
            if (e != null) {
                print(MLEVEL.INFO, null, e, tag);
            }
        }
    }

    public static void w(String tag, Object obj, Throwable e) {
        if (obj != null) {
            if (obj instanceof String) {
                String src = (String) obj;
                try {
                    JSONObject oo = new JSONObject(src);
                    print(MLEVEL.WARN, oo, e, tag);
                } catch (JSONException e1) {
                    try {
                        JSONArray arr = new JSONArray(src);
                        print(MLEVEL.WARN, arr, e, tag);
                    } catch (JSONException e2) {
                        print(MLEVEL.WARN, src, e, tag);
                    }
                }
            } else {
                if (e == null) {
                    print(MLEVEL.WARN, obj, e, tag);
                } else {
                    print(MLEVEL.WARN, obj, e, tag);
                }
            }
        } else {
            if (e != null) {
                print(MLEVEL.WARN, null, e, tag);
            }
        }
    }

    public static void e(String tag, Object obj, Throwable e) {
        if (obj != null) {
            if (obj instanceof String) {
                String src = (String) obj;
                try {
                    JSONObject oo = new JSONObject(src);
                    print(MLEVEL.ERROR, oo, e, tag);
                } catch (JSONException e1) {
                    try {
                        JSONArray arr = new JSONArray(src);
                        print(MLEVEL.ERROR, arr, e, tag);
                    } catch (JSONException e2) {
                        print(MLEVEL.ERROR, src, e, tag);
                    }
                }
            } else {
                if (e == null) {
                    print(MLEVEL.ERROR, obj, e, tag);
                } else {
                    print(MLEVEL.ERROR, obj, e, tag);
                }
            }
        } else {
            if (e != null) {
                print(MLEVEL.ERROR, null, e, tag);
            }
        }
    }

    /******************************************************************************************/
    /********************************* 该部分是多参数设置的 ******************************************/
    /******************************************************************************************/
    public static void d(String format, Object... args) {
        try {
            StringBuilder sb = new StringBuilder();
            if (format.contains("%")) {
                format = new Formatter(Locale.getDefault()).format(format, args).toString();
                sb.append(format).append("\n");

            } else {
                if (args != null) {
                    if (!TextUtils.isEmpty(format)) {
                        // sb.append(format).append("\n");
                        DEFAULT_TAG = format;
                    }
                }
            }
            Throwable e = null;
            for (Object obj : args) {
                if (obj instanceof String) {
                    sb.append((String) obj).append("\n");
                } else if (obj instanceof JSONObject) {
                    sb.append(format((JSONObject) obj)).append("\n");
                } else if (obj instanceof JSONArray) {
                    sb.append(format((JSONArray) obj)).append("\n");
                } else if (obj instanceof Throwable) {
                    e = (Throwable) obj;
                } else {
                    sb.append(obj.toString()).append("\n");
                }
            }
            print(MLEVEL.DEBUG, sb.toString(), e, null);
        } catch (Exception e) {
        }
    }

    public static void wtf(String format, Object... args) {
        try {
            StringBuilder sb = new StringBuilder();
            if (format.contains("%")) {
                format = new Formatter(Locale.getDefault()).format(format, args).toString();
                sb.append(format).append("\n");

            } else {
                if (args != null) {
                    if (!TextUtils.isEmpty(format)) {
                        // sb.append(format).append("\n");
                        DEFAULT_TAG = format;
                    }
                }
            }
            Throwable e = null;
            for (Object obj : args) {
                if (obj instanceof String) {
                    sb.append((String) obj).append("\n");
                } else if (obj instanceof JSONObject) {
                    sb.append(format((JSONObject) obj)).append("\n");
                } else if (obj instanceof JSONArray) {
                    sb.append(format((JSONArray) obj)).append("\n");
                } else if (obj instanceof Throwable) {
                    e = (Throwable) obj;
                } else {
                    sb.append(obj.toString()).append("\n");
                }
            }
            print(MLEVEL.WTF, sb.toString(), e, null);
        } catch (Exception e) {
        }
    }

    public static void i(String format, Object... args) {
        try {
            StringBuilder sb = new StringBuilder();
            if (format.contains("%")) {
                format = new Formatter(Locale.getDefault()).format(format, args).toString();
                sb.append(format).append("\n");

            } else {
                if (args != null) {
                    if (!TextUtils.isEmpty(format)) {
                        // sb.append(format).append("\n");
                        DEFAULT_TAG = format;
                    }
                }
            }
            Throwable e = null;
            for (Object obj : args) {
                if (obj instanceof String) {
                    sb.append((String) obj).append("\n");
                } else if (obj instanceof JSONObject) {
                    sb.append(format((JSONObject) obj)).append("\n");
                } else if (obj instanceof JSONArray) {
                    sb.append(format((JSONArray) obj)).append("\n");
                } else if (obj instanceof Throwable) {
                    e = (Throwable) obj;
                } else {
                    sb.append(obj.toString()).append("\n");
                }
            }
            print(MLEVEL.INFO, sb.toString(), e, null);
        } catch (Exception e) {
        }
    }

    public static void w(String format, Object... args) {
        try {
            StringBuilder sb = new StringBuilder();
            if (format.contains("%")) {
                format = new Formatter(Locale.getDefault()).format(format, args).toString();
                sb.append(format).append("\n");
            } else {
                if (args != null) {
                    if (!TextUtils.isEmpty(format)) {
                        // sb.append(format).append("\n");
                        DEFAULT_TAG = format;
                    }
                }
            }
            Throwable e = null;
            for (Object obj : args) {
                if (obj instanceof String) {
                    sb.append((String) obj).append("\n");
                } else if (obj instanceof JSONObject) {
                    sb.append(format((JSONObject) obj)).append("\n");
                } else if (obj instanceof JSONArray) {
                    sb.append(format((JSONArray) obj)).append("\n");
                } else if (obj instanceof Throwable) {
                    e = (Throwable) obj;
                } else {
                    sb.append(obj.toString()).append("\n");
                }
            }
            print(MLEVEL.WARN, sb.toString(), e, null);

        } catch (Exception e) {
        }
    }

    public static void v(String format, Object... args) {
        try {

            StringBuilder sb = new StringBuilder();
            if (format.contains("%")) {
                format = new Formatter(Locale.getDefault()).format(format, args).toString();
                sb.append(format).append("\n");

            } else {
                if (args != null) {
                    if (!TextUtils.isEmpty(format)) {
                        // sb.append(format).append("\n");
                        DEFAULT_TAG = format;
                    }
                }
            }
            Throwable e = null;
            for (Object obj : args) {
                if (obj instanceof String) {
                    sb.append((String) obj).append("\n");
                } else if (obj instanceof JSONObject) {
                    sb.append(format((JSONObject) obj)).append("\n");
                } else if (obj instanceof JSONArray) {
                    sb.append(format((JSONArray) obj)).append("\n");
                } else if (obj instanceof Throwable) {
                    e = (Throwable) obj;
                } else {
                    sb.append(obj.toString()).append("\n");
                }
            }
            print(MLEVEL.VERBOSE, sb.toString(), e, null);

        } catch (Exception e) {
        }
    }

    public static void e(String format, Object... args) {
        try {
            StringBuilder sb = new StringBuilder();
            if (format.contains("%")) {
                format = new Formatter(Locale.getDefault()).format(format, args).toString();
                sb.append(format).append("\n");

            } else {
                if (args != null) {
                    if (!TextUtils.isEmpty(format)) {
                        // sb.append(format).append("\n");
                        DEFAULT_TAG = format;
                    }
                }
            }
            Throwable e = null;
            for (Object obj : args) {
                if (obj instanceof String) {
                    sb.append((String) obj).append("\n");
                } else if (obj instanceof JSONObject) {
                    sb.append(format((JSONObject) obj)).append("\n");
                } else if (obj instanceof JSONArray) {
                    sb.append(format((JSONArray) obj)).append("\n");
                } else if (obj instanceof Throwable) {
                    e = (Throwable) obj;
                } else {
                    sb.append(obj.toString()).append("\n");
                }
            }
            print(MLEVEL.ERROR, sb.toString(), e, null);

        } catch (Exception e) {
        }
    }

    /**
     * 转换json为字符串
     *
     * @param level 打印的log
     * @param obj   打印的消息体
     * @param e     打印的堆栈信息
     * @param tag   打印使用的临时tag
     */
    private static void print(int level, Object obj, Throwable e, String tag) {
        if (isShowLog) {
            try {
                String msg = "";
                if (obj != null) {
                    if (obj instanceof JSONObject) {
                        msg = format((JSONObject) obj);
//                        msg = ((JSONObject) obj).toString(JSON_INDENT);
                    } else if (obj instanceof JSONArray) {
                        msg = format((JSONArray) obj);
//                        msg = ((JSONArray) obj).toString(JSON_INDENT);
                    } else if (obj instanceof String) {
                        msg = (String) obj;
                    } else {
                        msg = obj.toString();
                    }
                } else {
                    msg = " the message is null";
                }
                print(level, msg, e, tag);
            } catch (Throwable ea) {
                L.e(ea);
            }

        }
    }

    /**
     * <pre>
     * 区别处理异常、超长log打印
     * log 结构：
     *  1.调试状态
     *      调用堆栈信息===log信息(包含抬头和格式化功能等)===异常信息(包含抬头和异常信息)===结尾
     *  2.正常状态
     *  开始===调用简述(包名.类名.方法名[行号])===log信息(包含抬头和格式化功能等)====异常信息(包含抬头和异常信息)===结尾
     * </pre>
     * <p>
     * // // // // //
     *
     * @param level 打印log等级
     * @param msg   打印log信息
     * @param e     打印异常信息
     * @param tag   打印使用的tag
     */
    private static void print(int level, String msg, Throwable e, String tag) {
        if (isShowLog) {
            tag = (TextUtils.isEmpty(tag) ? DEFAULT_TAG : tag);

            if (isNeedWrapper) {
                // begin
                if (isNeedCallstackInfo) {
                    realSendToCmd(level, tag, content_title_info_callstack, false);
                } else {
                    realSendToCmd(level, tag, content_title_begin, false);
                }
            }

            realSendToCmd(level, tag, getCallStackInfo(), false);
            if (!TextUtils.isEmpty(msg)) {
                if (isNeedWrapper) {
                    realSendToCmd(level, tag, content_title_info_log, false);
                }

                msg = prepareProcessMessage(msg);
                int strLength = msg.length();
                int start = 0;
                int end = LOG_MAXLENGTH;
                for (int i = 0; i < 100; i++) {
                    // 剩下的文本还是大于规定长度则继续重复截取并输出
                    if (strLength > end) {
                        realSendToCmd(level, tag, msg.substring(start, end), true);
                        start = end;
                        end = end + LOG_MAXLENGTH;
                    } else {
                        realSendToCmd(level, tag, msg.substring(start, strLength), true);
                        break;
                    }
                }
            } // end with msg print

            if (e != null) {
                String result = getStackTrace(e);
                if (!TextUtils.isEmpty(result)) {
                    if (isNeedWrapper) {

                        realSendToCmd(level, tag, content_title_info_error, false);
                    }
                    realSendToCmd(level, tag, result, true);
                }
            }
            if (isNeedWrapper) {
                // end
                realSendToCmd(level, tag, content_title_end, false);
            }
        }
    }

    /**
     * 字符串处理,说白了就是前面加双竖线
     *
     * @param logMsg
     * @return
     */
    private static String prepareProcessMessage(String logMsg) {
        StringBuilder sb = new StringBuilder();
        String ss[] = new String[]{};
        String temp = null;
        if (logMsg.contains("\n")) {
            ss = logMsg.split("\n");
            if (ss.length > 0) {
                sb = new StringBuilder();
                for (int i = 0; i < ss.length; i++) {
                    temp = ss[i];
                    if (isNeedWrapper) {
                        if (!temp.startsWith(content_line)) {
                            sb.append(content_line).append(temp);
                        }
                    } else {
                        sb.append(temp);
                    }

                    if (i != ss.length - 1) {
                        sb.append("\n");
                    }
                }
                logMsg = sb.toString();
            }
        } else if (logMsg.contains("\r")) {
            ss = logMsg.split("\r");
            if (ss.length > 0) {
                sb = new StringBuilder();
                for (int i = 0; i < ss.length; i++) {
                    temp = ss[i];

                    if (isNeedWrapper) {
                        if (!temp.startsWith(content_line)) {
                            sb.append(content_line).append(temp);
                        }
                    } else {
                        sb.append(temp);
                    }
                    if (i != ss.length - 1) {
                        sb.append("\r");
                    }
                }
                logMsg = sb.toString();
            }
        } else if (logMsg.contains("\r\n")) {
            ss = logMsg.split("\r\n");
            if (ss.length > 0) {
                sb = new StringBuilder();
                for (int i = 0; i < ss.length; i++) {
                    temp = ss[i];

                    if (isNeedWrapper) {
                        if (!temp.startsWith(content_line)) {
                            sb.append(content_line).append(temp);
                        }
                    } else {
                        sb.append(temp);
                    }

                    if (i != ss.length - 1) {
                        sb.append("\r\n");
                    }
                }
                logMsg = sb.toString();
            }
        } else if (logMsg.contains("\n\r")) {
            ss = logMsg.split("\n\r");
            if (ss.length > 0) {
                sb = new StringBuilder();
                for (int i = 0; i < ss.length; i++) {
                    temp = ss[i];
                    if (isNeedWrapper) {
                        if (!temp.startsWith(content_line)) {
                            sb.append(isNeedCallstackInfo ? (content_line + logMsg) : logMsg).append(temp);
                        }
                    } else {
                        sb.append(logMsg).append(temp);
                    }

                    if (i != ss.length - 1) {
                        sb.append("\n\r");
                    }
                }
                logMsg = sb.toString();
            }
        }
        return logMsg;
    }

    public static String getCallStackInfo() {
        Exception callStack = new Exception("debug_info call stack.");
        StringBuilder sb = new StringBuilder();
        StackTraceElement stackElement[] = Thread.currentThread().getStackTrace();
        // 现在文件
        boolean currentFile = false;
        // 现在文件多重调用
        boolean isKeeping = false;
        for (StackTraceElement ste : stackElement) {
            if (currentFile && !isKeeping) {
                break;
            }
            if (ste.getClassName().equals(L.class.getName())) {
                if (!currentFile) {
                    currentFile = true;
                }
                isKeeping = true;
                continue;
            } else {
                if (currentFile) {

                    if (isNeedWrapper) {
                        if (isNeedCallstackInfo) {
                            sb.append("║  文件名:    " + ste.getFileName()).append("\n")
                                    .append("║  类名:      " + ste.getClassName()).append("\n")
                                    .append("║  方法名:    " + ste.getMethodName()).append("\n")
                                    .append("║  行号:      " + ste.getLineNumber()).append("\n")
                                    .append("║  Native方法:" + (!ste.isNativeMethod() ? "不是" : "是")).append("\n")
                                    .append("║  调用堆栈详情:").append("\n").append(getStackTrace(callStack));
                        } else {
                            sb.append(content_line).append(String.format(content_simple_callstack, ste.getClassName(),
                                    ste.getMethodName(), ste.getLineNumber()));
                        }
                    }

                    isKeeping = false;
                    break;
                }
            }
        }
        currentFile = false;
        isKeeping = false;
        callStack = null;
        stackElement = null;
        return sb.toString();
    }

    /**
     * 真正打印数据. 支持命令行控制log展示,控制命令：setprop log.tag.sanbo log等级.
     * log等级：VERBOSE/DEBUG/INFO/WARN/ERROR/ASSERT
     *
     * @param level          log等级
     * @param tag            log的tag
     * @param logMsg         消息内容
     * @param needDoubleLine 行首需要双竖线
     */
    private static void realSendToCmd(int level, String tag, String logMsg, boolean needDoubleLine) {

        if (TextUtils.isEmpty(logMsg)) {
            return;
        }
        if (isNeedWrapper) {
            if (needDoubleLine) {
                if (!logMsg.startsWith(content_line)) {
                    logMsg = content_line + logMsg;
                }
            }
        }


        switch (level) {
            case MLEVEL.DEBUG:

                if (isShellControl) {
                    if (Log.isLoggable(tag, Log.DEBUG)) {
                        Log.d(tag, logMsg);
                    }
                } else {
                    Log.d(tag, logMsg);
                }

                break;
            case MLEVEL.INFO:
                if (isShellControl) {
                    if (Log.isLoggable(tag, Log.INFO)) {
                        Log.i(tag, logMsg);
                    }
                } else {
                    Log.i(tag, logMsg);
                }

                break;
            case MLEVEL.ERROR:
                if (isShellControl) {
                    if (Log.isLoggable(tag, Log.ERROR)) {
                        Log.e(tag, logMsg);
                    }
                } else {
                    Log.e(tag, logMsg);
                }
                break;
            case MLEVEL.VERBOSE:
                if (isShellControl) {
                    if (Log.isLoggable(tag, Log.VERBOSE)) {
                        Log.v(tag, logMsg);
                    }
                } else {
                    Log.v(tag, logMsg);
                }

                break;
            case MLEVEL.WARN:
                if (isShellControl) {
                    if (Log.isLoggable(tag, Log.WARN)) {
                        Log.w(tag, logMsg);
                    }
                } else {
                    Log.w(tag, logMsg);
                }

                break;
            case MLEVEL.WTF:
                if (isShellControl) {
                    if (Log.isLoggable(tag, Log.ASSERT)) {
                        Log.wtf(tag, logMsg);
                    }
                } else {
                    Log.wtf(tag, logMsg);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 将error转换成字符串
     */

    public static String getStackTrace(Throwable e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        StringBuilder sb = new StringBuilder();
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            String result = sw.toString();
            String[] ss = result.split("\n");
            for (int i = 0; i < ss.length; i++) {
                if (isNeedWrapper) {
                    sb.append(content_line).append(ss[i]);
                } else {
                    sb.append(ss[i]);
                }
                if (i != ss.length - 1) {
                    sb.append("\n");
                }
            }
        } catch (Throwable error) {
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (Throwable e1) {
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sb.toString();
    }

    /**
     * 格式化输出JSONArray
     *
     * @param arr
     * @return
     */
    public static String format(JSONArray arr) {
        if (arr != null) {
//            return isFormat ? format(arr.toString()) : arr.toString();

            try {
//                return isFormat ?     ((JSONObject) obj).toString(JSON_INDENT): obj.toString();
                return isFormat ? format(arr.toString()) : arr.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 格式化输出JSONObject
     *
     * @param obj
     * @return
     */
    public static String format(JSONObject obj) {

        if (obj != null) {
            try {
//                return isFormat ?     ((JSONObject) obj).toString(JSON_INDENT): obj.toString();
                return isFormat ? format(obj.toString()) : obj.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private static String SPACE = "    ";

    private static String format(String jsonStr) {
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char llast = '\0';
        char current = '\0';
        int indent = 0;

        for (int i = 0; i < jsonStr.length(); i++) {
            llast = last;
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '"':
                    // 更改.支持{"port":8080,"host":"30.30.142.48"}不换行问题
                    // 支持 {"host":"30.30.142.48,,,","port":8080}
                    if ((last == ',' && llast == '}') || (last == ',' && llast != ',')) {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    sb.append(current);
                    break;
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);

                    switch (last) {
                        case '\\':

                            break;
                        case ']':
                            // 支持JsonArray
                            sb.append('\n');
                            addIndentBlank(sb, indent);
                            break;
                        case '"':
                            // 支持json Value里多个,的. 解决不了问题,会出现空行
                            // if (llast != ':') {
                            // sb.append('\n');
                            // addIndentBlank(sb, indent);
                            // }
                            break;

                        default:
                            break;
                    }
                    break;
                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append(SPACE);
        }
    }

}