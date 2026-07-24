package com.example.healthtracker.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import androidx.core.graphics.createBitmap

object ProgressRingRenderer {

    private const val SIZE_DP = 104f
    private const val STROKE_DP = 11f
    private const val PERCENT_TEXT_DP = 26f
    private const val LABEL_TEXT_DP = 12f
    private const val TRACK_ALPHA = 60
    private const val CAPTION_ALPHA = 180
    private const val CAPTION_LINE_SPACING = 1.3f
    private const val START_ANGLE = -90f
    private const val FULL_CIRCLE = 360f
    private const val OVER_TARGET_COLOR = 0xFFE5484D.toInt()

    fun render(
        context: Context,
        progress: Float,
        percentLabel: String,
        caption: String,
        accentColor: Int,
        isOverTarget: Boolean
    ): Bitmap {
        val density = context.resources.displayMetrics.density
        val size = (SIZE_DP * density).toInt()
        val stroke = STROKE_DP * density

        val bitmap = createBitmap(size, size)
        val canvas = Canvas(bitmap)

        val arcColor = if (isOverTarget) OVER_TARGET_COLOR else accentColor
        val inset = stroke / 2f
        val bounds = RectF(inset, inset, size - inset, size - inset)

        val trackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = stroke
            color = Color.argb(
                TRACK_ALPHA,
                Color.red(arcColor),
                Color.green(arcColor),
                Color.blue(arcColor)
            )
        }
        canvas.drawArc(bounds, START_ANGLE, FULL_CIRCLE, false, trackPaint)

        val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = stroke
            strokeCap = Paint.Cap.ROUND
            color = arcColor
        }
        canvas.drawArc(bounds, START_ANGLE, FULL_CIRCLE * progress, false, arcPaint)

        val percentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = arcColor
            textAlign = Paint.Align.CENTER
            textSize = PERCENT_TEXT_DP * density
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        }
        val captionPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(
                CAPTION_ALPHA,
                Color.red(arcColor),
                Color.green(arcColor),
                Color.blue(arcColor)
            )
            textAlign = Paint.Align.CENTER
            textSize = LABEL_TEXT_DP * density
        }

        val center = size / 2f
        val percentHeight = -percentPaint.ascent()
        val captionOffset = captionPaint.textSize * CAPTION_LINE_SPACING
        val percentBaseline = center - (percentHeight + captionOffset) / 2f + percentHeight

        canvas.drawText(percentLabel, center, percentBaseline, percentPaint)
        canvas.drawText(caption, center, percentBaseline + captionOffset, captionPaint)

        return bitmap
    }
}
