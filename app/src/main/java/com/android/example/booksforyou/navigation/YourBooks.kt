package com.android.example.booksforyou.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android.example.booksforyou.MainActivity
import com.android.example.booksforyou.R
import com.android.example.booksforyou.books
import com.android.example.booksforyou.books.Book
import com.android.example.booksforyou.books.BookAdapter
import com.android.example.booksforyou.books.BookComparator
import com.android.example.booksforyou.databinding.FragmentYourBooksBinding
import kotlin.properties.Delegates


class YourBooks : Fragment() {
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
        val bookModel = arguments?.getParcelable<Book>("newBook")
        val newBook = bookModel?.let {
            Book(
                it.name, bookModel.authorName, bookModel.noPages,
                bookModel.type, bookModel.date, bookModel.photoLink
            )
        }
        newBook?.let {
            if (it.type == "finished") {
                books.addFinishedBook(it)
            } else {
                books.addInProgressBook(it)
            }
        }
        var finishedBooks = books.getFinishedBooks()
        var inProgressBooks = books.getInProgressBooks()

        if (finishedBooks.size > 0) {
            binding.noFinishedBooks.visibility = View.GONE
            //sort books
            finishedBooks = finishedBooks.sortedWith(BookComparator).reversed() as MutableList<Book>
        }
        if (inProgressBooks.size > 0) {
            binding.noInProgressBooks.visibility = View.GONE
            //sort
            inProgressBooks =
                inProgressBooks.sortedWith(BookComparator).reversed() as MutableList<Book>
        }


        val gridFinishedView = binding.finishedBooks
        val finishedBooksAdapter = BookAdapter(activity as MainActivity, finishedBooks)
        gridFinishedView.isVerticalScrollBarEnabled = false
        gridFinishedView.adapter = finishedBooksAdapter


        val gridInProgressView = binding.inProgressBooks
        val inProgressBooksAdapter = BookAdapter(activity as MainActivity, inProgressBooks)
        gridInProgressView.isVerticalScrollBarEnabled = false
        gridInProgressView.adapter = inProgressBooksAdapter

//        inProgressBooks: MutableList<Book> by Delegates.observable() {
//                property, oldValue, newValue ->
//            val newfinishedBooksAdapter = BookAdapter(activity as MainActivity, newValue)
//            gridFinishedView.adapter = newfinishedBooksAdapter
//        }

        Log.i("YourBooks", bookModel.toString())
        Log.i(
            "YourBooks", finishedBooks.size.toString() + " finished books: "
                    + finishedBooks.toString()
        )
        Log.i(
            "YourBooks", inProgressBooks.size.toString() + " in progress books: "
                    + inProgressBooks.toString()
        )


        if(inProgressBooks.size > 0) {
            setHasOptionsMenu(true)
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