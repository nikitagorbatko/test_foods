package com.nikitagorbatko.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.nikitagorbatko.main.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModel()

    private val adapterDelegate = ListDelegationAdapter(
        categoryAdapterDelegate {
            val request = NavDeepLinkRequest.Builder
                .fromUri("android-app://com.nikitagorbatko.testfoods/navigation_dishes".toUri())
                .build()
            findNavController().navigate(request)
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerCategories.adapter = adapterDelegate
        observe()
        viewModel.getCategories()
    }


    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.categories.collect {
                adapterDelegate.items = it
                adapterDelegate.notifyDataSetChanged()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest {
                when (it) {
                    MainViewModel.State.LOADING -> {
                        binding.progressCategories.visibility = View.VISIBLE
                        binding.recyclerCategories.visibility = View.GONE
                        binding.textCategoriesError.visibility = View.GONE
                    }
                    MainViewModel.State.PRESENT -> {
                        binding.progressCategories.visibility = View.GONE
                        binding.recyclerCategories.visibility = View.VISIBLE
                        binding.textCategoriesError.visibility = View.GONE
                    }
                    MainViewModel.State.ERROR -> {
                        binding.progressCategories.visibility = View.GONE
                        binding.recyclerCategories.visibility = View.GONE
                        binding.textCategoriesError.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}