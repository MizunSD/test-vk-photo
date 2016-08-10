package ru.teaz.testvkphoto.utils;

import android.content.Context;

import com.vk.sdk.VKAccessToken;

public class SharedPreferencesManager {

    private static final String TOKEN_KEY = "token";

    public static void saveAccessToken(Context context, VKAccessToken token) {
        token.saveTokenToSharedPreferences(context, TOKEN_KEY);
    }

    public static VKAccessToken getAccessToken(Context context) {
        return VKAccessToken.tokenFromSharedPreferences(context, TOKEN_KEY);
    }

    public static void deleteAccessToken(Context context) {
        VKAccessToken.removeTokenAtKey(context, TOKEN_KEY);
    }
}
