package top.xiamoi.lightui.ui

import org.jetbrains.skiko.Cursor
import top.xiamoi.lightui.event.EventBus
import top.xiamoi.lightui.event.EventListener
import top.xiamoi.lightui.event.MouseDragEvent
import top.xiamoi.lightui.event.MouseMoveEvent

internal object InputManager {

    var mouseX = 0f
    var mouseY = 0f

    private var focusedNodeID  : String? = null

     var selectionStart : Int = 0
     var selectionEnd : Int = 0

    var cursorStyle : Int = Cursor.DEFAULT_CURSOR

    var imeX: Float = 0f
    var imeY: Float = 0f
    var imeHeight: Float = 0f

    init {
        EventBus.subscribe(this)

    }

    fun updateFocusedNode(nodeID: String?) {
        if (this.focusedNodeID != nodeID) {
            selectionStart = 0
            selectionEnd = 0
        }
        this.focusedNodeID = nodeID
    }

    fun getFocusedNodeID(): String? {
        return focusedNodeID
    }

    @EventListener
    fun mouseMove(e: MouseMoveEvent) {
        mouseX = e.mouseEvent.x.toFloat()
        mouseY = e.mouseEvent.y.toFloat()
    }

    @EventListener
    fun mouseDrag(e: MouseDragEvent) {
        mouseX = e.mouseEvent.x.toFloat()
        mouseY = e.mouseEvent.y.toFloat()
    }


}