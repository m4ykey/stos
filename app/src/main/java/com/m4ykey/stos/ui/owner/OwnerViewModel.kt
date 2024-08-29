package com.m4ykey.stos.ui.owner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.network.data.repository.OwnerRepository
import com.m4ykey.stos.ui.owner.uistate.OwnerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OwnerViewModel(private val repository: OwnerRepository) : ViewModel() {

    private val _owner = MutableStateFlow(OwnerUiState())
    val owner : StateFlow<OwnerUiState> = _owner.asStateFlow()

    suspend fun getOwnerById(ownerId : Int) = viewModelScope.launch {
        _owner.value = OwnerUiState(isLoading = true)
        try {
            repository.getOwnerById(ownerId).collect { owner ->
                _owner.value = OwnerUiState(owner = owner)
            }
        } catch (e : Exception) {
            _owner.value = OwnerUiState(isError = e.localizedMessage)
        }
    }

}