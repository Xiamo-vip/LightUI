package top.xiamoi.lightui.ui.widget

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skia.Paint
import top.xiamoi.lightui.event.EventBus
import top.xiamoi.lightui.event.EventListener
import top.xiamoi.lightui.event.MouseClickEvent
import top.xiamoi.lightui.ui.InputManager
import top.xiamoi.lightui.ui.RenderSystem
import top.xiamoi.lightui.ui.anim.Animator
import top.xiamoi.lightui.ui.anim.effect.ripple.RippleManager
import top.xiamoi.lightui.ui.modifier.Modifier
import top.xiamoi.lightui.ui.modifier.shadow
import top.xiamoi.lightui.ui.node.Node
import top.xiamoi.lightui.utils.RenderUtils


class ButtonWidget(
    private val text: String,
    private val onClick: () -> Unit
) : Node() {

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
        EventBus.subscribe(this)
    }

    override fun initLayout() {
        super.initLayout()

    }

    @EventListener
     fun click(e: MouseClickEvent) {
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

    override fun initPath() {
        if (isHovered) {
            this.contentPath.addPath(RenderUtils.drawRoundRectanglePath(x,y,width,height,12f))

        } else {
            this.contentPath.addPath(RenderUtils.drawRoundRectanglePath(x,y,width,height,12f))
        }

    }

    override fun drawContent(canvas: Canvas) {
        val paint = Paint().apply {
            color = Animator.animateColor(id + "HoverColor", if (isHovered) Color.withA(Color.BLUE,200) else Color.BLUE)
            isAntiAlias = true
        }
        canvas.drawPath(contentPath, paint)
        rippleInfo.apply(canvas,contentPath)

    }


}

fun Button(text : String = "Button", modifier: Modifier = Modifier, onClick : () -> Unit = {}, content : () -> Unit) {
    val btn = ButtonWidget(text, onClick)
    btn.modifier = modifier.shadow()
    RenderSystem.add(btn)
    RenderSystem.pushNode(btn)
    content()
    RenderSystem.popNode()
}