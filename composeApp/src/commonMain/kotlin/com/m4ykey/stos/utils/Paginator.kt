package com.m4ykey.stos.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Paginator<T>(
    private val loadPage : suspend (page : Int, sort : String) -> List<T>
) {

    private var currentSort = "hot"

    private var currentPage = 1
    private val _items = MutableStateFlow<List<T>>(emptyList())
    val items = _items.asStateFlow()

    suspend fun loadNextPage() {
        val newItems = loadPage(currentPage + 1, currentSort)
        if (newItems.isNotEmpty()) {
            _items.value += newItems
            currentPage++
        }
    }
}