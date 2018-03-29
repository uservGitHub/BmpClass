package funit

import java.lang.IllegalArgumentException

/**
 * 页配置
 * pageInd: 起始页码，从0开始（前向最近有效，且包含）
 * clipRect:    裁剪矩形
 */
data class FPage(val pageInd: Int, val clipRect:FClipRect) {
    companion object {

        fun parseFPage(str: String): FPage? {
            val dotIndex = str.indexOf(',')
            if (dotIndex > 0) {
                val pageInd = str.substring(0, dotIndex).toIntOrNull()
                if (pageInd != null) {
                    val clipRect = FClipRect.parseFClipRect(str.substring(dotIndex + 1))
                    if (clipRect != null) {
                        return FPage(pageInd, clipRect)
                    }
                }
            }
            return null
        }
    }

    init {
        if (pageInd < 0) {
            throw IllegalArgumentException("页码不能小于0：$pageInd")
        }
    }

    override fun toString() = "$pageInd,$clipRect"
}
