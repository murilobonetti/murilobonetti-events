package com.murilobonetti.events.utils

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.util.regex.Pattern


fun View.getTextString(): String? {
    return if (this is EditText) {
        this.text.toString()
    } else {
        null
    }
}

fun View.validateEmail(): Boolean {
    val emailPattern =
        "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    val email: String = this.getTextString() ?: return false
    val pattern: Pattern = Pattern.compile(emailPattern)
    if (!pattern.matcher(email).matches()) {
        return false
    }
    return true
}

fun showToast(context: Context, messageId: Int) {
    Toast.makeText(
        context,
        context.getString(messageId),
        Toast.LENGTH_SHORT
    ).show()
}