package top.xiamoi.lightui.ui.widget

import org.jetbrains.skia.*
import top.xiamoi.lightui.event.EventBus
import top.xiamoi.lightui.resource.bitmapFromPath
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
    val icon = Image.makeFromBitmap(bitmap = bitmapFromPath("/icon/checked.png"))
    init {
        this.width = 30f
        this.height = 30f
        EventBus.subscribe(this)

    }

    override fun onClicked() {
        onCheckedChange(!checked)
    }

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


    override fun initPath() {
        this.contentPath.addRRect(
            RRect.makeXYWH(this.x,this.y,this.width,this.height,5f)
        )
        super.initPath()
    }

    override fun drawContent(canvas: Canvas) {

        paintBackground.alpha = Animator.animateFloat(this.id + "backgroundOnChecked",if (checked) 255f else 0f).toInt()
        val scale = Animator.animateFloat(this.id + "iconScale",if (checked) 1f else 0f)

        val actualIconScaledWidth = icon.width * (this.contentWidth / icon.width)
        val actualIconScaledHeight = icon.height * (this.contentHeight / icon.height)

        val offsetX = (this.contentWidth - actualIconScaledWidth) / 2f
        val offsetY = (this.contentHeight - actualIconScaledHeight) / 2f

        paintStroke.alpha = 255 - paintBackground.alpha
        canvas.drawPath(this.contentPath,paintStroke)
        canvas.drawRRect(
            RRect.makeXYWH(this.x,this.y,this.width,this.height,style.roundCorners),
            paintBackground
        )

        canvas.save()
        canvas.translate(
            this.x + this.contentPadding.start + offsetX,
            this.y + this.contentPadding.top + offsetY
        )
        canvas.scale((this.contentWidth / icon.width),  (this.contentHeight / icon.height))
        canvas.scale(scale, scale)
        canvas.drawImage(icon,0f,0f,null)
        canvas.restore()



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