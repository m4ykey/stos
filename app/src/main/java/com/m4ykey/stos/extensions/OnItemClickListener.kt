package com.m4ykey.stos.extensions

interface OnItemClickListener<T> {
    fun onItemClick(position : Int, item : T)
}