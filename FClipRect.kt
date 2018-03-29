package funit

import java.lang.IllegalArgumentException

/**
 * 剪切矩形，原矩形特定(0,0,1,1)，裁剪矩形(x1,y1,x2,y2)
 * 要求: 0 <= x1 < x2 <= 1，0 <= y1 < y2 < =1
 * 由外部控制，代码了不做控制
 * 横纵裁剪范围默认[0F,1F]
 */
data class FClipRect(var x1:Float = 0F, var y1:Float = 0F, var x2:Float = 1F, var y2:Float = 1F) {
    companion object {
        //region    统计功能
        private val mLock = Any()
        private val mList: MutableList<FClipRect> by lazy {
            mutableListOf<FClipRect>()
        }

        private fun add(item: FClipRect) {
            synchronized(mLock) {
                mList.add(item)
            }
        }

        fun reset() {
            synchronized(mLock) {
                mList.clear()
            }
        }

        /**
         * 计算置信区间
         */
        fun confidence() {

        }
        //endregion

        fun parseFClipRect(str: String): FClipRect? {
            //FClipRect(x1=0.0, y1=0.0, ..., y2=0.3)
            //0.3,0.2,0.0,1.0
            val items = str.split(',')
            if (items.size == 4) {
                val nums = items.map { it.toFloatOrNull() }
                if (nums.all { it != null && it >= 0F && it <= 1F }) {
                    return FClipRect(nums[0]!!, nums[1]!!, nums[2]!!, nums[3]!!)
                }
            }
            return null
        }
    }

    init {
        if (x1 >= x2 || y1 >= y2 || x1 < 0F || y1 < 0F || x2 > 1F || y2 > 1F) {
            throw IllegalArgumentException("输入参数不合法：$x1,$y1,$x2,$y2")
        }
        mList.add(this)
    }

    override fun toString() = "$x1,$y1,$x2,$y2"
}

