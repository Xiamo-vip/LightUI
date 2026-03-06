package top.xiamoi.lightui.ui.font

    open class FontStyle (
        val fontName: String,
        val fontSize: Float,
        val fontFamily: String,
    ) {
        companion object {
            val defaultFontStyle  = FontStyle(FontManager.defaultFont.name,20f,"normal")
        }
    }

