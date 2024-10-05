package com.m4ykey.stos.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.network.core.launchPaging
import com.m4ykey.network.data.repository.SearchRepository
import com.m4ykey.stos.ui.search.uistate.SearchUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel(
    private val repository : SearchRepository
) : ViewModel() {

    private val _search = MutableStateFlow(SearchUiState())
    val search : StateFlow<SearchUiState> = _search.asStateFlow()

    fun searchQuestions(inTitle : String?, tagged : String?) {
        launchPaging(
            scope = viewModelScope,
            source = { repository.searchQuestions(inTitle, tagged) },
            onDataCollected = { pagingData ->
                _search.value = SearchUiState(searchList = pagingData)
            }
        )
    }

}