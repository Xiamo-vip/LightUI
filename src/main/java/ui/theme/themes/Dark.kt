package ui.theme.themes

import ui.theme.Theme
import java.awt.Color

class Dark : Theme {
    override var primary: Int = Color(0xAAC7FF).rgb
    override var onPrimary: Int = Color(0x0A305F).rgb
    override var primaryContainer: Int = Color(0x284777).rgb
    override var onPrimaryContainer: Int = Color(0xD6E3FF).rgb

    override var secondary: Int = Color(0xBEC6DC).rgb
    override var onSecondary: Int = Color(0x283141).rgb
    override var secondaryContainer: Int = Color(0x3E4759).rgb
    override var onSecondaryContainer: Int = Color(0xDAE2F9).rgb

    override var tertiary: Int = Color(0xDDBCE0).rgb
    override var onTertiary: Int = Color(0x3F2844).rgb
    override var tertiaryContainer: Int = Color(0x573E5C).rgb
    override var onTertiaryContainer: Int = Color(0xFAD8FD).rgb

    override var error: Int = Color(0xFFB4AB).rgb
    override var onError: Int = Color(0x690005).rgb
    override var errorContainer: Int = Color(0x93000A).rgb
    override var onErrorContainer: Int = Color(0xFFDAD6).rgb

    override var background: Int = Color(0x111318).rgb
    override var onBackground: Int = Color(0xE2E2E9).rgb
    override var surface: Int = Color(0x111318).rgb
    override var onSurface: Int = Color(0xE2E2E9).rgb
    override var surfaceVariant: Int = Color(0x44474E).rgb
    override var onSurfaceVariant: Int = Color(0xC4C6D0).rgb

    override var outline: Int = Color(0x8E9099).rgb
    override var outlineVariant: Int = Color(0x44474E).rgb
    override var scrim: Int = Color(0x000000).rgb

    override var inverseSurface: Int = Color(0xE2E2E9).rgb
    override var inverseOnSurface: Int = Color(0x2E3036).rgb
    override var inversePrimary: Int = Color(0x415F91).rgb

    override var surfaceDim: Int = Color(0x111318).rgb
    override var surfaceBright: Int = Color(0x37393E).rgb
    override var surfaceContainerLowest: Int = Color(0x0C0E13).rgb
    override var surfaceContainerLow: Int = Color(0x191C20).rgb
    override var surfaceContainer: Int = Color(0x1D2024).rgb
    override var surfaceContainerHigh: Int = Color(0x282A2F).rgb
    override var surfaceContainerHighest: Int = Color(0x33353A).rgb
}