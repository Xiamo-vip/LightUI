package top.xiamoi.lightui.ui.layout.values

data class MarginValue(
    var left: Int,
    var top: Int,
    var right: Int,
    var bottom: Int
) {

    companion object {
        const val NOCHANGE = -1
        const val ZERO = 0
    }

}