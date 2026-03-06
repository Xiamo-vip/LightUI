package top.xiamoi.lightui.ui.font


import org.jetbrains.skia.Data
import org.jetbrains.skia.Font
import org.jetbrains.skia.Typeface
import top.xiamoi.lightui.SkiaRender

object FontManager {
    val fontsData = mutableMapOf<String, Typeface>()
    val defaultFont = CompanionFonts.MiSans_Normal


    init {
        CompanionFonts.entries.forEach {
            fontsData[it.name] = Typeface.makeFromData(
                Data.makeFromBytes(SkiaRender::class.java.getResourceAsStream("/fonts/" + it.fileName)!!.readAllBytes())
            )
        }
    }


    fun buildFont(font : String, fontSize :  Float) : Font {
        return Font(typeface = fontsData[font] , size = fontSize)
    }

    fun registerFont(name : String, bytes : ByteArray) {
        val data = Data.makeFromBytes(bytes)
        fontsData[name] = Typeface.makeFromData(data)
    }




}


