package top.xiamoi.lightui.resource

import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image
import top.xiamoi.lightui.cache.CaCheManager
import java.io.File
import java.io.InputStream

fun bitmapFromPath(path: String): Bitmap {
    return CaCheManager.bitmapCache.getOrPut(path) {
        val inputStream: InputStream = if (path.startsWith("/")) {
            CaCheManager::class.java.getResourceAsStream(path)
                ?: throw IllegalArgumentException("在资源目录中找不到图片: $path")
        } else {

            val file = File(path)
            if (!file.exists()) throw IllegalArgumentException("本地文件不存在: $path")
            file.inputStream()
        }

        inputStream.use { stream ->
            val bytes = stream.readAllBytes()
            val skiaImage = Image.makeFromEncoded(bytes)
            Bitmap.makeFromImage(skiaImage)
        }
    }
}