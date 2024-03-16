package com.m4ykey.markdown

import org.apache.commons.text.StringEscapeUtils

internal fun String.removeSpecials() = StringEscapeUtils.unescapeHtml4(this)