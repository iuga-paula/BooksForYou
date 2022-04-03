package com.android.example.booksforyou.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android.example.booksforyou.MainActivity
import com.android.example.booksforyou.R
import com.android.example.booksforyou.databinding.FragmentWishlistBinding
import com.android.example.booksforyou.wishlist
import androidx.fragment.app.activityViewModels
import com.android.example.booksforyou.database.BooksApplication
import com.android.example.booksforyou.database.BooksForYouDb


class Wishlist : Fragment() {
    private lateinit var binding: FragmentWishlistBinding
    private val viewModel: WishlistViewModel by activityViewModels {
        WishlistViewModel.WishlistViewModelFactory(
            ((activity as MainActivity).application as BooksApplication).booksDatabase.databaseDao
        )
    }
    @SuppressLint("NotifyDataSetChanged")
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

        context?.let { hideKeyboard(it) }

        val addedWish = arguments?.getParcelable<WishlistItemViewHolder>("newWish")
        /*val newWish = addedWish?.let {
            WishlistItemViewHolder(it.bookInfo, it.obs)
        }
        newWish?.let{
           wishlist.addWish(it)
      }*/

        val recicleView = binding.wishlist
        var adapter = WishlistAdapter(wishlist.getWishes(), viewModel)
        recicleView.adapter = adapter

        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let {
                wishlist.resetWishes(it)
                var msg = ""
                for(w in it) {
                    msg += "(${w.bookInfo}, ${w.obs}) "
                }
                Log.i("Wishlist", "Changed data: $msg")
                //adapter.submitList(wishlist.getWishes())
                //adapter.notifyDataSetChanged()
                adapter = WishlistAdapter(wishlist.getWishes(), viewModel)
                recicleView.adapter = adapter
            }
        }



        binding.searchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = binding.searchText.text
                adapter.filter.filter(text)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })





        return binding.root
    }

    fun hideKeyboard(context: Context) {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = (context as Activity).currentFocus ?: return
        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

}