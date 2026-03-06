package top.xiamoi.lightui.utils

import org.jetbrains.skia.Path
import org.jetbrains.skia.RRect
import org.jetbrains.skia.Rect

object RenderUtils {
    fun drawRectangle(x: Float, y:Float, width:Float, height:Float) : Path {
        return Path().apply {
            this.addRect(
                Rect.makeXYWH(x,y,width,height)
            )
        }
    }
    fun drawRoundRectanglePath(x: Float, y:Float, width:Float, height:Float,radius:Float) : Path {
        val roundRect = RRect.makeXYWH(x, y, width, height, radius)

        return Path().apply {
            this.addRRect(roundRect)
        }
    }
}