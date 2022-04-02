package com.android.example.booksforyou.network
import com.squareup.moshi.Json

class NYTBook(
    @Json(name = "num_results")
     val numResults: Int,
    @Json(name = "results")
    val  results: Results

    ) {

    }

data class Results(
    @Json(name = "published_date")
    val publishedDate:String,
    @Json(name = "lists")
    val lists: List<ApiList>){
}

data class ApiList(
    @Json(name = "list_id")
    val listId: Int,
    @Json(name = "list_name")
    val listName: String,
    @Json(name = "books")
    val books: List<ApiBook>
){
}

data class ApiBook(
    @Json(name="title") val name: String,
    @Json(name="author") val author: String,
    @Json(name="description") val description: String,
    @Json(name="book_image") val img : String
){

}
