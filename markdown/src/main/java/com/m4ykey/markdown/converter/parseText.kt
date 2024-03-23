package com.m4ykey.markdown.converter

import org.jsoup.Jsoup

fun parseText(text : String) : String = Jsoup.parse(text).text()