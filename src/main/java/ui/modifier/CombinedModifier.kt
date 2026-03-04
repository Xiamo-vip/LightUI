package ui.modifier

import org.jetbrains.skia.Canvas
import ui.node.Node

class CombinedModifier(private val outer: Modifier, private val inner: Modifier) : Modifier {
    override fun draw(canvas: Canvas, node: Node, drawContent: () -> Unit) {
        outer.draw(canvas, node) {
            inner.draw(canvas, node, drawContent)
        }
    }
}