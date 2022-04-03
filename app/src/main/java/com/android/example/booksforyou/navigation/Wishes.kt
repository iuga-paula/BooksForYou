package com.android.example.booksforyou.navigation

import androidx.fragment.app.activityViewModels
import com.android.example.booksforyou.MainActivity
import com.android.example.booksforyou.database.BooksApplication
import com.android.example.booksforyou.database.UserWishlist


class Wishes {
    private var wishes: MutableList<WishlistItemViewHolder> = mutableListOf()
    init {
//        wishes = mutableListOf<WishlistItemViewHolder>(
//            WishlistItemViewHolder("Mademoiselle Coco", "Buy tomorrow from Carturesti Verona"),
//            WishlistItemViewHolder("Super genes", "ebook - on discount this week on Amazon")
//        )
    }

    fun getWishes(): MutableList<WishlistItemViewHolder> {
        return wishes
    }

    fun addWish(w: WishlistItemViewHolder) {
        wishes.add(w)
    }

    fun removeWish(position: Int) {
        if(wishes.size > position) {
            wishes.removeAt(position)
        }
    }

    fun resetWishes(userWishes: MutableList<UserWishlist>) {
        wishes = mutableListOf()
        for (wish in userWishes) {
            val newWish = WishlistItemViewHolder(wish.bookInfo, wish.obs)
            wishes.add(newWish)
        }

    }


}