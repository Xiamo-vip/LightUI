package ui.modifier

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Paint
import ui.anim.Animator
import ui.node.Node

interface Modifier {
    fun draw(canvas: Canvas,node: Node,drawContent: () -> Unit) {
        drawContent()
    }

    infix fun then(other: Modifier): Modifier {
        if (this == other) return other
        if (other == Modifier) return this
        return CombinedModifier(this, other)
    }

    companion object : Modifier

}



fun Modifier.hoverScale(targetScale: Float = 1.1f): Modifier = this.then(
    object : Modifier {
        override fun draw(canvas: Canvas, node: Node, drawContent: () -> Unit) {
            val isHovered = node.isHovered
            val scale = Animator.animateFloat("${node.id}_hoverScale", if (isHovered) targetScale else 1f, 200L)
            canvas.save()
            val centerX = node.x + node.width / 2f
            val centerY = node.y + node.height / 2f
            canvas.translate(centerX, centerY)
            canvas.scale(scale, scale)
            canvas.translate(-centerX, -centerY)
            drawContent()
            canvas.restore()
        }
    }
)

fun Modifier.alpha(targetAlpha: Float): Modifier = this.then(
    object : Modifier {
        override fun draw(canvas: Canvas, node: Node, drawContent: () -> Unit) {
            val currentAlpha = Animator.animateFloat("${node.id}_alpha", targetAlpha, 300L)
            canvas.saveLayer(node.x, node.y, node.x + node.width, node.y + node.height, Paint().apply {
                alpha = (currentAlpha * 255).toInt()
            })
            drawContent()
            canvas.restore()
        }
    }
)
