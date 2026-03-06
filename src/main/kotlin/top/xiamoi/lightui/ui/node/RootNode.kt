package top.xiamoi.lightui.ui.node

import org.jetbrains.skia.Canvas

class RootNode : Node() {
    init {
        width = 800f
        height = 600f

    }
    override fun initLayout() {
        children.forEach {
            it.x += this.x
            it.y += this.y
            it.layout()
        }
    }

    override fun drawContent(canvas: Canvas) {
        super.drawContent(canvas)
    }
}