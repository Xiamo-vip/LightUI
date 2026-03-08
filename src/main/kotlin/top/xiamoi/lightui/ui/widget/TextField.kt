package top.xiamoi.lightui.ui.widget

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Rect
import top.xiamoi.lightui.ui.RenderSystem
import top.xiamoi.lightui.ui.anim.Animator
import top.xiamoi.lightui.ui.font.FontStyle

class TextFieldWidget(text : String,onValueChange: (value: String) -> Unit,fontStyle: FontStyle,textColor : Int) :  BaseTextFieldWidget(
    text = text,
    onValueChange = onValueChange,
    textColor = textColor,
    fontStyle = fontStyle,

) {

    override fun drawContent(canvas: Canvas) {
        super.drawContent(canvas)
        canvas.drawRect(
            Rect.makeXYWH(this.x, this.y + this.height,this.width,1f),
            Paint().apply {
                color = Animator.animateColor(id + "UnderLine",if (isHovered) Color.BLUE else Color.withA(Color.BLACK,180))
            }
        )
    }
}

fun TextField(text : String,onValueChange: (value: String) -> Unit,fontStyle: FontStyle,textColor: Int) {
    val textFieldWidget = TextFieldWidget(text,onValueChange, fontStyle, textColor)
    RenderSystem.add(textFieldWidget)

}