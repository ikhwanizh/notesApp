package com.ferdyfermadi.storyapp.ui.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import androidx.core.widget.doAfterTextChanged
import com.ferdyfermadi.storyapp.R

class EmailEditText : CustomEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                when {
                    text.isNullOrBlank() -> {
                        error = resources.getString(R.string.email_must_be_filled)
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(text).matches() -> {
                        error = resources.getString(R.string.invalid_email)
                    }
                    else -> {
                        error = null
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }
}
