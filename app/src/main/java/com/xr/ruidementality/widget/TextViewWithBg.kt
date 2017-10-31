package com.xr.ruidementality.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.TypedValue
import com.xr.ruidementality.R

/**
 * Created by Sage on 2017/9/26.
 * Description:根据给定的颜色，生成默认圆角背景图
 */
class TextViewWithBg : AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        var a = context.obtainStyledAttributes(attrs, R.styleable.TextViewWithBg)

        colorBg = a.getColor(R.styleable.TextViewWithBg_tv_bg_color, context.resources.getColor(R.color.default_color_theme))
        colorStroke = a.getColor(R.styleable.TextViewWithBg_tv_bg_stroke_color, 0)
        strokeWidth = a.getDimensionPixelSize(R.styleable.TextViewWithBg_tv_stroke_width, 0).toFloat()
        cornerRadius = a.getDimensionPixelSize(R.styleable.TextViewWithBg_tv_corner_radius, -1).toFloat()
        paintBg.strokeWidth = strokeWidth
        a.recycle()

    }

    private fun addRipple() {
        if(isClickable){
            val typedValue = TypedValue();
            if (context.theme.resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true)) {
                setBackgroundResource(typedValue.resourceId);
            }
        }

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        addRipple()
    }
    var colorBg: Int = 0
    var colorStroke: Int = 0
    var strokeWidth = 0f
    var cornerRadius = -1f

    var paintBg = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {

        if (cornerRadius < 0) {
            cornerRadius = height / 2 - strokeWidth
        }
        var rectF = RectF(strokeWidth, strokeWidth, width - strokeWidth, height - strokeWidth)
        paintBg.color = colorBg
        paintBg.style = Paint.Style.FILL
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paintBg)
        paintBg.color = colorStroke
        paintBg.style = Paint.Style.STROKE

        canvas.drawRoundRect(RectF(strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2, height - strokeWidth / 2),
                cornerRadius, cornerRadius, paintBg)
        super.onDraw(canvas)
    }

}