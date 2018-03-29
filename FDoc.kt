package funit

/**
 * 文件配置
 * phyPath: 文件物理路径，必须存在且有效
 * phyId: 物理ID，通常来自某个数据源(用来更新数据记录)
 * phyPath 和 phyId 在特定场景下是等价的
 */
class FDoc(val phyPath:String = "", val phyId:Long = FConfigure.INVALID_ID) {
    private val pages: MutableList<FPage>

    init {
        pages = mutableListOf<FPage>()
    }

    /**
     * 添加页配置，自动替换及排序
     */
    fun add(page: FPage) {
        var findIndex = -1
        for (i in pages.indices) {
            if (pages[i].pageInd == page.pageInd) {
                findIndex = i
                break;
            }
        }
        if (findIndex == -1) {
            pages.add(page)
        } else {
            if (!pages[findIndex].clipRect.equals(page.clipRect)) {
                pages.removeAt(findIndex)
                pages.add(findIndex, page)
            }
        }
    }

    val items: String
        get() = if (pages.size == 0) ""
        else pages.map { "$it" }.joinToString(PAGE_SEP)

    companion object {
        val PAGE_SEP = "|"
        fun parseFDoc(phyPath: String = "", phyId: Long, items: String): FDoc? {
            val doc = FDoc(phyPath, phyId)
            if (items.length > 0) {
                items.split(PAGE_SEP).forEach {
                    doc.add(FPage.parseFPage(it)!!)
                }
            }
            return doc
        }
    }
}

