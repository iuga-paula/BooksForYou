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
import com.android.example.booksforyou.databinding.FragmentAddBookDialogBinding
import com.android.example.booksforyou.databinding.FragmentAddWishBinding

class AddWishDialog: Fragment() {
    private lateinit var binding: FragmentAddWishBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.hide()
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentAddWishBinding>(
            inflater,
            R.layout.fragment_add_wish, container, false
        )

        binding.cancelButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_addWishDialog_to_wishlist2)}

        return binding.root
    }

}