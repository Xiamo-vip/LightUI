package utils.render

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Paint
import org.jetbrains.skia.RRect
import org.jetbrains.skia.Rect

object RenderUtils {
    fun drawRectangle(canvas: Canvas, x: Float, y:Float, width:Float, height:Float,backgroundColor: Int) {
        val paint = Paint().apply {
            color = backgroundColor
        }
        canvas.drawRect(Rect(x, y, x + width, y + height), paint)
    }
    fun drawRoundRectangle(canvas: Canvas, x: Float, y:Float, width:Float, height:Float,radius:Float,backgroundColor: Int) {
        val paint = Paint().apply {
            color = backgroundColor
        }
        val roundRect = RRect.makeXYWH(x, y, width, height, radius)
        canvas.drawRRect(roundRect, paint)
    }
}