package top.xiamoi.lightui.ui.anim

import top.xiamoi.lightui.utils.ColorUtils


private class AnimState(var currentValue: Float) {
    var targetValue: Float = currentValue
    var startValue: Float = currentValue
    var startTime: Long = 0L
}


private class ColorAnimState(var currentColor: Int) {
    var targetColor: Int = currentColor
    var startColor: Int = currentColor
    var startTime: Long = 0L
}

object Animator {
    private val states = mutableMapOf<String, AnimState>()
    private val colorStates = mutableMapOf<String, ColorAnimState>()
    /**
     * 动画函数
     * @param id 标识符
     * @param targetValue 目标值
     * @param durationMs 动画时长(MS)
     * @param animationFunction 动画类型
     * @return 值
     */
    fun animateFloat(id: String, targetValue: Float, durationMs: Long = 300L,animationFunction: AnimationFunction = AnimationFunction.EASE_OUT_CUBIC): Float {
        val state = states.getOrPut(id) { AnimState(targetValue) }

        if (state.targetValue != targetValue) {
            state.startValue = state.currentValue
            state.targetValue = targetValue
            state.startTime = System.nanoTime()
        }

        if (state.currentValue != state.targetValue) {
            val elapsedMs = (System.nanoTime() - state.startTime) / 1_000_000L
            val progress = (elapsedMs.toFloat() / durationMs).coerceIn(0f, 1f)


            val easedProgress = AnimationFunctionManager.animate(x = progress,animationFunction)


            state.currentValue = state.startValue + (state.targetValue - state.startValue) * easedProgress


            if (progress < 1f) {
                _root_ide_package_.top.xiamoi.lightui.SkiaRender.skiaLayer.needRedraw()
            } else {
                state.currentValue = state.targetValue
            }
        }

        return state.currentValue
    }

    fun animateColor(
        id: String,
        targetColor: Int,
        durationMs: Long = 400L,
        animationFunction: AnimationFunction = AnimationFunction.EASE_OUT_CUBIC
    ): Int {
        val state = colorStates.getOrPut(id) { ColorAnimState(targetColor) }
        if (state.targetColor != targetColor) {
            state.startColor = state.currentColor
            state.targetColor = targetColor
            state.startTime = System.nanoTime()
        }

        if (state.currentColor != state.targetColor) {
            val elapsedMs = (System.nanoTime() - state.startTime) / 1_000_000L
            val progress = (elapsedMs.toFloat() / durationMs).coerceIn(0f, 1f)
            val easedProgress = AnimationFunctionManager.animate(progress, animationFunction)
            state.currentColor = ColorUtils.lerpHSB(state.startColor, state.targetColor, easedProgress)
            if (progress < 1f) {
                _root_ide_package_.top.xiamoi.lightui.SkiaRender.skiaLayer.needRedraw()
            } else {
                state.currentColor = state.targetColor
            }
        }

        return state.currentColor
    }

}