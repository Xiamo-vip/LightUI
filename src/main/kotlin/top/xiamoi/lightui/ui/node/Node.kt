package top.xiamoi.lightui.ui.node

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Path
import top.xiamoi.lightui.ui.InputManager
import top.xiamoi.lightui.ui.RenderSystem
import top.xiamoi.lightui.ui.layout.values.MarginValue
import top.xiamoi.lightui.ui.layout.values.PaddingValue
import top.xiamoi.lightui.ui.modifier.Modifier

abstract class Node {
    var id = RenderSystem.generateWidgetID().toString()
    var children = mutableListOf<Node>()
    var parent : Node? = null

    var x = 0f
        set(value) {
            val dx = value - field
            field = value
            children.forEach { it.x += dx }
        }
    var y = 0f
        set(value) {
            val dy = value - field
            field = value
            children.forEach { it.y += dy }
        }

    var width = 0f
    var height = 0f

    var isHovered = false
    var modifier: Modifier = Modifier
    var isIgnoreFocus = false

    val contentPath = Path()
    val contentPadding = PaddingValue(0,0,0,0)
    val contentMargin = MarginValue(0,0,0,0)

     fun layout() {
        modifier.layout(this) {
            initLayout()
        }
    }


     fun render(canvas: Canvas) {
        isHovered = findHit(
            InputManager.mouseX,
            InputManager.mouseY) == this

         this.initPath()

        modifier.draw(canvas, this) {
            drawContent(canvas)
            children.forEach { it.render(canvas) }
        }
    }

    open fun initLayout() {
        /*
        让子控件计算好布局
         */
        children.forEach { child ->
            child.layout()
        }

        /*
        获取子控件最大的宽高
         */
        val maxChildWidth = this.children.maxOfOrNull { it.width + it.contentMargin.left + it.contentMargin.right }?:0f
        val maxChildHeight = this.children.maxOfOrNull { it.height + it.contentMargin.top + it.contentMargin.bottom }?:0f

        /*
        计算控件需要的最小宽高
        子控件最大宽高+内边距
         */
        val requiredWidth = maxChildWidth + contentPadding.start + contentPadding.end
        val requiredHeight = maxChildHeight + contentPadding.top + contentPadding.bottom

        if (width < requiredWidth) width = requiredWidth
        if (height < requiredHeight) height = requiredHeight


        children.forEach { child ->
            child.x = this.x + contentPadding.start + child.contentMargin.left
            child.y = this.y + contentPadding.top + child.contentMargin.top
        }

    }

    open fun drawContent(canvas: Canvas) {


    }

    open fun initPath() {
    }

    open fun findHit(mouseX : Float, mouseY : Float): Node? {
        for (i in children.reversed()) {
            val hit = i.findHit(mouseX,mouseY)
            if (hit != null && hit.isIgnoreFocus == false) {
                return hit
            }
        }
        if (mouseX >= x && mouseX <= x + width &&
            mouseY >= y && mouseY <= y + height) {
            return this
        }
        return null
    }

}