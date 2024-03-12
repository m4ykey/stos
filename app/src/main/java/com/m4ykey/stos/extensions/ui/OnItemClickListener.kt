package com.m4ykey.stos.extensions.ui

interface OnItemClickListener<T> {
    fun onItemClick(position : Int, item : T)
}