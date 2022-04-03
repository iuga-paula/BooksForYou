package com.android.example.booksforyou.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.android.example.booksforyou.MainActivity
import com.android.example.booksforyou.R
import com.android.example.booksforyou.database.BooksApplication
import com.android.example.booksforyou.database.UserWishlist
import com.android.example.booksforyou.databinding.FragmentAddBookDialogBinding
import com.android.example.booksforyou.databinding.FragmentAddWishBinding
import kotlinx.android.synthetic.main.fragment_add_wish.*

class AddWishDialog: Fragment() {
    private lateinit var binding: FragmentAddWishBinding
    private val viewModel: WishlistViewModel by activityViewModels {
        WishlistViewModel.WishlistViewModelFactory(
            (activity?.application as BooksApplication).booksDatabase.databaseDao
        )
    }

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

        binding.saveButton.setOnClickListener {
            addNewWish(it)
        }

        return binding.root
    }

    private fun addNewWish(view: View) {
        val author = binding.authorName.text.toString()
        val bookName = binding.bookName.text.toString()
        val observations = binding.observations.text.toString()

        var errorMsg = "Please fill the following inputs:\n"
        if (bookName.isBlank()) {
            errorMsg += "- book name\n"
        }
        if (observations.isBlank()) {
            errorMsg += "- observations\n"
        }
        if (errorMsg !== "Please fill the following inputs:\n") {
            Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show()
        } else {
            var bookInfoText = bookName
            if(author.isNotEmpty()) {
                bookInfoText += " - $author"

            }


            val newWish = WishlistItemViewHolder(bookInfoText, observations)
            val bundle = Bundle()
            bundle.putParcelable("newWish", newWish)


            viewModel.addNewWish(bookInfoText, observations)
            view.findNavController().navigate(R.id.action_addWishDialog_to_wishlist2, bundle)
        }
    }

}