package top.xiamoi.lightui.ui.modifier

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Rect
import top.xiamoi.lightui.ui.anim.Animator
import top.xiamoi.lightui.ui.layout.values.PaddingValue
import top.xiamoi.lightui.ui.node.Node

interface Modifier {
    fun draw(canvas: Canvas, node: Node, drawContent: () -> Unit) {
        drawContent()
    }

    fun layout(node: Node, defaultLayout:() -> Unit) {
        defaultLayout()
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

fun Modifier.fillMaxSize() : Modifier = this.then(
    object : Modifier {
        override fun layout(node: Node, defaultLayout: () -> Unit) {

            node.width  = node.parent?.width!!
            node.height = node.parent?.height!!
            defaultLayout()
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

fun Modifier.background(color : Int = Color.TRANSPARENT): Modifier = this.then(
    object : Modifier {
        override fun draw(canvas: Canvas, node: Node, drawContent: () -> Unit) {
            canvas.save()
            canvas.drawRect(
                r = Rect(node.x, node.y, node.x + node.width, node.y + node.height),
                paint = Paint().apply {
                    this.color = color
                }
            )
            drawContent()
            canvas.restore()

        }
    }
)

fun Modifier.size(width: Float, height: Float): Modifier = this.then(
    object : Modifier {
        override fun layout(node: Node, defaultLayout: () -> Unit) {

            node.width = width
            node.height = height
            defaultLayout()
        }
    }
)

fun Modifier.width(width: Float): Modifier = this.then(
    object : Modifier {
        override fun layout(node: Node, defaultLayout: () -> Unit) {

            node.width = width
            defaultLayout()
        }
    }
)

fun Modifier.height(height: Float): Modifier = this.then(
    object : Modifier {
        override fun layout(node: Node, defaultLayout: () -> Unit) {

            node.height = height
            defaultLayout()
        }
    }
)

fun Modifier.padding(start: Int = PaddingValue.NOCHANGE, end: Int = PaddingValue.NOCHANGE, top: Int = PaddingValue.NOCHANGE, bottom: Int = PaddingValue.NOCHANGE): Modifier = this.then(
    object : Modifier {
        override fun layout(node: Node, defaultLayout: () -> Unit) {
            start.takeIf { it >=0 }.apply { node.contentPadding.start = start }
            end.takeIf { it >=0 }.apply { node.contentPadding.end = end }
            top.takeIf { it >=0 }.apply { node.contentPadding.top = top }
            bottom.takeIf { it >=0 }.apply { node.contentPadding.bottom = bottom }
            defaultLayout()
        }
    }
)

fun Modifier.padding(all : Int): Modifier = this.then(
    object : Modifier {
        override fun layout(node: Node, defaultLayout: () -> Unit) {
            node.contentPadding.start = all
            node.contentPadding.top = all
            node.contentPadding.bottom = all
            node.contentPadding.end = all
            defaultLayout()
        }
    }
)

fun Modifier.margin(all : Int): Modifier = this.then(
    object : Modifier {
        override fun layout(node: Node, defaultLayout: () -> Unit) {
            node.contentMargin.top = all
            node.contentMargin.bottom = all
            node.contentMargin.right = all
            node.contentMargin.left = all
            defaultLayout()
        }
    }
)

fun Modifier.margin(left : Int = PaddingValue.NOCHANGE, right : Int = PaddingValue.NOCHANGE, top: Int = PaddingValue.NOCHANGE, bottom: Int = PaddingValue.NOCHANGE): Modifier = this.then(
    object : Modifier {
        override fun layout(node: Node, defaultLayout: () -> Unit) {
            left.takeIf {it  >= 0 }.apply { node.contentMargin.left = left }
            right.takeIf {it  >= 0 }.apply { node.contentMargin.right = right }
            top.takeIf {it  >= 0 }.apply { node.contentMargin.top = top }
            bottom.takeIf {it  >= 0 }.apply { node.contentMargin.bottom = bottom }
            defaultLayout()
        }
    }
)


