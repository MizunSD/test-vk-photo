package ru.teaz.testvkphoto.utils;

import android.util.Log;

public class Logger {
    private static final boolean DEBUG = true;
    private static final String TAG = "TestVkPhoto";

    public static void e(String message) {
       if (DEBUG) {
           Log.e(TAG, message);
       }
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void d(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void d(String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message);
        }
    }
}
