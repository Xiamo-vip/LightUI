package top.xiamoi.lightui.ui.widget.styles

import org.jetbrains.skija.Color

open class OutlineTextFieldStyle(
    var strokeColorOnDefault : Int,
    var strokeColorOnHover : Int,
    var strokeColorOnFocus : Int,
    var strokeWidthOnDefault : Float,
    var strokeWidthOnHover : Float,
    var strokeWidthOnFocus : Float,
    var roundCorners : Float,
)

class DefaultOutlineTextFieldStyle(
    strokeColorOnDefault : Int = Color.makeRGB(36, 22, 201),
    strokeColorOnHover : Int = Color.makeRGB(215, 80, 59),
    strokeColorOnFocus : Int = Color.makeRGB(67, 173, 234),
    strokeWidthOnDefault : Float = 2f,
    strokeWidthOnHover : Float = 1.5f,
    strokeWidthOnFocus : Float = 1.5f,
    roundCorners : Float = 10f,
) : OutlineTextFieldStyle(
    strokeColorOnDefault = strokeColorOnDefault,
    strokeColorOnHover = strokeColorOnHover,
    strokeColorOnFocus = strokeColorOnFocus,
    strokeWidthOnDefault = strokeWidthOnDefault,
    strokeWidthOnHover = strokeWidthOnHover,
    strokeWidthOnFocus = strokeWidthOnFocus,
    roundCorners = roundCorners,

)