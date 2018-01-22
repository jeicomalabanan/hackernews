package com.propertyguru.hackernews.data.local;

import android.content.SharedPreferences;

public class SharedPrefImpl implements SharedPref {
    private SharedPreferences sharedPreferences;

    public SharedPrefImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void put(String key, int value) {
        sharedPreferences.edit().putInt(key, value).apply();
    }

    @Override
    public int get(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    @Override
    public void put(String key, float value) {
        sharedPreferences.edit().putFloat(key, value).apply();
    }

    @Override
    public float get(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    @Override
    public void put(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    @Override
    public boolean get(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    @Override
    public void put(String key, long value) {
        sharedPreferences.edit().putLong(key, value).apply();
    }

    @Override
    public long get(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    @Override
    public void put(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    @Override
    public String get(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    @Override
    public void delete(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    @Override
    public void deleteAll() {
        sharedPreferences.edit().clear().apply();
    }
}
