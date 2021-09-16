package com.shivamkumarjha.openweathermap.ui.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.getColorById(@ColorRes id: Int) = ContextCompat.getColor(this, id)