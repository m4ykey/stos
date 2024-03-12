package com.m4ykey.stos.extensions.ui

import androidx.viewbinding.ViewBinding

interface UIConfigurator<T : ViewBinding> {
    fun setupUI(binding : T)
}