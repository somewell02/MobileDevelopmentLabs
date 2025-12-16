package com.example.myapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.utils.LogUtils

abstract class BaseFragment : Fragment() {
    
    abstract fun getFragmentName(): String
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.logFragmentCreated(getFragmentName())
    }
    
    override fun onDestroy() {
        super.onDestroy()
        LogUtils.logFragmentDestroyed(getFragmentName())
    }
}