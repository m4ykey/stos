package com.m4ykey.stos.core.data

fun String.htmlDecode() : String {
    return this.replaceHtmlEntities()
        .replaceDiacriticalMarks()
        .replaceSymbols()
}

private fun String.replaceHtmlEntities() : String {
    return this.replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .replace("&#39;", "'")
        .replace("&#160;", " ")
}

private fun String.replaceDiacriticalMarks() : String {
    return this.replace("&#243;", "ó")
        .replace("&#233;", "é")
        .replace("&#225;", "á")
        .replace("&#250;", "ú")
        .replace("&#231;", "ç")
        .replace("&#237;", "í")
        .replace("&#252;", "ü")
        .replace("&#196;", "Ä")
        .replace("&#214;", "Ö")
        .replace("&#220;", "Ü")
        .replace("&#169;", "©")
        .replace("&#8482;", "™")
        .replace("&#246;", "ö")
}

private fun String.replaceSymbols() : String {
    return this.replace("&copy;", "©")
        .replace("&reg;", "®")
        .replace("&euro;", "€")
        .replace("&pound;", "£")
        .replace("&yen;", "¥")
        .replace("&deg;", "°")
        .replace("&times;", "×")
        .replace("&divide;", "÷")
}