package top.xiamoi.lightui.ui.layout.scale

enum class ImageScale {
    Fit,        // 完整显示，保持比例
    Crop,       // 填满容器，保持比例，超出裁剪
    FillBounds, // 拉伸填满，不保持比例
    FillWidth,  // 宽度填满
    FillHeight, // 高度填满
    Inside,     // 只缩小，不放大
    None,       // 原始大小
    Center      // 原始大小居中
}