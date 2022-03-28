package com.android.example.booksforyou.navigation
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.android.example.booksforyou.*
import com.android.example.booksforyou.BookAdapter
import com.android.example.booksforyou.databinding.FragmentYourBooksBinding


class YourBooks : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).supportActionBar?.show()
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentYourBooksBinding>(inflater,
            R.layout.fragment_your_books,container,false)

        binding.addBooks.setOnClickListener {view : View ->
            view.findNavController().navigate(R.id.action_yourBooks_to_addBookDialog)
        }
        val bookModel = arguments?.getParcelable<Book>("newBook")
        val newBook = bookModel?.let { Book(it.name, bookModel.authorName, bookModel.noPages,
            bookModel.type,  bookModel.date, bookModel.photoLink) }
        newBook?.let{
            if(it.type == "finished") {
                (activity as MainActivity).books.addFinishedBook(it)
            }
            else {
                (activity as MainActivity).books.addInProgressBook(it)
            }
        }
        val finishedBooks = (activity as MainActivity).books.getFinishedBooks()
        val inProgressBooks = (activity as MainActivity).books.getInProgressBooks()

        if (finishedBooks.size > 0) {
            binding.noFinishedBooks.visibility = View.GONE
        }
        if(inProgressBooks.size > 0) {
            binding.noInProgressBooks.visibility = View.GONE
        }

        val gridFinishedView = binding.finishedBooks
        val finishedBooksAdapter = BookAdapter(activity as MainActivity, finishedBooks)
        //gridFinishedView.isVerticalScrollBarEnabled = false
        gridFinishedView.adapter = finishedBooksAdapter


        val gridInProgressView = binding.inProgressBooks
        val inProgressBooksAdapter = BookAdapter(activity as MainActivity, inProgressBooks)
        //gridInProgressView.isVerticalScrollBarEnabled = false
        gridInProgressView.adapter = inProgressBooksAdapter


        Log.i("YourBooks", bookModel.toString())
        Log.i("YourBooks",  finishedBooks.size.toString() + " finished books: "
                +  finishedBooks.toString())
        Log.i("YourBooks",  inProgressBooks.size.toString() + " in progress books: "
                +  inProgressBooks.toString())

        return binding.root
    }


}