package ui.layout

import ui.RenderSystem
import ui.modifier.Modifier
import ui.node.Node


/**
 * Column垂直布局
 */

class ColumnNode : Node() {
    override fun layout() {
        var currentY = y
        var maxWidth = 0f

        children.forEach { child ->
            child.x = this.x
            child.y = currentY
            child.layout()
            currentY += child.height
            if (child.width > maxWidth) {
                maxWidth = child.width
            }
            this.width = maxWidth
            this.height = currentY - this.y
        }
        super.layout()
    }
}

fun Column(
    modifier: Modifier = Modifier,
    content: () -> Unit
) {
    val column = ColumnNode()
    column.id = RenderSystem.generateWidgetID().toString()
    column.modifier = modifier
    RenderSystem.add(column)
    RenderSystem.pushNode(column)
    content()
    RenderSystem.popNode()
}