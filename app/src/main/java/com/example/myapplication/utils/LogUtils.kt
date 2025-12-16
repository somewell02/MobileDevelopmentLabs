package com.example.myapplication.utils

import android.util.Log

object LogUtils {
    private const val TAG = "MessengerApp"
    
    // Existing methods
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

    fun logViewModelCreated(viewModelName: String) {
        Log.d(TAG, "ViewModel Created: $viewModelName")
    }
    
    fun logViewModelDestroyed(viewModelName: String) {
        Log.d(TAG, "ViewModel Destroyed: $viewModelName")
    }
    
    fun logDataChange(viewModelName: String, fieldName: String, oldValue: Any?, newValue: Any?) {
        Log.d(TAG, "Data Change in $viewModelName: $fieldName changed from '$oldValue' to '$newValue'")
    }
}