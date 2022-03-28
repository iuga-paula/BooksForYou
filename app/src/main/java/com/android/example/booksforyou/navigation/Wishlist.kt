package com.android.example.booksforyou.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android.example.booksforyou.MainActivity
import com.android.example.booksforyou.R
import com.android.example.booksforyou.databinding.FragmentAddWishBinding
import com.android.example.booksforyou.databinding.FragmentWishlistBinding


class Wishlist : Fragment() {
    private lateinit var binding: FragmentWishlistBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as MainActivity).supportActionBar?.show()
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentWishlistBinding>(
            inflater,
            R.layout.fragment_wishlist, container, false
        )
        binding.addBookButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_wishlist2_to_addWishDialog)
        }



        return binding.root
    }

}