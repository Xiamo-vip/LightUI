package top.xiamoi.lightui.ui.widget

import org.jetbrains.skia.*
import org.jetbrains.skiko.Cursor
import top.xiamoi.lightui.event.EventBus
import top.xiamoi.lightui.event.EventListener
import top.xiamoi.lightui.event.KeyTypedEvent
import top.xiamoi.lightui.event.MouseDragEvent
import top.xiamoi.lightui.event.TextInputEvent
import top.xiamoi.lightui.ui.InputManager
import top.xiamoi.lightui.ui.RenderSystem
import top.xiamoi.lightui.ui.font.FontManager
import top.xiamoi.lightui.ui.font.FontStyle
import top.xiamoi.lightui.ui.node.Node
import java.awt.event.KeyEvent
import kotlin.math.max
import kotlin.math.sin

open class BaseTextFieldWidget(
    private var text: String,
    private val onValueChange: (value: String) -> Unit,
    fontStyle : FontStyle,
    textColor : Int,
    private val isSingleLine: Boolean,

) : Node() {

    private val defaultWidth = 100f

    private val defaultHeight = 30f


    private val textFont = FontManager.buildFont(fontStyle.fontName, fontStyle.fontSize).apply {
        isSubpixel = true
        edging = FontEdging.SUBPIXEL_ANTI_ALIAS
    }

    val metrics = textFont.metrics
    val fontTotalHeight = metrics.descent - metrics.ascent

    private val textPaint = Paint().apply {
        isAntiAlias = true
        color = textColor
    }

    private val cursorPaint = Paint().apply {
        color = textColor
    }

    init {
        EventBus.subscribe(this)

    }

    @EventListener
    override fun onClicked() {
        if (isOnFocus && isHovered) {
            val index = getIndexFromMousePosition()
            InputManager.selectionStart = index
            InputManager.selectionEnd = index
        }
    }

    @EventListener
    fun onMouseDrag(e: MouseDragEvent) {
        if (isOnFocus && isHovered) {
            InputManager.selectionEnd = getIndexFromMousePosition()
        }
    }

    private fun getLineAndCol(globalIndex: Int, lines: List<String>): Pair<Int, Int> {
        var currentLen = 0
        for ((i, line) in lines.withIndex()) {
            if (globalIndex <= currentLen + line.length) {
                return Pair(i, globalIndex - currentLen)
            }
            currentLen += line.length + 1
        }
        return Pair(lines.lastIndex.coerceAtLeast(0), if (lines.isEmpty()) 0 else lines.last().length)
    }

    private fun getIndexFromMousePosition() : Int {
        val lines =  text.split("\n")

        val relativeX = InputManager.mouseX - this.x
        val relativeY = InputManager.mouseY - this.y

        val yOffset = (defaultHeight - fontTotalHeight) / 2f

        val lineIndex = if (isSingleLine) 0  else {
           ( (relativeY - yOffset ) / fontTotalHeight).toInt().coerceIn(0,lines.size - 1)
        }
        val lineText = lines[lineIndex]

        var colIndex = lineText.length

        if (relativeX <= 0) {
            colIndex = 0
        } else {
            for (i in 1..lineText.length) {
                val w = textFont.measureTextWidth(lineText.take(i), textPaint)
                if (w >= relativeX) {
                    val prevW = textFont.measureTextWidth(lineText.take(i - 1), textPaint)
                    colIndex = if (relativeX - prevW < w - relativeX) i - 1 else i
                    break
                }
            }
        }

        var globalIndex = 0
        for (i in 0 until lineIndex) {
            globalIndex += lines[i].length + 1
        }
        return globalIndex + colIndex

    }

    override fun initLayout() {
        this.width = defaultWidth
        this.height = defaultHeight


        if (isSingleLine) {
            if (this.width <  textFont.measureTextWidth(text)) {
                this.width = textFont.measureTextWidth(text)
            } else if (textFont.measureTextWidth(text) < defaultWidth) {
                this.width = defaultWidth
            }
        } else {
            val tokens = text.split("\n")
            val maxWidth = tokens.maxOf { token -> textFont.measureTextWidth(token) }
            if (this.width < maxWidth) {
                this.width = maxWidth
            } else {
                this.width = defaultWidth
            }

            val paddingHeight = defaultHeight - fontTotalHeight
            val requiredHeight = fontTotalHeight * tokens.size + paddingHeight
            this.height = max(requiredHeight, defaultHeight)
        }

        super.initLayout()
    }


    @EventListener
    fun keyTyped(e: KeyTypedEvent) {
        if (!isOnFocus) return
        val keyChar = e.keyEvent.keyChar

        val startIdx = minOf(InputManager.selectionStart, InputManager.selectionEnd).coerceIn(0, text.length)
        val endIdx = maxOf(InputManager.selectionStart, InputManager.selectionEnd).coerceIn(0, text.length)

        if (keyChar >= ' ' || (!isSingleLine && keyChar == '\n')) {
            text = text.substring(0, startIdx) + keyChar + text.substring(endIdx)
            val newPos = startIdx + 1
            InputManager.selectionStart = newPos
            InputManager.selectionEnd = newPos
            onValueChange(text)
        } else if (keyChar.code == KeyEvent.VK_BACK_SPACE) {
            if (startIdx != endIdx) {
                text = text.removeRange(startIdx, endIdx)
                InputManager.selectionStart = startIdx
                InputManager.selectionEnd = startIdx
                onValueChange(text)
            } else if (startIdx > 0) {
                text = text.removeRange(startIdx - 1, startIdx)
                InputManager.selectionStart = startIdx - 1
                InputManager.selectionEnd = startIdx - 1
                onValueChange(text)
            }
        }
    }

    @EventListener
    fun onTextInput(e: TextInputEvent) {
        if (!isOnFocus) return
        val startIdx = minOf(InputManager.selectionStart, InputManager.selectionEnd).coerceIn(0, text.length)
        val endIdx = maxOf(InputManager.selectionStart, InputManager.selectionEnd).coerceIn(0, text.length)

        text = text.substring(0, startIdx) + e.text + text.substring(endIdx)
        val newPos = startIdx + e.text.length
        InputManager.selectionStart = newPos
        InputManager.selectionEnd = newPos
        onValueChange(text)
    }

    private fun drawSelection(canvas: Canvas) {
        if (!isOnFocus || InputManager.selectionStart == InputManager.selectionEnd) return
        val startIdx = minOf(InputManager.selectionStart, InputManager.selectionEnd).coerceIn(0, text.length)
        val endIdx = maxOf(InputManager.selectionStart, InputManager.selectionEnd).coerceIn(0, text.length)
        val lines = text.split("\n")
        val (startLine, startCol) = getLineAndCol(startIdx, lines)
        val (endLine, endCol) = getLineAndCol(endIdx, lines)
        val yOffset = (defaultHeight - fontTotalHeight) / 2f
        val selectionPaint = Paint().apply { color = Color.makeARGB(100, 0, 120, 215) }
        for (i in startLine..endLine) {
            val lineY = this.y + yOffset + i * fontTotalHeight
            val lineText = lines[i]
            val startTextX = if (i == startLine) textFont.measureTextWidth(lineText.take(startCol), textPaint) else 0f
            val endTextX = if (i == endLine) textFont.measureTextWidth(lineText.take(endCol), textPaint) else textFont.measureTextWidth(lineText, textPaint) + 8f
            canvas.drawRect(Rect.makeLTRB(x + startTextX, lineY, x + endTextX, lineY + fontTotalHeight), selectionPaint)
        }
    }

    override fun drawContent(canvas: Canvas) {
        canvas.save()
        if (isHovered) InputManager.cursorStyle = Cursor.TEXT_CURSOR
        drawSelection(canvas)
        if (isSingleLine) drawLine(canvas) else drawLines(canvas)
        if (isOnFocus) {
            val cursorIdx = InputManager.selectionEnd.coerceIn(0, text.length)
            val lines = text.split("\n")
            val (cLine, cCol) = getLineAndCol(cursorIdx, lines)

            val cursorXOffset = textFont.measureTextWidth(lines[cLine].substring(0, cCol), textPaint)
            val yOffset = (defaultHeight - fontTotalHeight) / 2f
            val cursorYOffset = yOffset + cLine * fontTotalHeight

            if (InputManager.selectionStart == InputManager.selectionEnd) {
                drawBlinkingCursor(canvas, cursorXOffset, cursorYOffset, fontTotalHeight)
            }

            InputManager.imeX = x + cursorXOffset
            InputManager.imeY = y + cursorYOffset
            InputManager.imeHeight = fontTotalHeight
        }
        canvas.restore()
    }

    private fun drawBlinkingCursor(canvas: Canvas, cursorXOffset: Float, cursorYOffset: Float, cursorHeight: Float) {
        val alphaRatio = (sin(RenderSystem.timeMills / 150.0) + 1.0) / 2.0
        cursorPaint.alpha = (alphaRatio * 255).toInt()
        val cursorX = x + cursorXOffset
        val cursorY = y + cursorYOffset
        canvas.drawRect(Rect(cursorX, cursorY, cursorX + 1.5f, cursorY + cursorHeight), cursorPaint)
    }

    private fun drawLine(canvas: Canvas) {
        val yOffset = (height - fontTotalHeight) / 2f
        val baselineY = y + yOffset - metrics.ascent
        canvas.clipPath(Path().addRect(Rect.makeXYWH(x, y, width, height)))
        canvas.drawString(text, this.x, baselineY, textFont, textPaint)
    }

    private fun drawLines(canvas: Canvas) {
        val yOffset = (defaultHeight - fontTotalHeight) / 2f
        val baselineY = y + yOffset - metrics.ascent
        canvas.clipPath(Path().addRect(Rect.makeXYWH(x, y, width, height)))
        val tokens = text.split("\n")
        tokens.forEachIndexed { index, string ->
            canvas.drawString(string, this.x, baselineY + (fontTotalHeight * index), textFont, textPaint)
        }



    }

}

