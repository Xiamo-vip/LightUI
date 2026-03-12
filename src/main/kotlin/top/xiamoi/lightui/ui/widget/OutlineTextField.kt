package top.xiamoi.lightui.ui.widget

import org.jetbrains.skia.*
import top.xiamoi.lightui.ui.InputManager
import top.xiamoi.lightui.ui.RenderSystem
import top.xiamoi.lightui.ui.anim.Animator
import top.xiamoi.lightui.ui.font.FontStyle
import top.xiamoi.lightui.ui.modifier.Modifier
import top.xiamoi.lightui.ui.node.Node
import top.xiamoi.lightui.ui.widget.styles.DefaultOutlineTextFieldStyle
import top.xiamoi.lightui.ui.widget.styles.OutlineTextFieldStyle

class OutlineTextFieldWidget(
    text: String,
    onValueChange: (String) -> Unit,
    isSingleLine : Boolean,
    val style : OutlineTextFieldStyle

) : Node() {

    val baseTextFieldWidget = BaseTextFieldWidget(text, onValueChange, fontStyle = FontStyle.defaultFontStyle,textColor = Color.BLACK,isSingleLine = isSingleLine)

    init {

        RenderSystem.pushNode(this)
        RenderSystem.add(baseTextFieldWidget)
        RenderSystem.popNode()


    }

    private val outlinePaint = Paint().apply {
        mode = PaintMode.STROKE
        isAntiAlias = true
    }


    override fun initPath() {

        this.contentPath.addRRect(
            RRect.makeXYWH(this.x,this.y,this.width,this.height, radius = style.roundCorners),
        )




    }


    override fun drawContent(canvas: Canvas) {

        val isFocused = baseTextFieldWidget.isOnFocus || this.isOnFocus
        val hitNode = this.findHit(InputManager.mouseX, InputManager.mouseY)
        val isHovering = hitNode != null

        outlinePaint.color = Animator.animateColor(
            id = id + "color",
            targetColor = if (isFocused) style.strokeColorOnFocus else if (isHovering) style.strokeColorOnHover else style.strokeColorOnDefault
        )

        outlinePaint.strokeWidth = Animator.animateFloat(
            id = id + "hoverWidth",
            targetValue = if (isFocused) style.strokeWidthOnFocus else if (isHovering) style.strokeWidthOnHover else style.strokeWidthOnDefault
        )

        canvas.drawPath(this.contentPath,outlinePaint)
        super.drawContent(canvas)


    }
}

fun OutlineTextField(text : String, modifier: Modifier = Modifier, onValueChange: (String) -> Unit,isSingleLine: Boolean = true) {
    val outlineTextFieldWidget  = OutlineTextFieldWidget(text, onValueChange,isSingleLine, DefaultOutlineTextFieldStyle())
    outlineTextFieldWidget.modifier = modifier
    RenderSystem.add(outlineTextFieldWidget)
}