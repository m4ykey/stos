package com.m4ykey.stos.util

fun formatReputation(reputation : Int) : String {
    return when {
        reputation >= 1000000 -> "${reputation / 1000000}M"
        reputation >= 1000 -> {
            val remainder = reputation % 1000
            val decimal = if (remainder > 0) ".${remainder / 100}" else ""
            "${reputation / 1000}$decimal" + "k"
        }
        else -> "$reputation"
    }
}