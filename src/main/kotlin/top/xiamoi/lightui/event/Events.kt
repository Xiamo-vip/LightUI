package top.xiamoi.lightui.event

import java.awt.event.MouseEvent

class MouseClickEvent() : Event()

class MouseMoveEvent(val mouseEvent : MouseEvent) : Event()