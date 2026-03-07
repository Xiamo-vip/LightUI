package top.xiamoi.lightui.ui

import top.xiamoi.lightui.event.EventBus
import top.xiamoi.lightui.event.EventListener
import top.xiamoi.lightui.event.MouseMoveEvent

internal object InputManager {

    var mouseX = 0f
    var mouseY = 0f


    init {
        EventBus.subscribe(this)

    }


    @EventListener
    fun mouseMove(e: MouseMoveEvent) {
        mouseX = e.mouseEvent.x.toFloat()
        mouseY = e.mouseEvent.y.toFloat()
    }


}