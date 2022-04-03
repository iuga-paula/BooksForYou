package com.android.example.booksforyou.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.android.example.booksforyou.MainActivity
import com.android.example.booksforyou.R
import com.android.example.booksforyou.books
import com.android.example.booksforyou.books.Book
import com.android.example.booksforyou.books.BookAdapter
import com.android.example.booksforyou.books.BookComparator
import com.android.example.booksforyou.books.BookViewModel
import com.android.example.booksforyou.database.BooksApplication
import com.android.example.booksforyou.databinding.FragmentYourBooksBinding
import kotlin.properties.Delegates


class YourBooks : Fragment() {
    private val viewModel: BookViewModel by activityViewModels {
        BookViewModel.BookViewModelFactory(
            (activity?.application as BooksApplication).booksDatabase.databaseDao
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("FragmentBooks", "onCreateView")
        (activity as MainActivity).supportActionBar?.show()
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentYourBooksBinding>(
            inflater,
            R.layout.fragment_your_books, container, false
        )

        binding.addBooks.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_yourBooks_to_addBookDialog)
        }

         val gridFinishedView = binding.finishedBooks
         val gridInProgressView = binding.inProgressBooks

        viewModel.finishedBooks.observe(this.viewLifecycleOwner) { items ->
            items.let {
                books.setFinishedBooks(it)
                var newFinishedBooks = books.getFinishedBooks()
                if (books.getFinishedBooks().size > 0) {
                    binding.noFinishedBooks.visibility = View.GONE
                    //sort books
                    newFinishedBooks = books.getFinishedBooks().sortedWith(BookComparator).reversed() as MutableList<Book>

                }

                val finishedBooksAdapter = BookAdapter(activity as MainActivity, newFinishedBooks, viewModel)
                gridFinishedView.adapter = finishedBooksAdapter
            }
            }

        viewModel.inProgressBooks.observe(this.viewLifecycleOwner) { items ->
            items.let {
                books.setInProgressBooks(it)
                var newInProgressBooks = books.getInProgressBooks()
                if (books.getInProgressBooks().size > 0) {
                    setHasOptionsMenu(true)
                    binding.noInProgressBooks.visibility = View.GONE
                    newInProgressBooks = books.getInProgressBooks().sortedWith(BookComparator).reversed() as MutableList<Book>
                }
                else {
                    setHasOptionsMenu(false)
                }

                val inProgressBooksAdapter =  BookAdapter(activity as MainActivity, newInProgressBooks, viewModel)
                gridInProgressView.adapter = inProgressBooksAdapter
            }
        }

        return binding.root
    }

    // Creating our Share Intent
    private fun getShareIntent() : Intent {
       // val args = GameWonFragmentArgs.fromBundle(requireArguments())
        val shareIntent = Intent(Intent.ACTION_SEND)
        var text = "See what I am currently reading with BooksForYouApp:\n"
        val inProgressBooks = books.getInProgressBooks()
        for (book in inProgressBooks) {
           val bookText = "- ${book.name} by ${book.authorName}"
            text += bookText
        }
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, text)
        return shareIntent
    }

    // Starting an Activity with our new Intent
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    // Showing the Share Menu Item Dynamically
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)
        // check if the activity resolves
        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {
            // hide the menu item if it doesn't resolve
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    // Sharing from the Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }






}