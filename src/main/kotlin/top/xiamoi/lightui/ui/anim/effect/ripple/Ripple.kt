package top.xiamoi.lightui.ui.anim.effect.ripple

import kotlin.time.Duration


data class Ripple(
    val  x  : Float,
    val  y : Float,
    val maxRadius : Float,
    var color : Int,
    val startAlpha : Float = 0.5f,
    val duration: Long = 300L,
    val startTime : Long  = System.currentTimeMillis()
) {
     var alpha : Float = 1f
     var radius : Float = 0f
    fun isFinished(): Boolean = alpha <= 0f
}