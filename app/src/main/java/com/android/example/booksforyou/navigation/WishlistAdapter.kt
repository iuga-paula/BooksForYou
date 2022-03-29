package com.android.example.booksforyou.navigation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.android.example.booksforyou.R
import com.android.example.booksforyou.wishlist
import java.util.*
import kotlin.collections.ArrayList

class WishlistAdapter(private val mList: MutableList<WishlistItemViewHolder>):
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>(), Filterable {

    var filteredWishList:MutableList<WishlistItemViewHolder> = mutableListOf()

    init {
        filteredWishList = mList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { //  sets the views to display the items.
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_wishlist, parent, false)

        return ViewHolder(view)
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val bookInfo: TextView = itemView.findViewById(R.id.book_name)
        val observations: TextView = itemView.findViewById(R.id.wish_text)
        val img: ImageView = itemView.findViewById(R.id.quality_image)
        val deleteButton: Button = itemView.findViewById(R.id.delete_wish)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = filteredWishList[position] //mList[position]
        holder.bookInfo.text = currentItem.bookInfo
        holder.observations.text = currentItem.obs
        holder.img.setImageResource(R.drawable.ic_baseline_menu_book_24)
        holder.deleteButton.setOnClickListener{
            deleteWish(position)
        }


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
       // return mList.size
        return filteredWishList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                   filteredWishList = mList
                } else {
                    val resultList :MutableList<WishlistItemViewHolder> = mutableListOf()
                    for (wish in filteredWishList) {
                        if (wish.bookInfo.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))
                            || wish.obs.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(wish)
                        }
                    }
                    filteredWishList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredWishList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredWishList = results?.values as MutableList<WishlistItemViewHolder>
                notifyDataSetChanged()
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteWish(position: Int) {
        if(mList.size > position) {
            mList.removeAt(position)
        }
        if(filteredWishList.size > position) {
            filteredWishList.removeAt(position)
        }
        wishlist.removeWish(position)
        notifyDataSetChanged()

    }



}