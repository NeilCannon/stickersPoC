package org.fuzzyrobot.kpresent.log;


import org.fuzzyrobot.kpresent.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import de.akquinet.android.androlog.Constants;
import de.akquinet.android.androlog.LogHelper;

/**
 * Created by neil on 17/03/15.
 * Wrap de.akquinet.android.androlog.Log, to
 * - add method name to logging
 * - add a few more useful logging methods such as .d()
 * - exclude de.akquinet.android.androlog.Log from test coverage reports
 */
public class Log {
    protected static final List<String> CLASSNAME_TO_ESCAPE = new ArrayList<String>();
    public static final boolean LOG_ENABLED = BuildConfig.DEBUG;

    static {
        CLASSNAME_TO_ESCAPE.add("java.lang.Thread");
        CLASSNAME_TO_ESCAPE.add("dalvik.system.VMStack");
        CLASSNAME_TO_ESCAPE.add(de.akquinet.android.androlog.Log.class.getName());
        CLASSNAME_TO_ESCAPE.add(LogHelper.class.getName());
        CLASSNAME_TO_ESCAPE.add(Constants.class.getName());
        CLASSNAME_TO_ESCAPE.add(Log.class.getName());
    }

    public static void enter() { if (LOG_ENABLED) d("{"); }
    public static void exit() { if (LOG_ENABLED) d("}"); }
    public static void enter(Object o) { if (LOG_ENABLED) d(o, "{"); }
    public static void exit(Object o) { if (LOG_ENABLED) d(o, "}"); }

    public static void d() {
        if (LOG_ENABLED) d("-");
    }

    public static void d(Object obj) {
        if (LOG_ENABLED) d(String.valueOf(obj));
    }

    public static void d(Object msg, Object obj) {
        if (LOG_ENABLED) d(msg + " : " + obj);
    }

    public static void d(Object msg, Object obj, Object o2) {
        if (LOG_ENABLED) d(msg + " : " + obj + " : " + o2);
    }

    public static void d(Object msg, Object obj, Object o2, Object o3) {
        if (LOG_ENABLED) d(msg + " : " + obj + " : " + o2 + " : " + o3);
    }

    public static void d(Object msg, Object obj, Object o2, Object o3, Object o4) {
        if (LOG_ENABLED) d(msg + " : " + obj + " : " + o2 + " : " + o3 + " : " + o4);
    }

    public static void d(Object msg, Object obj, Object o2, Object o3, Object o4, Object o5) {
        if (LOG_ENABLED) d(msg + " : " + obj + " : " + o2 + " : " + o3 + " : " + o4 + " : " + o5);
    }

    public static void d(Object msg, Object obj, Object o2, Object o3, Object o4, Object o5, Object o6) {
        if (LOG_ENABLED) d(msg + " : " + obj + " : " + o2 + " : " + o3 + " : " + o4 + " : " + o5 + " ; " + o6);
    }

    public static int d(String msg) {
        if (!LOG_ENABLED) {
            return 0;
        }
            // This is a quick check to avoid the expensive stack trace reflection.
//        if (!activated) {
//            return 0;
//        }

        String caller = getCaller();
        if (caller != null) {
            return d(caller, msg);
        }
        return 0;
    }

    /**
     * Send a {@link #Constants.ERROR} log message.
     *
     * @param object
     *            The object logging this message.
     * @param msg
     *            The message you would like logged.
     */
    public static int e(Object object, String msg) {
        if (object != null) {
            return e(object.getClass().getName(), msg);
        }
        return 0;
    }

    /**
     * Send a {@link #Constants.ERROR} log message.
     *
     * @param msg
     *            The message you would like logged.
     */
    public static int e(String msg) {
        // This is a quick check to avoid the expensive stack trace reflection.
//        if (!activated) {
//            return 0;
//        }
        String caller = getCaller();
        if (caller != null) {
            return e(caller, msg);
        }
        return 0;
    }

    public static void e(Object obj) {
        e(String.valueOf(obj));
    }



    public static String getCaller() {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        if (stacks != null) {
            for (int i = 0; i < stacks.length; i++) {
                final StackTraceElement stack = stacks[i];
                String cn = stack.getClassName();
                if (cn != null && !CLASSNAME_TO_ESCAPE.contains(cn)) {
                    String simpleName = cn.substring(cn.lastIndexOf('.') + 1);
                    return simpleName + "." + stack.getMethodName();
                }
            }
        }
        return null;
    }

    public static int d(String tag, String msg) {
        if (!LOG_ENABLED) {
            return 0;
        }
        de.akquinet.android.androlog.Log.d("dm." + tag, String.valueOf(msg));
        return 0;
    }

    public static int e(String tag, String msg) {
        if (!LOG_ENABLED) {
            return 0;
        }
        de.akquinet.android.androlog.Log.e("dm." + tag, msg);
        return 0;
    }

}
