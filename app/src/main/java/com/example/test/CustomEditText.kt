package com.example.test

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatAutoCompleteTextView

class CustomEditText : AppCompatAutoCompleteTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Check if the touch event is outside the EditText
                val x = event.x.toInt()
                val y = event.y.toInt()
                val location = IntArray(2)
                getLocationOnScreen(location)
                val left = location[0]
                val top = location[1]
                val right = left + width
                val bottom = top + height

                if (x < left || x > right || y < top || y > bottom) {
                    // Hide the keyboard when a touch event occurs outside the EditText
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(windowToken, 0)
                }
            }
        }

        return super.onTouchEvent(event)
    }
}