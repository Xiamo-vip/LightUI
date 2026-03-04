import adapter.MouseAdapter
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import org.jetbrains.skiko.SkikoRenderDelegate
import ui.InputManager
import ui.RenderSystem
import ui.layout.Column
import ui.layout.Row
import ui.modifier.Modifier
import ui.modifier.alpha
import ui.modifier.hoverScale
import ui.theme.ThemeManager
import ui.theme.themes.Dark
import ui.theme.themes.Light
import ui.widget.Button
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

object SkiaRender {
    val skiaLayer = SkiaLayer()
    fun create()  {
        skiaLayer.renderDelegate = SkiaLayerRenderDelegate(skiaLayer, object : SkikoRenderDelegate {
            override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
                RenderSystem.begin()
                canvas.clear(ThemeManager.colors.secondary)
                Column {
                    Button("OK", modifier = Modifier
                        .hoverScale(targetScale = 1.15f)
                    ) {
                        ThemeManager.switchTheme(Dark())
                    }

                    Button("OK", modifier = Modifier
                        .hoverScale(targetScale = 1.15f)
                    ) {
                        ThemeManager.switchTheme(Light())
                    }
                    Row(Modifier.alpha(0.8f)) {
                        Button("OK", modifier = Modifier
                            .hoverScale(targetScale = 1.15f)
                        ) {
                        }
                        Button("OK", modifier = Modifier
                            .hoverScale(targetScale = 1.15f)
                        ) {
                        }
                    }
                }

                RenderSystem.end(canvas)
            }



        })

        skiaLayer.addMouseListener(MouseAdapter)
        skiaLayer.addMouseMotionListener(MouseAdapter)

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