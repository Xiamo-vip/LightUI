package top.xiamoi.lightui.ui

import java.awt.event.MouseEvent
import kotlin.collections.forEach

internal object InputManager {

    var mouseX = 0f
    var mouseY = 0f
    private var isMouseDown = false


    fun broadcastMove(event: MouseEvent) {
        RenderSystem.getAllNodes().forEach { node ->
            node.mouseMove(event)
        }
    }
    fun broadcastClick(event: MouseEvent) {
        RenderSystem.getAllNodes().forEach { node ->
            node.click(event)
        }
    }
    fun updateMousePosition(event: MouseEvent) {
        this.mouseX = event.x.toFloat()
        this.mouseY = event.y.toFloat()
        broadcastMove(event)
    }
    fun updateMouseDown(bool: Boolean) {
        isMouseDown = bool
    }
    fun updateMouseClick(event: MouseEvent) {
        broadcastClick(event)
    }


}