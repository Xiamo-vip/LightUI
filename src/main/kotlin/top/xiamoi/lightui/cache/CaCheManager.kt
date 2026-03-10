package top.xiamoi.lightui.cache

import org.jetbrains.skia.Bitmap

object CaCheManager {
    private val caches = mutableListOf<MutableMap<*, *>>()

    val bitmapCache = mutableMapOf<String, Bitmap>().also { caches.add(it) }


    fun clear() {
        caches.clear()
    }
}