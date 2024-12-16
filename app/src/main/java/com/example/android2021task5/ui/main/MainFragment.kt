package com.example.android2021task5.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android2021task5.IMG_URL_KEY
import com.example.android2021task5.R
import com.example.android2021task5.databinding.MainFragmentBinding
import com.example.android2021task5.interfaces.ICatImageListener
import com.example.android2021task5.ui.adapters.CatImageAdapter

class MainFragment : Fragment(), ICatImageListener {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val catImageAdapter = CatImageAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            catImagesRecycler.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = catImageAdapter
            }
            swiperefresh.isEnabled = false
        }
        initViewModelListeners()
        initViewDataObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun loadNextPage() {
        viewModel.loadNextPageCatImages()
    }

    override fun openImageFragment(imgUrl: String) {
        view?.findNavController()?.navigate(R.id.action_mainFragment_to_catImageFragment, bundleOf(IMG_URL_KEY to imgUrl))
    }

    private fun initViewDataObservers() {
        with(viewModel) {
            catImages.observe(
                viewLifecycleOwner,
                Observer {
                    it ?: return@Observer
                    catImageAdapter.submitList(it)
                }
            )

            isLoadingCats.observe(
                viewLifecycleOwner,
                Observer {
                    it ?: return@Observer
                    binding.swiperefresh.isRefreshing = it
                }
            )

            isInternetAvailable.observe(
                viewLifecycleOwner,
                Observer {
                    it ?: return@Observer
                    binding.noConnectFrame.isVisible = !it
                    binding.swiperefresh.isVisible = it
                }
            )
        }
    }

    private fun initViewModelListeners() {
        binding.refreshButton.setOnClickListener {
            if (viewModel.catsPage > 0) {
                viewModel.loadNextPageCatImages()
            } else {
                viewModel.initCatImages()
            }
        }
    }
}
