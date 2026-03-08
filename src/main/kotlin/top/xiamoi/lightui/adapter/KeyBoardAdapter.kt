package top.xiamoi.lightui.adapter

import top.xiamoi.lightui.event.KeyTypedEvent
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

object KeyBoardAdapter : KeyAdapter() {
    override fun keyTyped(e: KeyEvent) {
        super.keyTyped(e)
        KeyTypedEvent(e).broadcast()

    }

    override fun keyPressed(e: KeyEvent) {
        super.keyPressed(e)
    }

    override fun keyReleased(e: KeyEvent) {
        super.keyReleased(e)
    }
}