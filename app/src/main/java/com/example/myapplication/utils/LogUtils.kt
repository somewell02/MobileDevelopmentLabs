package com.example.myapplication.utils

import android.util.Log

object LogUtils {
    private const val TAG = "LifecycleTracker"
    
    fun logActivityCreated(activityName: String) {
        Log.d(TAG, "Activity created: $activityName")
    }
    
    fun logActivityDestroyed(activityName: String) {
        Log.d(TAG, "Activity destroyed: $activityName")
    }
    
    fun logFragmentCreated(fragmentName: String) {
        Log.d(TAG, "Fragment created: $fragmentName")
    }
    
    fun logFragmentDestroyed(fragmentName: String) {
        Log.d(TAG, "Fragment destroyed: $fragmentName")
    }
}