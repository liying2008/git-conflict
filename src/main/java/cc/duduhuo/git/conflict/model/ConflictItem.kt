package cc.duduhuo.git.conflict.model

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 19:03
 * Description: 冲突条目对象
 * Remarks: StartLineNum 包含，EndLineNum 不包含
 * =======================================================
 */
data class ConflictItem(
    var currentChangeMarkerLineNum: Int = -1,
    var currentChangeStartLineNum: Int = -1,
    var currentChangeEndLineNum: Int = -1,

    var commonMarkerLineNum: Int = -1,
    var commonStartLineNum: Int = -1,
    var commonEndLineNum: Int = -1,

    var separatorMarkerLineNum: Int = -1,

    var incomingChangeMarkerLineNum: Int = -1,
    var incomingChangeStartLineNum: Int = -1,
    var incomingChangeEndLineNum: Int = -1,

    var currentChangeStr: String = "",
    var commonStr: String = "",
    var incomingChangeStr: String = "",
)
