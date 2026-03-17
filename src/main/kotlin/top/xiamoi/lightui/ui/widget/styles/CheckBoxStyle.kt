package top.xiamoi.lightui.ui.widget.styles

import org.jetbrains.skia.Color


open class CheckBoxStyle (
    var strokeColorOnDefault : Int,
    var strokeColorOnHover : Int,
    var strokeColorOnCheck : Int,
    var strokeWidthOnDefault : Float,
    var backgroundColorOnDefault : Int,
    var backgroundColorOnChecked : Int,
    var roundCorners : Float,
)

class DefaultCheckBoxStyle(
    strokeColorOnDefault : Int = Color.makeRGB(73, 69, 79),
    strokeColorOnHover : Int = Color.makeRGB(73, 69, 79),
    strokeColorOnCheck : Int = Color.makeARGB(0,0,0,0),
    strokeWidthOnDefault : Float = 3f,
    backgroundColorOnDefault : Int = Color.makeARGB(0,0,0,0),
    backgroundColorOnChecked : Int = Color.makeRGB(103, 80, 164),
    roundCorners : Float = 3f,
) : CheckBoxStyle (
    strokeColorOnDefault,
    strokeColorOnHover,
    strokeColorOnCheck,
    strokeWidthOnDefault,
    backgroundColorOnDefault,
    backgroundColorOnChecked,
    roundCorners
)


