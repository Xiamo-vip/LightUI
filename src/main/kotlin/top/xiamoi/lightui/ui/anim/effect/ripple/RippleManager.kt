package top.xiamoi.lightui.ui.anim.effect.ripple

object RippleManager {
    private val rippleStates = mutableMapOf<String, RippleInfo>()


    fun getRippleInfo(id: String) : RippleInfo {
        return rippleStates.getOrPut(id) { RippleInfo() }
    }
}