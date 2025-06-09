package com.m4ykey.stos.core.data

fun String.htmlDecode() : String {
    return this.replaceHtmlEntities()
        .replaceDiacriticalMarks()
        .replaceSymbols()
        .replaceCheckboxes()
        .replaceWhitespace()
        .replaceEmojis()
}

private fun String.replaceCheckboxes() : String {
    return this.replace("- [ ]", "⬜")
        .replace("- [x]", "✅")
}

private fun String.replaceHtmlEntities() : String {
    return this.replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .replace("&#39;", "'")
        .replace("&#160;", " ")
        .replace("&nbsp;", " ")
        .replace("&hellip;", "…")
        .replace("&mdash;", "—")
        .replace("&ndash;", "–")
        .replace("&lsquo;", "‘")
        .replace("&rsquo;", "’")
        .replace("&ldquo;", "“")
        .replace("&rdquo;", "”")
        .replace("&apos;", "'")
        .replace("&bull;", "•")
        .replace("&middot;", "·")
        .replace("&laquo;", "«")
        .replace("&raquo;", "»")
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
        .replace("&#199;", "Ç")
        .replace("&#223;", "ß")
        .replace("&#228;", "ä")
        .replace("&#241;", "ñ")
        .replace("&#209;", "Ñ")
        .replace("&#232;", "è")
        .replace("&#234;", "ê")
        .replace("&#244;", "ô")
        .replace("&#249;", "ù")
        .replace("&#226;", "â")
        .replace("&#193;", "Á")
        .replace("&#201;", "É")
        .replace("&#205;", "Í")
        .replace("&#211;", "Ó")
        .replace("&#218;", "Ú")
        .replace("&#200;", "È")
        .replace("&#202;", "Ê")
        .replace("&#174;", "®")
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
        .replace("&plusmn;", "±")
        .replace("&sect;", "§")
        .replace("&para;", "¶")
        .replace("&micro;", "µ")
        .replace("&sup1;", "¹")
        .replace("&sup2;", "²")
        .replace("&sup3;", "³")
        .replace("&frac14;", "¼")
        .replace("&frac12;", "½")
        .replace("&frac34;", "¾")
        .replace("&cent;", "¢")
        .replace("&trade;", "™")
}

private fun String.replaceWhitespace(): String {
    return this.replace("\t", "    ")
        .replace("\r\n", "\n")
        .replace("\r", "\n")
        .replace("(?<!\n)\n(?!\n)".toRegex(), "  \n")
}

private fun String.replaceEmojis(): String {
    return Regex("&#(\\d+);").replace(this) { matchResult ->
        matchResult.groupValues[1].toInt().toChar().toString()
    }
}
