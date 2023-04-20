package com.example.stepik

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

class PinCustom @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    private val myShape = MaterialShapeDrawable()

    init {
        val radius = resources.getDimension(R.dimen.pin_radius)
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.Pin, 0, 0
        ).apply {
            try {
                val border = getBoolean(R.styleable.Pin_hasBorder, false)
                val borderColor = getColor(R.styleable.Pin_borderColor, -1)
                val borderSize = resources.getDimension(R.dimen.pin_border)

                myShape.strokeWidth = borderSize
                myShape.strokeColor = ColorStateList.valueOf(borderColor)
                myShape.fillColor = ColorStateList.valueOf(resources.getColor(R.color.button_bg))

                if (radius > 0) {
                    myShape.shapeAppearanceModel = myShape.shapeAppearanceModel.toBuilder()
                        .setAllCorners(CornerFamily.ROUNDED, radius).build()
                }

                if (border) {
                    border(borderColor)
                }
            } finally {
                recycle()
            }
        }
        background = myShape
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    fun border(color: Int) {
        myShape.strokeWidth = resources.getDimension(R.dimen.pin_border)
        myShape.strokeColor = ColorStateList.valueOf(color)
        invalidate()
    }

    fun unBorder() {
        myShape.strokeWidth = 0f
        invalidate()
    }
}
