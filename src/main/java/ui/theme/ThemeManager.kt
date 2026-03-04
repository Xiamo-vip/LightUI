package ui.theme

import ui.anim.Animator
import ui.theme.themes.Dark
import java.lang.reflect.Proxy

object ThemeManager {
    var targetTheme: Theme = Dark()
    fun switchTheme(theme: Theme) {
        targetTheme = theme
    }

    /**
     * 反射动态生成color
     */
    val colors: Theme = Proxy.newProxyInstance(
        Theme::class.java.classLoader,
        arrayOf(Theme::class.java)
    ) { _, method, args ->
        if (method.name.startsWith("get") && method.returnType == Int::class.java) {
            val targetColor = method.invoke(targetTheme) as Int
            return@newProxyInstance Animator.animateColor("theme_${method.name}", targetColor, 300L)
        }
        return@newProxyInstance if (args != null) {
            method.invoke(targetTheme, *args)
        } else {
            method.invoke(targetTheme)
        }
    } as Theme
}