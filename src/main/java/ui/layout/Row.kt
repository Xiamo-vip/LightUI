package ui.layout

import ui.RenderSystem
import ui.modifier.Modifier
import ui.node.Node

/**
 * Row 水平布局
 */

class RowNode : Node() {

    override fun layout() {
        super.layout()
        var currentX = this.x
        var maxHeight = 0f

        children.forEach { child ->
            child.x = currentX
            child.y = this.y
            child.layout()


            currentX += child.width
            if (child.height > maxHeight) {
                maxHeight = child.height
            }

            this.width = currentX - this.x
            this.height = maxHeight
        }

    }

}

fun Row(modifier: Modifier = Modifier, content: () -> Unit) {
    val row = RowNode()
    row.modifier = modifier
    RenderSystem.add(row)
    RenderSystem.pushNode(row)
    content()
    RenderSystem.popNode()
}