package com.m4ykey.stos.extensions.ui

import android.content.Context
import android.view.View
import android.widget.Toast

fun showToast(context : Context, text : String, duration : Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, text, duration).show()
}
