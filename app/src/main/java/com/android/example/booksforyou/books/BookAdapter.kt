package com.android.example.booksforyou.books

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import com.android.example.booksforyou.MainActivity
import com.android.example.booksforyou.R
import com.android.example.booksforyou.books
import com.android.example.booksforyou.navigation.YourBooks
import java.text.SimpleDateFormat
import java.util.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.internal.ContextUtils.getActivity


internal class BookAdapter(
    private val context: Context,
    private var thisBooks: MutableList<Book>,
    private val viewModel: BookViewModel
) : BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int {
        return thisBooks.size
    }

    override fun getItem(position: Int): Any? {
        return thisBooks[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var updatedView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (updatedView == null) {
            updatedView = layoutInflater!!.inflate(R.layout.book_item, null)
        }
        val coverImg = updatedView!!.findViewById(R.id.cover_img) as ImageView
        val title = updatedView.findViewById(R.id.book_title) as TextView
        val author = updatedView.findViewById(R.id.book_author) as TextView
        val info = updatedView.findViewById(R.id.book_info) as TextView
        val deleteBookBtn = updatedView.findViewById(R.id.delete_button) as Button
        val finishBook = updatedView.findViewById(R.id.finish_button) as Button

        deleteBookBtn.setOnClickListener {
            deleteBook(position)
        }

        finishBook.setOnClickListener {
            finishBook(position)
        }

        BitmapFactory.decodeFile(thisBooks[position].photoLink, BitmapFactory.Options())
            ?.also { bitmap ->
                coverImg.setImageBitmap(bitmap)
            }
        title.text = thisBooks[position].name
        author.text = thisBooks[position].authorName
        var info_text = ""
        if (thisBooks[position].type == "finished") {
            info_text += "Finished at: "
            finishBook.visibility = View.GONE
            finishBook.isClickable = false

        } else {
            info_text += "Started at: "
        }

        info_text += thisBooks[position].date + "\nTotal pages: " + thisBooks[position].noPages

        info.text = info_text



        return updatedView
    }

    private fun finishBook(position: Int) {
        if (thisBooks.size > position && thisBooks[position].type != "finished") {
            // we have adapter for in progress books
            //remove the book from thisBooks
            val book = thisBooks[position]
            viewModel.finishBook(book.name, book.authorName)

            val builder = AlertDialog.Builder(context)
            builder.setMessage("You have finished " + book.name + " book!")
                .setTitle("Congratulations!")
            builder.setNegativeButton(
                "Ok"
            ) { dialog, _ ->

                dialog.dismiss()

            }
            builder.show()

        }

    }

    fun getItems(): MutableList<Book> {
        return thisBooks
    }

    private fun deleteBook(position: Int) {
        //remove book with given position from array
        if (thisBooks.size > position) {
            viewModel.deleteBook(
                thisBooks[position].name, thisBooks[position].authorName,
                thisBooks[position].type
            )

        }
    }
}