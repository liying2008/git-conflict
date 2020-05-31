package cc.duduhuo.git.conflict.model

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 19:03
 * Description:
 * Remarks:
 * =======================================================
 */
data class ConflictItem(
    var currentChangeLineNum: Int = -1,
    var separatorLineNum: Int = -1,
    var incomingLineNum: Int = -1,
    var currentChangeStr: String = "",
    var incomingChangeStr: String = ""
) {
    constructor(currentChangeLineNum: Int, separatorLineNum: Int, incomingLineNum: Int) :
        this(currentChangeLineNum, separatorLineNum, incomingLineNum, "", "")
}
