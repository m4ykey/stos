package com.m4ykey.stos.ui.owner.uistate

import com.m4ykey.network.data.model.Owner

data class OwnerUiState(
    val isLoading : Boolean = false,
    val isError : String? = null,
    val owner : Owner? = null
)
