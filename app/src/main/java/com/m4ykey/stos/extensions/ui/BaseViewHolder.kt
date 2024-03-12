package com.m4ykey.stos.extensions.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(
    private val view : View,
    private val listener: OnItemClickListener<T>? = null
) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item : T)

    abstract fun getItem(position : Int) : T

    init {
        listener?.let { view.setOnClickListener {
            listener.onItemClick(bindingAdapterPosition, getItem(bindingAdapterPosition))
        } }
    }

}