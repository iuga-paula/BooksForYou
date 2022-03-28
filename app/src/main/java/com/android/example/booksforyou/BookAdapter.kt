package com.android.example.booksforyou

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

internal class BookAdapter(
    private val context: Context,
    private val books: MutableList<Book>): BaseAdapter(){
    private var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int {
        return books.size
    }
    override fun getItem(position: Int): Any? {
        return books[position]
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

        BitmapFactory.decodeFile(books[position].photoLink, BitmapFactory.Options())?.also { bitmap ->
            coverImg.setImageBitmap(bitmap)
        }
        title.text = books[position].name
        author.text = books[position].authorName
        var info_text = ""
        if (books[position].type == "finished") {
            info_text += "Finished at: "

        }
        else {
            info_text += "Started at: "
        }

        info_text += books[position].date + "\nTotal pages: " + books[position].noPages

        info.text = info_text

        return updatedView
    }
}