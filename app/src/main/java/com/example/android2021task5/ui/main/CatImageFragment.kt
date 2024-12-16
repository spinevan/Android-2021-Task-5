package com.example.android2021task5.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.android2021task5.IMG_URL_KEY
import com.example.android2021task5.databinding.FragmentCatImageBinding

class CatImageFragment : Fragment() {

    private var _binding: FragmentCatImageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CatImageViewModel by activityViewModels()

    private var imgUrl: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imgUrl = it.getString(IMG_URL_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(binding.root)
            .load(imgUrl)
            .into(binding.fullCatImage)

        binding.saveToGallaryBtn.setOnClickListener {
            if (!verifyPermissions()) {
                return@setOnClickListener
            }
            imgUrl?.let { it1 -> viewModel.saveImageToFile(it1, requireContext()) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun verifyPermissions(): Boolean {

        val permissionExternalMemory =
            context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE) }
        if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
            val storagePermissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(requireActivity(), storagePermissions, 1)
            return false
        }
        return true
    }
}
