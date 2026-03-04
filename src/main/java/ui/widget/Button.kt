package ui.widget

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skia.Paint
import ui.RenderSystem
import ui.modifier.Modifier
import ui.node.Node
import utils.render.RenderUtils


class ButtonWidget(
    private val text: String,
    private val onClick: () -> Unit
) : Node() {
    override fun layout() {
        width = 100f
        height = 40f
    }

    override fun drawContent(canvas: Canvas) {
        super.drawContent(canvas)
        val paint = Paint().apply {
            color = if (isHovered) Color.YELLOW else Color.BLUE
        }
        if (isHovered) {
            RenderUtils.drawRoundRectangle(canvas,x,y,width,height,8f, Color.BLUE)
        } else {
            RenderUtils.drawRoundRectangle(canvas,x,y,width,height,8f, Color.RED)
        }
    }


}

fun Button(text : String, modifier: Modifier = Modifier, onClick : () -> Unit) {
    val btn = ButtonWidget(text, onClick)
    btn.modifier = modifier
    btn.id = RenderSystem.generateWidgetID().toString()
    RenderSystem.add(
        btn
    )
}