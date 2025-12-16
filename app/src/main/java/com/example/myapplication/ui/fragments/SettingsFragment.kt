package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentSettingsBinding
import com.example.myapplication.data.repositories.SettingsRepositoryImpl
import com.example.myapplication.ui.viewmodels.SettingsViewModel
import com.example.myapplication.ui.viewmodels.SettingsViewModelFactory

class SettingsFragment : BaseFragment() {
    
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(SettingsRepositoryImpl(requireContext()))
    }
    
    private var isUpdatingSwitch = false
    private var hasAppliedInitialTheme = false
    
    override fun getFragmentName(): String = "SettingsFragment"
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupThemeSwitch()
        observeThemeChanges()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun setupThemeSwitch() {
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (!isUpdatingSwitch && viewModel.isDarkTheme.value != isChecked) {
                viewModel.toggleTheme(isChecked)
                applyTheme(isChecked)
            }
        }
    }
    
    private fun observeThemeChanges() {
        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            isUpdatingSwitch = true
            binding.switchTheme.isChecked = isDark
            isUpdatingSwitch = false
            
            if (!hasAppliedInitialTheme) {
                applyTheme(isDark)
                hasAppliedInitialTheme = true
            }
        }
    }
    
    private fun applyTheme(isDark: Boolean) {
        try {
            val targetNightMode = if (isDark) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            
            val currentNightMode = AppCompatDelegate.getDefaultNightMode()
            if (currentNightMode != targetNightMode) {
                binding.root.post {
                    if (_binding != null) {
                        AppCompatDelegate.setDefaultNightMode(targetNightMode)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}