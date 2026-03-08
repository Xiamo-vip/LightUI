package top.xiamoi.lightui

import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import org.jetbrains.skiko.SkikoRenderDelegate
import top.xiamoi.lightui.adapter.KeyBoardAdapter
import top.xiamoi.lightui.adapter.MouseAdapter
import top.xiamoi.lightui.event.TextInputEvent
import top.xiamoi.lightui.ui.InputManager
import top.xiamoi.lightui.ui.RenderSystem
import top.xiamoi.lightui.ui.font.CompanionFonts
import top.xiamoi.lightui.ui.font.FontStyle
import top.xiamoi.lightui.ui.layout.Column
import top.xiamoi.lightui.ui.layout.Row
import top.xiamoi.lightui.ui.modifier.Modifier
import top.xiamoi.lightui.ui.modifier.background
import top.xiamoi.lightui.ui.modifier.fillMaxSize
import top.xiamoi.lightui.ui.modifier.margin
import top.xiamoi.lightui.ui.theme.ThemeManager
import top.xiamoi.lightui.ui.theme.themes.Dark
import top.xiamoi.lightui.ui.theme.themes.Light
import top.xiamoi.lightui.ui.widget.Button
import top.xiamoi.lightui.ui.widget.Text
import top.xiamoi.lightui.ui.widget.TextField
import java.awt.Cursor
import java.awt.Dimension
import java.awt.Rectangle
import java.awt.event.InputMethodEvent
import java.awt.event.InputMethodListener
import java.awt.font.TextHitInfo
import java.awt.im.InputMethodRequests
import java.text.AttributedCharacterIterator
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants
import kotlin.math.sin

object SkiaRender {
    val skiaLayer = object  : SkiaLayer()  {
        override fun getInputMethodRequests(): InputMethodRequests {
            return object : InputMethodRequests {
                override fun getTextLocation(offset: TextHitInfo?): Rectangle? {
                    return try {
                        val p = locationOnScreen
                        Rectangle(
                            (p.x + InputManager.imeX).toInt(),
                            (p.y + InputManager.imeY + InputManager.imeHeight).toInt(),
                            0, 0
                        )
                    } catch (e: Exception) {
                        Rectangle(0, 0, 0, 0)
                    }
                }

                override fun getLocationOffset(x: Int, y: Int): TextHitInfo? = null

                override fun getInsertPositionOffset(): Int = 0

                override fun getCommittedText(beginIndex: Int, endIndex: Int, attributes: Array<out AttributedCharacterIterator.Attribute>?): AttributedCharacterIterator? = null

                override fun getCommittedTextLength(): Int = 0

                override fun cancelLatestCommittedText(attributes: Array<out AttributedCharacterIterator.Attribute>?): AttributedCharacterIterator? = null

                override fun getSelectedText(attributes: Array<out AttributedCharacterIterator.Attribute>?): AttributedCharacterIterator? = null

            }
        }
    }
    private var lastFrameTime = 0L
    private var fps = 0

    private fun updateFPS(nanoTime: Long) {
        if (lastFrameTime != 0L) {
            val deltaTime = nanoTime - lastFrameTime
            fps = (1_000_000_000L / deltaTime).toInt()
        }
        lastFrameTime = nanoTime
    }
    fun create()  {
        var inputText1 = ""
        var inputText2 = ""
        skiaLayer.renderDelegate = SkiaLayerRenderDelegate(skiaLayer, object : SkikoRenderDelegate {
            override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
                updateFPS(nanoTime)
                RenderSystem.begin(width,height)
                fun rainbow(): Int {
                    val time = System.nanoTime() / 1e9

                    val r = ((sin(time) + 1) * 127.5).toInt()
                    val g = ((sin(time + 2.094) + 1) * 127.5).toInt()
                    val b = ((sin(time + 4.188) + 1) * 127.5).toInt()

                    return (255 shl 24) or (r shl 16) or (g shl 8) or b
                }
                Column(
                    modifier = Modifier.fillMaxSize()
                        .background(ThemeManager.colors.background)
                ) {
                    Column {
                        Text("FPS：${fps.toString()}", fontStyle = FontStyle(CompanionFonts.MiSans_Medium.name,50f,"normal"), color =
                            rainbow()
                        )
                        Row {
                            Button(
                                "测试",
                                modifier = Modifier,
                                onClick = {
                                   ThemeManager.switchTheme(
                                       Dark()
                                    )
                                }) {
                                Text("测试1")
                            }
                           Button(
                                "测试",
                                modifier = Modifier.margin(left = 20),
                                onClick = {
                                    println("yes")
                                   ThemeManager.switchTheme(
                                       Light()
                                    )
                                }) {
                                Text("测试2")
                            }
                        }
                    }
                    Text("当前主题：${ThemeManager.targetTheme.javaClass.simpleName}", color = ThemeManager.colors.onBackground)
                    testUI(canvas)
                    TextField(text = inputText1,onValueChange = {inputText1 = it}, fontStyle = FontStyle.defaultFontStyle, ThemeManager.colors.secondary)
                    TextField(text = inputText2,onValueChange = {inputText2 = it}, fontStyle = FontStyle.defaultFontStyle, ThemeManager.colors.secondary)
                }
                RenderSystem.end(canvas)

                if (skiaLayer.cursor.type != InputManager.cursorStyle) {
                    skiaLayer.cursor = Cursor(InputManager.cursorStyle)
                }
                InputManager.cursorStyle = Cursor.DEFAULT_CURSOR

                skiaLayer.needRedraw()
            }



        })

        skiaLayer.addMouseListener(MouseAdapter)
        skiaLayer.addMouseMotionListener(MouseAdapter)
        skiaLayer.addKeyListener(KeyBoardAdapter)
        skiaLayer.isFocusable = true
        skiaLayer.requestFocus()

        skiaLayer.enableInputMethods(true)
        skiaLayer.addInputMethodListener(object : InputMethodListener {
            override fun inputMethodTextChanged(event: InputMethodEvent) {
                val textIterator = event.text
                if (textIterator != null && event.committedCharacterCount > 0) {
                    val sb = java.lang.StringBuilder()
                    var c = textIterator.first()
                    for (i in 0 until event.committedCharacterCount) {
                        sb.append(c)
                        c = textIterator.next()
                    }
                    val committedText = sb.toString()
                    TextInputEvent(committedText).broadcast()
                }
            }
            override fun caretPositionChanged(event: InputMethodEvent?) {
            }
        })

        SwingUtilities.invokeLater {
            val window = JFrame("Skiko example").apply {
                defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
                preferredSize = Dimension(800, 600)
            }
            skiaLayer.attachTo(window.contentPane)
            skiaLayer.needRedraw()
            window.pack()
            window.isVisible = true
        }
    }
}

fun testUI(canvas: Canvas) {
    Column {
        Button(onClick = {
            println(999)
        }) {
            Column {
                Text("测试")
                Text("测试")
                Text("测试")
                Text("测试")
                Text("测试")
                Row {
                    Text("测试")
                    Text("测试")
                    Text("测试")
                    Text("测试")
                    Text("测试")
                    Column {
                        Text("测试")
                        Text("测试")
                        Text("测试")
                    }
                }
            }
        }
    }

}