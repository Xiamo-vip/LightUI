package ui.anim

import kotlin.math.pow

object AnimationFunctionManager {

    fun animate(x:Float,animationFunction: AnimationFunction = AnimationFunction.EASE_OUT_CIRC) : Float {
        when (animationFunction) {
            AnimationFunction.EASE_OUT_CUBIC -> {
                return 1f - (1f - x).pow(3)
            }
            AnimationFunction.EASE_IN_OUT_CUBIC -> {
                return if (x < 0.5f) {
                    4f * x * x * x
                } else {
                    1f - ((-2f * x + 2f).toDouble().pow(3.0) / 2.0).toFloat()
                }
            }
            AnimationFunction.EASE_OUT_CIRC -> {
                val t = x - 1f
                return kotlin.math.sqrt(1f - t * t)
            }
        }


    }


}