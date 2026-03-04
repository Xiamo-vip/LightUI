package ui

import ui.node.Node
import java.awt.event.MouseEvent
import java.util.Stack

object InputManager {

    var mouseX = 0f
    var mouseY = 0f
    private var isMouseDown = false
    private var wasMouseClicked = false


    fun broadcast(event: MouseEvent) {
        RenderSystem.getAllNodes().forEach { node ->
            node.mouseMove(event)
        }
    }
    fun updateMousePosition(event: MouseEvent) {
        this.mouseX = event.x.toFloat()
        this.mouseY = event.y.toFloat()
        broadcast(event)
    }
    fun updateMouseDown(bool: Boolean) {
        isMouseDown = bool
    }
    fun updateMouseClick(bool: Boolean) {
        wasMouseClicked = bool
    }


}