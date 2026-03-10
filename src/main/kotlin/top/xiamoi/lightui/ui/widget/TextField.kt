package top.xiamoi.lightui.ui.widget

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Rect
import top.xiamoi.lightui.ui.RenderSystem
import top.xiamoi.lightui.ui.anim.Animator
import top.xiamoi.lightui.ui.font.FontStyle
import top.xiamoi.lightui.ui.modifier.Modifier

class TextFieldWidget(
    private val text : String,
    onValueChange : (value: String) -> Unit,
    fontStyle: FontStyle,
    textColor : Int,
    isSingleLine: Boolean,
    private val placeHolder : () -> Unit
) :  BaseTextFieldWidget(
    text = text,
    onValueChange = onValueChange,
    textColor = textColor,
    fontStyle = fontStyle,
    isSingleLine = isSingleLine

) {

    override fun drawContent(canvas: Canvas) {
        if (this.text == "") {
            canvas.save()
            RenderSystem.pushNode(this)
            placeHolder()
            this.initLayout()
            RenderSystem.popNode()
            canvas.restore()
        }
        super.drawContent(canvas)


        canvas.drawRect(
            Rect.makeXYWH(this.x, this.y + this.height,this.width,1f),
            Paint().apply {
                color = Animator.animateColor(id + "UnderLine",if (isHovered || isOnFocus) Color.BLUE else Color.withA(Color.BLACK,180))
            }
        )
    }
}

fun TextField(text : String,onValueChange: (value: String) -> Unit,fontStyle: FontStyle,textColor: Int,isSingleLine: Boolean = false,placeHolder: () -> Unit = {},modifier: Modifier = Modifier) {
    val textFieldWidget = TextFieldWidget(text,onValueChange, fontStyle, textColor,isSingleLine,placeHolder)
    textFieldWidget.modifier = modifier
    RenderSystem.add(textFieldWidget)
}