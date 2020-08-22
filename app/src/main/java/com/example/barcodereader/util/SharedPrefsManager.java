package com.example.barcodereader.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.barcodereader.room.model.User;

public class SharedPrefsManager {
    private static SharedPreferences sharedPreferences;
    private static SharedPrefsManager mInstance;
    private final String SHARED_PREFS_NAME = "shared_prefs_name";
    private static final String TAG = "SharedPrefsManager";

    private static final String KEY_USER = "key_user";

    private SharedPrefsManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefsManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefsManager(context);
        }
        return mInstance;
    }

    public User getCurrentUser() {
        return User.create(sharedPreferences.getString(KEY_USER, ""));
    }

    public void saveUser(String serializedUser) {
        sharedPreferences.edit().putString(KEY_USER, serializedUser).apply();
    }
}
