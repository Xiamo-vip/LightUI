package top.xiamoi.lightui

import org.jetbrains.skia.Canvas
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import org.jetbrains.skiko.SkikoRenderDelegate
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
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants
import kotlin.math.sin

object SkiaRender {
    val skiaLayer = SkiaLayer()
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
                }
                RenderSystem.end(canvas)
                skiaLayer.needRedraw()
            }



        })

        skiaLayer.addMouseListener(_root_ide_package_.top.xiamoi.lightui.adapter.MouseAdapter)
        skiaLayer.addMouseMotionListener(_root_ide_package_.top.xiamoi.lightui.adapter.MouseAdapter)

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