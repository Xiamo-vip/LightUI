package top.xiamoi.lightui.ui

import org.jetbrains.skia.Canvas
import top.xiamoi.lightui.ui.font.FontManager
import top.xiamoi.lightui.ui.node.RootNode
import top.xiamoi.lightui.ui.theme.ThemeManager
import java.util.Stack

internal object RenderSystem {
    var root: top.xiamoi.lightui.ui.node.RootNode? = null
    var lastFrameRoot : top.xiamoi.lightui.ui.node.RootNode? = null


    private val parentStack = Stack<top.xiamoi.lightui.ui.node.Node>()
    private var nodeCounter = 0



    fun begin(windowWidth: Int, windowHeight: Int) {
        root = RootNode().apply {
            this.width = windowWidth.toFloat()
            this.height = windowHeight.toFloat()
        }
        nodeCounter = 0

        parentStack.clear()
        parentStack.push(root)
    }

    fun generateWidgetID() : Int {
        nodeCounter++
        return nodeCounter
    }

    fun add(node: top.xiamoi.lightui.ui.node.Node) {
        val parent = parentStack.peek()
        parent.children.add(node)
        node.parent = parent
    }

    fun end(canvas: Canvas) {
        root?.layout()
        root?.render(canvas)
        lastFrameRoot = root
    }

    fun getAllNodes(): List<top.xiamoi.lightui.ui.node.Node> {
        val result = mutableListOf<top.xiamoi.lightui.ui.node.Node>()
        fun traverse(current: top.xiamoi.lightui.ui.node.Node) {
            result.add(current)
            current.children.forEach { traverse(it) }
        }
        traverse(root!!)
        return result
    }

    fun pushNode(nodes: top.xiamoi.lightui.ui.node.Node) {
        parentStack.push(nodes)
    }
    fun popNode() {
        parentStack.pop()
    }
}