package top.xiamoi.lightui.ui.widget

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Path
import org.jetbrains.skia.RRect
import top.xiamoi.lightui.ui.InputManager
import top.xiamoi.lightui.ui.RenderSystem
import top.xiamoi.lightui.ui.anim.Animator
import top.xiamoi.lightui.ui.anim.effect.ripple.RippleInfo
import top.xiamoi.lightui.ui.anim.effect.ripple.RippleManager
import top.xiamoi.lightui.ui.layout.values.MarginValue
import top.xiamoi.lightui.ui.modifier.Modifier
import top.xiamoi.lightui.ui.node.Node
import top.xiamoi.lightui.utils.RenderUtils
import java.awt.event.MouseEvent


class ButtonWidget(
    private val text: String,
    private val onClick: () -> Unit
) : top.xiamoi.lightui.ui.node.Node() {

    private val rippleInfo  = RippleManager.getRippleInfo(id)

    init {
        this.width = 100f
        this.height = 20f
        this.contentPadding.apply {
            this.top = 5
            this.bottom = 5
            this.start = 10
            this.end = 10
        }
    }

    override fun initLayout() {
        super.initLayout()

    }

    override fun click(e: MouseEvent) {
        super.click(e)
        if (isHovered) {
            rippleInfo.spawnRipple(InputManager.mouseX,InputManager.mouseY, Color.WHITE, radius = width)
            onClick()
        }
    }

    override fun findHit(mouseX: Float, mouseY: Float): Node? {
        // 强制拦截
        val hit = super.findHit(mouseX, mouseY)
        if (hit != null) {
            return this
        }

        return null
    }

    override fun drawContent(canvas: Canvas) {
        super.drawContent(canvas)
        val paint = Paint().apply {
            color = Animator.animateColor(id + "HoverColor", if (isHovered) Color.withA(Color.BLUE,200) else Color.BLUE)
            isAntiAlias = true
        }
        if (isHovered) {
           this.contentPath.addPath(RenderUtils.drawRoundRectanglePath(x,y,width,height,8f))
            RRect.makeXYWH(x, y, width, height, 8f)

        } else {
            this.contentPath.addPath(RenderUtils.drawRoundRectanglePath(x,y,width,height,8f))
        }


        canvas.drawPath(contentPath, paint)
        rippleInfo.apply(canvas,contentPath)

    }


}

fun Button(text : String = "Button", modifier: Modifier = Modifier, onClick : () -> Unit = {}, content : () -> Unit) {
    val btn = ButtonWidget(text, onClick)
    btn.modifier = modifier
    RenderSystem.add(btn)
    RenderSystem.pushNode(btn)
    content()
    RenderSystem.popNode()
}