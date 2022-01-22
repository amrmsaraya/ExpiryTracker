package com.github.amrmsaraya.expirytracker.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatDate(): String {
    return SimpleDateFormat("dd/MM/y", Locale.getDefault()).format(this)
}