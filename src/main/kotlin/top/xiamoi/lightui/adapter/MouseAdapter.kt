package top.xiamoi.lightui.adapter

import top.xiamoi.lightui.event.MouseClickEvent
import top.xiamoi.lightui.event.MouseMoveEvent
import java.awt.event.MouseEvent

object MouseAdapter : java.awt.event.MouseAdapter() {


    override fun mouseClicked(e: MouseEvent) {
//       InputManager.updateMouseClick(e)
        super.mouseClicked(e)
    }

    override fun mousePressed(e: MouseEvent?) {
        MouseClickEvent().broadcast()
        super.mousePressed(e)
    }

    override fun mouseMoved(e: MouseEvent) {
       MouseMoveEvent(e).broadcast()
        super.mouseMoved(e)
    }
}