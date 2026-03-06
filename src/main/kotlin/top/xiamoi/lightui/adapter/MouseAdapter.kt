package top.xiamoi.lightui.adapter

import top.xiamoi.lightui.ui.InputManager
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent

object MouseAdapter : java.awt.event.MouseAdapter() {


    override fun mouseClicked(e: MouseEvent) {
//       InputManager.updateMouseClick(e)
        super.mouseClicked(e)
    }

    override fun mousePressed(e: MouseEvent?) {
       InputManager.updateMouseDown(true)
        super.mousePressed(e)
    }

    override fun mouseReleased(e: MouseEvent) {
        InputManager.updateMouseClick(e)
        super.mouseReleased(e)
    }

    override fun mouseEntered(e: MouseEvent) {
       InputManager.updateMousePosition(e)
        super.mouseEntered(e)
    }

    override fun mouseExited(e: MouseEvent?) {
        super.mouseExited(e)
    }

    override fun mouseWheelMoved(e: MouseWheelEvent?) {
        super.mouseWheelMoved(e)
    }

    override fun mouseDragged(e: MouseEvent?) {
        super.mouseDragged(e)
    }

    override fun mouseMoved(e: MouseEvent) {
       InputManager.updateMousePosition(e)
        super.mouseMoved(e)
    }
}