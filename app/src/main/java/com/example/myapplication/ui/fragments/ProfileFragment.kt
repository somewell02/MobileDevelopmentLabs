package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.data.repositories.UserRepositoryImpl
import com.example.myapplication.ui.viewmodels.ProfileViewModel
import com.example.myapplication.ui.viewmodels.ProfileViewModelFactory

class ProfileFragment : BaseFragment() {
    
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(UserRepositoryImpl(requireContext()))
    }
    
    override fun getFragmentName(): String = "ProfileFragment"
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Set default static data
        binding.tvProfileEmail.text = "user@example.com"
        binding.tvProfileUserId.text = "default_user"
        binding.ivProfileAvatar.setImageResource(R.drawable.ic_launcher_foreground)
        
        setupObservers()
        setupEditTextListeners()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    private fun setupObservers() {
        viewModel.userName.observe(viewLifecycleOwner) { name ->
            if (binding.etProfileName.text.toString() != name) {
                binding.etProfileName.setText(name)
            }
        }

        viewModel.userStatus.observe(viewLifecycleOwner) { status ->
            if (binding.etProfileStatus.text.toString() != status) {
                binding.etProfileStatus.setText(status)
            }
        }
    }
    
    private fun setupEditTextListeners() {
        binding.etProfileName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val newName = s?.toString() ?: ""
                if (viewModel.userName.value != newName) {
                    viewModel.updateUserName(newName)
                }
            }
        })

        binding.etProfileStatus.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val newStatus = s?.toString() ?: ""
                if (viewModel.userStatus.value != newStatus) {
                    viewModel.updateUserStatus(newStatus)
                }
            }
        })
    }
}