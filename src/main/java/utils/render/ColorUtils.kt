package utils.render

import org.jetbrains.skia.Color

object ColorUtils {


    fun lerpARGB(startColor: Int, endColor: Int, fraction: Float): Int {
        val a = (Color.getA(startColor) + (Color.getA(endColor) - Color.getA(startColor)) * fraction).toInt()
        val r = (Color.getR(startColor) + (Color.getR(endColor) - Color.getR(startColor)) * fraction).toInt()
        val g = (Color.getG(startColor) + (Color.getG(endColor) - Color.getG(startColor)) * fraction).toInt()
        val b = (Color.getB(startColor) + (Color.getB(endColor) - Color.getB(startColor)) * fraction).toInt()
        return Color.makeARGB(a, r, g, b)
    }


    fun lerpHSB(startColor: Int, endColor: Int, fraction: Float): Int {
        val startA = Color.getA(startColor)
        val endA = Color.getA(endColor)
        val outA = (startA + (endA - startA) * fraction).toInt()

        val startHSB = java.awt.Color.RGBtoHSB(
            Color.getR(startColor), Color.getG(startColor), Color.getB(startColor), null
        )
        val endHSB = java.awt.Color.RGBtoHSB(
            Color.getR(endColor), Color.getG(endColor), Color.getB(endColor), null
        )

        val outS = startHSB[1] + (endHSB[1] - startHSB[1]) * fraction
        val outB = startHSB[2] + (endHSB[2] - startHSB[2]) * fraction
        var startH = startHSB[0]
        var endH = endHSB[0]
        val dH = endH - startH

        if (dH > 0.5f) {
            startH += 1f
        } else if (dH < -0.5f) {
            endH += 1f
        }

        var outH = startH + (endH - startH) * fraction
        outH = (outH % 1f + 1f) % 1f

        val rgb = java.awt.Color.HSBtoRGB(outH, outS, outB)

        val outR = (rgb shr 16) and 0xFF
        val outG = (rgb shr 8) and 0xFF
        val outB_rgb = rgb and 0xFF

        return Color.makeARGB(outA, outR, outG, outB_rgb)
    }
}