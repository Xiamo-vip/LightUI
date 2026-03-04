package adapter

import SkiaRender
import org.jetbrains.skiko.SkiaLayer
import ui.InputManager
import ui.RenderSystem
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent

object MouseAdapter : MouseAdapter() {
    override fun mouseClicked(e: MouseEvent) {
        InputManager.updateMouseClick(true)
        super.mouseClicked(e)
    }

    override fun mousePressed(e: MouseEvent?) {
        InputManager.updateMouseDown(true)
        super.mousePressed(e)
    }

    override fun mouseReleased(e: MouseEvent?) {
        InputManager.updateMouseDown(false)
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