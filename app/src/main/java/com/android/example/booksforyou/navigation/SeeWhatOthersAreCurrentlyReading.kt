package com.android.example.booksforyou.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.example.booksforyou.R
import com.android.example.booksforyou.databinding.FragmentSeeWhatOthersAreCurrentlyReadingBinding
import androidx.fragment.app.viewModels
import com.android.example.booksforyou.network.BookGridAdapter

class SeeWhatOthersAreCurrentlyReading : Fragment() {
    lateinit var binding: FragmentSeeWhatOthersAreCurrentlyReadingBinding
    private val viewModel: OverViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentSeeWhatOthersAreCurrentlyReadingBinding>(
            inflater,
            R.layout.fragment_see_what_others_are_currently_reading, container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.photosGrid.adapter = BookGridAdapter()

        return binding.root
    }

}