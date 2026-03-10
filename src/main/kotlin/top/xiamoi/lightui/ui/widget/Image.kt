package top.xiamoi.lightui.ui.widget

import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Image
import org.jetbrains.skia.Rect
import top.xiamoi.lightui.ui.RenderSystem
import top.xiamoi.lightui.ui.layout.scale.ImageScale
import top.xiamoi.lightui.ui.layout.scale.ImageScale.*
import top.xiamoi.lightui.ui.modifier.Modifier
import top.xiamoi.lightui.ui.node.Node
import kotlin.math.max
import kotlin.math.min

class ImageWidget(private val bitmap: Bitmap,private val imageScale: ImageScale) : Node() {
    private var scaleX = 1f
    private var scaleY = 1f

    private var offsetX = 0f
    private var offsetY = 0f

    val image = Image.makeFromBitmap(bitmap)

    init {
        this.width = 200f
        this.height = 200f
    }

    override fun initLayout() {
        super.initLayout()
        when(imageScale) {
            Fit -> {
                scaleX = min(this.contentWidth / image.width,this.contentHeight / image.height)
                scaleY = scaleX
            }
            Crop -> {
                scaleX = max(this.contentWidth / image.width,this.contentHeight / image.height)
                scaleY = scaleX
            }
            FillBounds -> {
                scaleX = this.contentWidth / image.width
                scaleY = this.contentHeight / image.height
            }
            FillWidth -> {
                scaleX = this.contentWidth / image.width
                scaleY  = scaleX
            }
            FillHeight -> {
                scaleX = this.contentHeight / image.height
                scaleY = scaleX
            }
            Inside -> {
                scaleX = min(1f, min(this.contentWidth/image.width, this.contentHeight/image.height))
                scaleY = scaleX
            }
            None -> {
                scaleX = 1f
                scaleY = 1f
            }
            Center -> {
                scaleX = 1f
                scaleY = 1f
            }
        }
        val actualScaledWidth = image.width * scaleX
        val actualScaledHeight = image.height * scaleY


        offsetX = (this.contentWidth - actualScaledWidth) / 2f
        offsetY = (this.contentHeight - actualScaledHeight) / 2f

        if (imageScale == None) {
            offsetX = 0f
            offsetY = 0f
        }


    }

    override fun initPath() {
        this.contentPath.addRect(Rect.makeXYWH(this.x,this.y,this.width,this.height))
    }

    override fun drawContent(canvas: Canvas) {

        canvas.clipPath(this.contentPath)
        canvas.save()
        canvas.translate(
            this.x + this.contentPadding.start + offsetX,
            this.y + this.contentPadding.top + offsetY
        )
        canvas.scale(scaleX, scaleY)
        canvas.drawImage(image = image, 0f, 0f, null)
        canvas.restore()
    }

}

fun Image(bitmap: Bitmap, modifier: Modifier = Modifier, imageScale: ImageScale = ImageScale.Fit) {
    val imageWidget = ImageWidget(bitmap, imageScale)
    imageWidget.modifier = modifier
    RenderSystem.add(imageWidget)
}