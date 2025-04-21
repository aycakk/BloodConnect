package com.softwarengineering.bloodconnect.utils



import android.util.Base64

fun String.toBase64(): String {
    return Base64.encodeToString(this.toByteArray(), Base64.NO_WRAP)
}

fun String.fromBase64(): String {
    return String(Base64.decode(this, Base64.NO_WRAP))
}
