package top.xiamoi.lightui.ui

import org.jetbrains.skia.Canvas
import top.xiamoi.lightui.ui.node.Node
import top.xiamoi.lightui.ui.node.RootNode
import top.xiamoi.lightui.utils.ClearUtils
import java.util.*

internal object RenderSystem {
    var root: RootNode? = null
    var lastFrameRoot : RootNode? = null


    private val parentStack = Stack<Node>()
    private var nodeCounter = 0



    fun begin(windowWidth: Int, windowHeight: Int) {

        lastFrameRoot?.let { ClearUtils.clearEvents(it) }

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

    fun add(node: Node) {
        val parent = parentStack.peek()
        parent.children.add(node)
        node.parent = parent
    }

    fun end(canvas: Canvas) {
        root?.layout()
        root?.render(canvas)
        lastFrameRoot = root
    }

    fun getAllNodes(): List<Node> {
        val result = mutableListOf<Node>()
        fun traverse(current: Node) {
            result.add(current)
            current.children.forEach { traverse(it) }
        }
        traverse(root!!)
        return result
    }

    fun pushNode(nodes: Node) {
        parentStack.push(nodes)
    }
    fun popNode() {
        parentStack.pop()
    }
}