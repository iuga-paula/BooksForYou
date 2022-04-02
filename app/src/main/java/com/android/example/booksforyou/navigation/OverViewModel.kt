package com.android.example.booksforyou.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.example.booksforyou.network.ApiBook
import com.android.example.booksforyou.network.BooksApi
import com.android.example.booksforyou.network.NYTBook
import kotlinx.coroutines.launch


class OverViewModel: ViewModel() {
    private val _status = MutableLiveData<String>() // internal stores req status
    val status: LiveData<String> = _status //external

    private val _results = MutableLiveData<List<ApiBook>>()
    val results: LiveData<List<ApiBook>> = _results


    init {
        getBooksInfo()
    }

    private fun getBooksInfo() {
        try {
            viewModelScope.launch {
                _status.value = "Loading books..."
                val listResult = BooksApi.retrofitService.getBooks()
                _results.value = listResult.results.lists[0].books + listResult.results.lists[1].books
                _status.value = "Done: ${listResult.results.lists[0].books.size} results"
            }
        }
        catch (e: Exception) {
            _status.value = "Error: ${e.message}"
        }
    }


}