package top.xiamoi.lightui.ui.widget

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.FontEdging
import org.jetbrains.skia.Paint
import top.xiamoi.lightui.ui.RenderSystem
import top.xiamoi.lightui.ui.font.FontManager
import top.xiamoi.lightui.ui.font.FontStyle
import top.xiamoi.lightui.ui.modifier.Modifier
import top.xiamoi.lightui.ui.theme.ThemeManager

class TextWidget (val text: String, val textColor: Int, val fonStyle: FontStyle,): top.xiamoi.lightui.ui.node.Node() {
    val applyFont = FontManager.buildFont(fonStyle.fontName,fonStyle.fontSize).apply {
        isSubpixel = true
        edging = FontEdging.SUBPIXEL_ANTI_ALIAS
    }
    val paint = Paint().apply {
        this.isAntiAlias = true
        this.color = textColor
    }
    init {
        this.isIgnoreFocus = true
    }
    override fun initLayout() {

        val measure = applyFont.measureText(text,paint)
        this.width = measure.width
        this.height = applyFont.metrics.descent - applyFont.metrics.ascent
        super.initLayout()
    }

    override fun drawContent(canvas: Canvas) {
        super.drawContent(canvas)
        val baselineY = y - applyFont.metrics.ascent
        canvas.drawString(
            text,x,baselineY,applyFont,paint
        )


    }


}

fun Text(text:String, modifier: Modifier = Modifier.Companion, color: Int = ThemeManager.colors.onBackground, fontStyle: FontStyle = FontStyle.defaultFontStyle ) {
    val textNode = TextWidget(text, color, fontStyle)
    textNode.modifier = modifier
    RenderSystem.add(textNode)
}