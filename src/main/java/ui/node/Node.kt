package ui.node

import org.jetbrains.skia.Canvas
import ui.InputManager
import ui.modifier.Modifier
import java.awt.event.MouseEvent

abstract class Node {
    var id = ""
    val children = mutableListOf<Node>()
    var x = 0f
    var y = 0f
    var width = 0f
    var height = 0f

    var isHovered = false
    var modifier: Modifier = Modifier


    open fun mouseMove(e: MouseEvent) {
        if (findHit(this) == this) isHovered = true else isHovered = false
    }

    open fun layout() {}
    open fun render(canvas: Canvas) {
        isHovered = findHit(this) == this
        modifier.draw(canvas, this) {
            drawContent(canvas)
            children.forEach { it.render(canvas) }
        }
    }

    open fun drawContent(canvas: Canvas) {}

    fun findHit(node: Node): Node? {
        val mouseX = InputManager.mouseX
        val mouseY = InputManager.mouseY
        for (i in node.children.reversed()) {
            val hit = findHit(i)
            if (hit != null) {
                return hit
            }
        }
        if (mouseX >= x && mouseX <= x + width &&
            mouseY >= y && mouseY <= y + height) {
            return this
        }
        return null
    }

}