package com.nab.weatherforecast.common

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import com.nab.weatherforecast.di.MainApp
import kotlinx.coroutines.*

/**
 * Created by tho nguyen on 6/17/2021.
 * dinhthohcmus@gmail.com
 */

fun toast(message: String, time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MainApp.instance, message, time).show()
}

fun String?.value(def: String = "") = this ?: def
fun Int?.value(def: Int = 0) = this ?: def

fun Context.hideKeyboard(view: View){
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun TextView.asyncText(text: CharSequence?): Job {
    val error = CoroutineExceptionHandler { _, t ->
        t.printStackTrace()
    }
    return CoroutineScope(Dispatchers.Main).launch(error) {
        val precomputedText = withContext(Dispatchers.IO) {
            PrecomputedTextCompat.create(
                text ?: "",
                TextViewCompat.getTextMetricsParams(this@asyncText)
            )
        }
        TextViewCompat.setPrecomputedText(this@asyncText, precomputedText)
    }
}

