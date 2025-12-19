package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.repositories.MessageRepositoryImpl
import com.example.myapplication.ui.adapters.MessagesAdapter
import com.example.myapplication.ui.viewmodels.NewsViewModel
import com.example.myapplication.ui.viewmodels.NewsViewModelFactory

class NewsFragment : BaseFragment() {
    
    private lateinit var recyclerView: RecyclerView
    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var buttonRefresh: Button
    private lateinit var progressBarLoading: ProgressBar
    private lateinit var textViewEmptyState: TextView
    private lateinit var newsViewModel: NewsViewModel
    
    override fun getFragmentName(): String = "NewsFragment"
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        setupRecyclerView()
        setupViewModel()
        observeViewModel()
        setupClickListeners()
    }

    private fun initializeViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerViewMessages)
        buttonRefresh = view.findViewById(R.id.buttonRefresh)
        progressBarLoading = view.findViewById(R.id.progressBarLoading)
        textViewEmptyState = view.findViewById(R.id.textViewEmptyState)
    }

    private fun setupRecyclerView() {
        messagesAdapter = MessagesAdapter()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = messagesAdapter
        }
    }

    private fun setupViewModel() {
        val messageRepository = MessageRepositoryImpl(requireContext())
        val viewModelFactory = NewsViewModelFactory(messageRepository)
        newsViewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
    }

    private fun observeViewModel() {
        newsViewModel.messages.observe(viewLifecycleOwner) { messages ->
            messagesAdapter.updateMessages(messages)

            if (messages.isEmpty()) {
                textViewEmptyState.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                textViewEmptyState.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }

        newsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                progressBarLoading.visibility = View.VISIBLE
                buttonRefresh.isEnabled = false
            } else {
                progressBarLoading.visibility = View.GONE
                buttonRefresh.isEnabled = true
            }
        }

        newsViewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                newsViewModel.clearError()
            }
        }
    }

    private fun setupClickListeners() {
        buttonRefresh.setOnClickListener {
            newsViewModel.refreshMessages()
        }
    }
}