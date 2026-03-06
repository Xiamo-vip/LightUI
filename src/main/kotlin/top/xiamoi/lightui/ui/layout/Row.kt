package top.xiamoi.lightui.ui.layout

import top.xiamoi.lightui.ui.RenderSystem
import top.xiamoi.lightui.ui.modifier.Modifier

/**
 * Row 水平布局
 */

class RowNode : top.xiamoi.lightui.ui.node.Node() {

    override fun initLayout() {
        /*
        子控件初始化布局
         */

        children.forEach { child ->
            child.layout()
        }

        var currentX = this.x + contentPadding.start

        /*
        计算出子控件总共的宽高
         */
        val totalChildWidth = children.sumOf { it.width.toInt() + it.contentMargin.left + it.contentMargin.right }
        val totalChildHeight = children.sumOf { it.height.toInt() + it.contentMargin.top + it.contentMargin.bottom }

        val  requiredWidth = totalChildWidth + contentPadding.start + contentMargin.right
        val  requiredHeight = totalChildHeight + contentPadding.top + contentMargin.bottom

        /*
        撑大布局
         */

        if (width < requiredWidth) { width = requiredWidth.toFloat() }
        if (height < requiredHeight) { height = requiredHeight.toFloat() }



        children.forEach { child ->
            currentX += child.contentMargin.left
            child.x = currentX
            child.y = this.y + contentPadding.top + child.contentMargin.top
            currentX += child.width + child.contentMargin.right
        }



    }

}

fun Row(modifier: Modifier = Modifier, content: () -> Unit) {
    val row = RowNode()
    row.modifier = modifier
   RenderSystem.add(row)
    RenderSystem.pushNode(row)
    content()
    RenderSystem.popNode()
}