package ui.node

import org.jetbrains.skia.Canvas

class RootNode : Node() {
    init {
        width = 800f
        height = 600f

    }
    override fun layout() {
        children.forEach {
            it.x += this.x
            it.y += this.y
            it.layout()
        }
    }

    override fun render(canvas: Canvas) {
        super.render(canvas)
    }
}