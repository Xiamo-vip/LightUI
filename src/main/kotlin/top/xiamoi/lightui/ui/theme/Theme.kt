package top.xiamoi.lightui.ui.theme

interface Theme {
    var primary: Int
    var onPrimary: Int
    var primaryContainer: Int
    var onPrimaryContainer: Int

    var secondary: Int
    var onSecondary: Int
    var secondaryContainer: Int
    var onSecondaryContainer: Int

    var tertiary: Int
    var onTertiary: Int
    var tertiaryContainer: Int
    var onTertiaryContainer: Int

    var error: Int
    var onError: Int
    var errorContainer: Int
    var onErrorContainer: Int

    var background: Int
    var onBackground: Int
    var surface: Int
    var onSurface: Int
    var surfaceVariant: Int
    var onSurfaceVariant: Int

    var outline: Int
    var outlineVariant: Int
    var scrim: Int

    var inverseSurface: Int
    var inverseOnSurface: Int
    var inversePrimary: Int

    var surfaceDim: Int
    var surfaceBright: Int
    var surfaceContainerLowest: Int
    var surfaceContainerLow: Int
    var surfaceContainer: Int
    var surfaceContainerHigh: Int
    var surfaceContainerHighest: Int
}