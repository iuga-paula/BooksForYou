package com.android.example.booksforyou.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android.example.booksforyou.R
import com.android.example.booksforyou.databinding.FragmentHelloBinding


class HelloFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHelloBinding>(inflater,
            R.layout.fragment_hello,container,false)

        binding.startJourney.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_helloFragment_to_yourBooks)
        }
        return binding.root
    }

}