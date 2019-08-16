package com.religion76.mvpkotlin.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes

/**
 * Created by SunChao on 2019-08-13.
 */

fun View.selectedBy(action: () -> Boolean) {
    this.isSelected = action.invoke()
}

fun View.goneBy(action: () -> Boolean) {
    this.visibility = if (action.invoke()) View.GONE else View.VISIBLE
}

fun View.visibleBy(action: () -> Boolean) {
    this.visibility = if (action.invoke()) View.VISIBLE else View.GONE
}

fun ViewGroup.inflateChild(@LayoutRes resId: Int) = LayoutInflater.from(context).inflate(resId, this, false)

fun EditText.simpleTextChangeListener(onTextChange: ((s: CharSequence?, start: Int, before: Int, count: Int) -> Unit)?) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChange?.invoke(s, start, before, start + count)
        }
    })
}

fun View.hideKeyBoard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun EditText.showKeyBoard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, 0)
}
