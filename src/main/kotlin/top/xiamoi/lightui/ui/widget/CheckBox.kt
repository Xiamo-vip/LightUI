package top.xiamoi.lightui.ui.widget

import org.jetbrains.skia.*
import top.xiamoi.lightui.event.EventBus
import top.xiamoi.lightui.ui.RenderSystem
import top.xiamoi.lightui.ui.anim.Animator
import top.xiamoi.lightui.ui.node.Node
import top.xiamoi.lightui.ui.widget.styles.CheckBoxStyle
import top.xiamoi.lightui.ui.widget.styles.DefaultCheckBoxStyle

class BaseCheckBoxWidget(
    val checked: Boolean,
    val onCheckedChange: (Boolean) -> Unit,
    val style : CheckBoxStyle,
) : Node() {

    val paintStroke = Paint().apply {
        color = if (checked) style.strokeColorOnCheck else if (isHovered) style.strokeColorOnHover else style.strokeColorOnDefault
        mode = PaintMode.STROKE
        isAntiAlias = true
        strokeWidth = style.strokeWidthOnDefault
    }

    val paintBackground = Paint().apply {
        color = if (checked) style.backgroundColorOnChecked else style.backgroundColorOnDefault
        isAntiAlias = true
    }

    private val checkMarkPath = Path.makeFromSVGString("M400-304 240-464l56-56 104 104 264-264 56 56-320 320Z")
    private val paintCheckMark = Paint().apply {
        color = style.iconColorOnBackground
        isAntiAlias = true
    }



    init {
        this.width = 24f
        this.height = 24f
        EventBus.subscribe(this)

    }

    override fun onClicked() {
        onCheckedChange(!checked)
    }



    override fun initPath() {
        this.contentPath.addRRect(
            RRect.makeXYWH(this.x,this.y,this.width,this.height,5f)
        )
        super.initPath()
    }

    override fun drawContent(canvas: Canvas) {
        val scale = Animator.animateFloat(this.id + "iconScale",if (checked) 1f else 0f)


        paintBackground.alpha = Animator.animateFloat(this.id + "backgroundOnChecked",if (checked) 255f else 0f).toInt()
        paintStroke.alpha = 255 - paintBackground.alpha
        canvas.drawPath(this.contentPath,paintStroke)
        canvas.drawRRect(
            RRect.makeXYWH(this.x,this.y,this.width,this.height,style.roundCorners),
            paintBackground
        )
        if (scale > 0.01f) {
            canvas.save()
            val centerX = this.x + this.width / 2f
            val centerY = this.y + this.height / 2f
            canvas.translate(centerX, centerY)
            val bounds = checkMarkPath.bounds
            val maxDim = maxOf(bounds.width, bounds.height)
            val autoScale = (this.width / maxDim) * 0.5f
            canvas.scale(autoScale * scale, autoScale * scale)
            canvas.translate(-(bounds.left + bounds.width / 2f), -(bounds.top + bounds.height / 2f))
            canvas.drawPath(checkMarkPath, paintCheckMark)
            canvas.restore()
        }

        super.drawContent(canvas)
    }

}


fun Checkbox(checked: Boolean, onCheckedChange: (Boolean) -> Unit,content: () -> Unit) {
    val checkedWidth = BaseCheckBoxWidget(checked, onCheckedChange, DefaultCheckBoxStyle())
    RenderSystem.add(checkedWidth)
    RenderSystem.pushNode(checkedWidth)
    content()
    RenderSystem.popNode()
}