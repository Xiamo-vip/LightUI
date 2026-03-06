package top.xiamoi.lightui.ui.layout

import top.xiamoi.lightui.ui.RenderSystem
import top.xiamoi.lightui.ui.modifier.Modifier


/**
 * Column垂直布局
 */

class ColumnNode : top.xiamoi.lightui.ui.node.Node() {
    override fun initLayout() {

        children.forEach { it.layout() }

        var currentY = this.y + contentPadding.top

        /*
        计算子控件总共需要的宽高
         */

        val totalChildWidth = children.sumOf { it.width.toInt() + it.contentMargin.left + it.contentMargin.right }
        val totalChildHeight = children.sumOf { it.height.toInt() + it.contentMargin.top + it.contentMargin.bottom }

        /*
        撑大布局
         */
        val  requiredWidth = totalChildWidth + contentPadding.start + contentPadding.end
        val  requiredHeight = totalChildHeight + contentPadding.top + contentPadding.bottom

        if (width < requiredWidth) { width = requiredWidth.toFloat() }
        if (height < requiredHeight) { height = requiredHeight.toFloat() }




        children.forEach { child ->
           currentY += child.y + child.contentMargin.top
            child.x = this.x + contentPadding.start + child.contentMargin.left
            child.y = currentY
            currentY += child.height + child.contentMargin.bottom
        }
    }
}

fun Column(
    modifier: Modifier = Modifier,
    content: () -> Unit
) {
    val column = ColumnNode()
    column.id = RenderSystem.generateWidgetID().toString()
    column.modifier = modifier
    RenderSystem.add(column)
    RenderSystem.pushNode(column)
    content()
    RenderSystem.popNode()
}