package top.xiamoi.lightui.event

import java.awt.event.KeyEvent
import java.awt.event.MouseEvent

class MouseClickEvent() : Event()

class MouseMoveEvent(val mouseEvent : MouseEvent) : Event()

class KeyTypedEvent(val keyEvent : KeyEvent) : Event()

class TextInputEvent(val text: String) : Event()