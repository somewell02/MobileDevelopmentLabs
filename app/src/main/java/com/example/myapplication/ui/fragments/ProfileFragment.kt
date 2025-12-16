package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.data.models.UserProfile

class ProfileFragment : BaseFragment() {
    
    override fun getFragmentName(): String = "ProfileFragment"
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val userProfile = UserProfile(
            id = "somewell",
            name = "Samvel Grigoryan",
            email = "samvel121202@mail.ru",
            status = "Online"
        )
        
        val avatarImageView = view.findViewById<ImageView>(R.id.iv_profile_avatar)
        val nameTextView = view.findViewById<TextView>(R.id.tv_profile_name)
        val emailTextView = view.findViewById<TextView>(R.id.tv_profile_email)
        val statusTextView = view.findViewById<TextView>(R.id.tv_profile_status)
        val userIdTextView = view.findViewById<TextView>(R.id.tv_profile_user_id)
        
        nameTextView.text = userProfile.name
        emailTextView.text = userProfile.email
        statusTextView.text = userProfile.status
        userIdTextView.text = userProfile.id
        avatarImageView.setImageResource(R.drawable.ic_launcher_foreground)
    }
}