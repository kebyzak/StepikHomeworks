package com.example.stepik

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class PinCompound @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val pinViews: List<PinCustom>
    private val pinTextAttrs = intArrayOf(
        R.styleable.PinCompound_pinOne,
        R.styleable.PinCompound_pinTwo,
        R.styleable.PinCompound_pinThree,
        R.styleable.PinCompound_pinFour
    )

    init {
        inflate(context, R.layout.item_pin, this)

        pinViews = listOf(
            findViewById(R.id.cv_pin_1),
            findViewById(R.id.cv_pin_2),
            findViewById(R.id.cv_pin_3),
            findViewById(R.id.cv_pin_4)
        )

        context.theme.obtainStyledAttributes(attrs, R.styleable.PinCompound, 0, 0).apply {
            try {
                pinTextAttrs.forEachIndexed { index, attr ->
                    pinViews[index].text = getString(attr)
                }
            } finally {
                recycle()
            }
        }
    }

    fun setPinValue(index: Int, value: String) {
        if (index in pinViews.indices) {
            pinViews[index].text = value
        }
    }

    fun clear() {
        pinViews.forEach {
            it.unBorder()
            it.text = ""
        }
    }

    fun showError(errorColor: Int) {
        pinViews.forEach {
            it.border(errorColor)
            it.setTextColor(errorColor)
        }
    }

    val pinOne: PinCustom
        get() = pinViews[0]
    val pinTwo: PinCustom
        get() = pinViews[1]
    val pinThree: PinCustom
        get() = pinViews[2]
    val pinFour: PinCustom
        get() = pinViews[3]
}