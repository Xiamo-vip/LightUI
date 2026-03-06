package top.xiamoi.lightui.ui.layout.values

data class PaddingValue(
    var start: Int,
    var top: Int,
    var end: Int,
    var bottom: Int
) {

    companion object {
        const val NOCHANGE = -1
        const val ZERO = 0
    }

}