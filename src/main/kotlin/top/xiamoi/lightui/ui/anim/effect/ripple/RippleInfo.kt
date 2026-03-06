package top.xiamoi.lightui.ui.anim.effect.ripple

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.ClipMode
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Path
import top.xiamoi.lightui.SkiaRender


class RippleInfo {
    val ripples = mutableListOf<Ripple>()


    fun spawnRipple(x: Float, y: Float, color : Int , duration : Long = 1000L,radius : Float = 150f) {
        ripples.add(
            Ripple(x = x, y = y,color = color, maxRadius = radius )
        )

    }


    fun apply(canvas: Canvas,path: Path = Path()) {
        canvas.save()
        canvas.clipPath(path , ClipMode.INTERSECT, true)
        val currentTime = System.currentTimeMillis()
        val iter = ripples.iterator()
        while (iter.hasNext()) {
            val ripple = iter.next()
            val progress = ((currentTime - ripple.startTime).toFloat() / ripple.duration.toFloat()).coerceIn(0f,1f)
            ripple.radius = progress * ripple.maxRadius
            ripple.alpha = ripple.startAlpha - ripple.startAlpha * progress

            if (ripple.isFinished() || progress >= 1f) {
                iter.remove()
            } else {
                canvas.drawCircle(ripple.x, ripple.y, ripple.radius, Paint().apply {
                    color = ripple.color
                    alpha = (ripple.alpha * 255).toInt().coerceIn(0, 255)
                })
            }
        }
        canvas.restore()
        SkiaRender.skiaLayer.needRedraw()


    }



}