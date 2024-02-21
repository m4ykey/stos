package com.m4ykey.stos.extensions

import androidx.viewbinding.ViewBinding

interface UIConfigurator<T : ViewBinding> {
    fun setupUI(binding : T)
}