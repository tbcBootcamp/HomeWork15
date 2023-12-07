package com.example.homework15.ui.fragments

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework15.adapter.ItemModelAdapter
import com.example.homework15.base.BaseFragment
import com.example.homework15.databinding.FragmentMessagesBinding
import com.example.homework15.viewModels.MessagesFragmentViewModel
import kotlinx.coroutines.launch


class MessagesFragment : BaseFragment<FragmentMessagesBinding>(FragmentMessagesBinding::inflate) {

    private val viewModel: MessagesFragmentViewModel by viewModels()
    private val adapter = ItemModelAdapter()


    override fun setUp() {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        observeViewModel()
    }

    override fun listeners() {
        binding.btnSearch.setOnClickListener {
            adapter.submitList(viewModel.search(binding.etSearch.text.toString()))
        }
    }

    override fun observeViewModel() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemModels.collect { contacts ->
                    adapter.submitList(contacts)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loading.collect { isLoading ->
                    if (isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { msg ->
                    if (msg.isNotEmpty()) {
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}