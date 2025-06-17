package com.m4ykey.stos.user.presentation

import com.m4ykey.stos.user.domain.model.User

data class UserState(
    val user : User? = null,
    val isLoading : Boolean = false,
    val errorMessage : String? = null,
    val selectedUrl : String? = null
)