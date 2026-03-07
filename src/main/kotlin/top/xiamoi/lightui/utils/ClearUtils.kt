package top.xiamoi.lightui.utils

import top.xiamoi.lightui.event.EventBus
import top.xiamoi.lightui.ui.node.Node

object ClearUtils {
    fun clearEvents(rootNode : Node) {
        EventBus.unsubscribe(rootNode)
        rootNode.children.forEach { clearEvents(it) }
    }
}