package top.xiamoi.lightui.ui.widget

import org.jetbrains.skia.*
import org.jetbrains.skiko.Cursor
import top.xiamoi.lightui.event.EventBus
import top.xiamoi.lightui.event.EventListener
import top.xiamoi.lightui.event.KeyTypedEvent
import top.xiamoi.lightui.ui.InputManager
import top.xiamoi.lightui.ui.RenderSystem
import top.xiamoi.lightui.ui.font.FontManager
import top.xiamoi.lightui.ui.font.FontStyle
import top.xiamoi.lightui.ui.node.Node
import java.awt.event.KeyEvent
import kotlin.math.sin

open class BaseTextFieldWidget(
    private var text: String,
    private val onValueChange: (value: String) -> Unit,
    fontStyle : FontStyle,
    textColor : Int,

) : Node() {

    private val textFont = FontManager.buildFont(fontStyle.fontName, fontStyle.fontSize).apply {
        isSubpixel = true
        edging = FontEdging.SUBPIXEL_ANTI_ALIAS
    }

    private val textPaint = Paint().apply {
        isAntiAlias = true
        color = textColor
    }

    private val cursorPaint = Paint().apply {
        color = textColor
    }

    init {
        EventBus.subscribe(this)
        this.width = 100f
        this.height = textFont.metrics.descent - textFont.metrics.ascent + 10f
    }



    @EventListener
    fun keyTyped(e : KeyTypedEvent) {

        if (!isOnFocus) return

        val keyChar = e.keyEvent.keyChar
        if (keyChar >= ' ') {
            text += keyChar
            onValueChange(text)
        } else {
            if (keyChar.code == KeyEvent.VK_BACK_SPACE && text.isNotEmpty()) {
                text = text.dropLast(1)
                onValueChange(text)
            }

        }
    }

    @EventListener
    fun onTextInput(e: top.xiamoi.lightui.event.TextInputEvent) {
        if (!isOnFocus) return
        text += e.text
        onValueChange(text)
    }

    override fun drawContent(canvas: Canvas) {
        canvas.save()
        canvas.clipPath(Path().addRect(Rect.makeXYWH(x, y, width, height)))

        if (isHovered) InputManager.cursorStyle = Cursor.TEXT_CURSOR

        val metrics = textFont.metrics
        val fontTotalHeight = metrics.descent - metrics.ascent
        val yOffset = (height - fontTotalHeight) / 2f
        val baselineY = y + yOffset - metrics.ascent


        canvas.drawString(text, x, baselineY, textFont, textPaint)


        if (isOnFocus) {
            drawBlinkingCursor(canvas, yOffset, fontTotalHeight)
            val textWidth = textFont.measureTextWidth(text, textPaint)
            InputManager.imeX = x + textWidth + 4f
            InputManager.imeY = y + yOffset
            InputManager.imeHeight = fontTotalHeight
        }

        canvas.restore()
    }

    private fun drawBlinkingCursor(canvas: Canvas, yOffset: Float, cursorHeight: Float) {
        val alphaRatio = (sin(RenderSystem.timeMills / 150.0) + 1.0) / 2.0
        cursorPaint.alpha = (alphaRatio * 255).toInt()
        val textWidth = textFont.measureTextWidth(text, textPaint)
        val cursorX = x + textWidth + 4f
        val cursorY = y + yOffset
        canvas.drawRect(
            Rect(cursorX, cursorY, cursorX + 2f, cursorY + cursorHeight),
            cursorPaint
        )
    }


}

