package ui

import org.jetbrains.skia.Canvas
import ui.node.Node
import ui.node.RootNode
import java.util.Stack

object RenderSystem {
    var root: RootNode? = null
    var lastFrameRoot : RootNode? = null


    private val parentStack = Stack<Node>()
    private var nodeCounter = 0



    fun begin() {
        root = RootNode()
        nodeCounter = 0

        parentStack.clear()
        parentStack.push(root)
    }

    fun generateWidgetID() : Int {
        nodeCounter++
        return nodeCounter
    }

    fun add(node: Node) {
        parentStack.peek().children.add(node)
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