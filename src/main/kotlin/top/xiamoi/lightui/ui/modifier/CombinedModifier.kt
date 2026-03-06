package top.xiamoi.lightui.ui.modifier

import org.jetbrains.skia.Canvas
import top.xiamoi.lightui.ui.node.Node

class CombinedModifier(private val outer: Modifier, private val inner: Modifier) : Modifier {
    override fun draw(canvas: Canvas, node: Node, drawContent: () -> Unit) {
        outer.draw(canvas, node) {
            inner.draw(canvas, node, drawContent)
        }
    }
    override fun layout(node: Node, defaultLayout: () -> Unit) {
        outer.layout(node) {
            inner.layout(node, defaultLayout)
        }
    }

}