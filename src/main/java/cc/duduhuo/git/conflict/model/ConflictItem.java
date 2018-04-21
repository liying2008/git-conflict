package cc.duduhuo.git.conflict.model;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/21 19:03
 * Description:
 * Remarks:
 * =======================================================
 */
public class ConflictItem {
    private int currentChangeLineNum = -1;
    private int separatorLineNum = -1;
    private int incomingLineNum = -1;

    public ConflictItem() {
    }

    public ConflictItem(int currentChangeLineNum, int separatorLineNum, int incomingLineNum) {
        this.currentChangeLineNum = currentChangeLineNum;
        this.separatorLineNum = separatorLineNum;
        this.incomingLineNum = incomingLineNum;
    }

    public int getCurrentChangeLineNum() {
        return currentChangeLineNum;
    }

    public void setCurrentChangeLineNum(int currentChangeLineNum) {
        this.currentChangeLineNum = currentChangeLineNum;
    }

    public int getSeparatorLineNum() {
        return separatorLineNum;
    }

    public void setSeparatorLineNum(int separatorLineNum) {
        this.separatorLineNum = separatorLineNum;
    }

    public int getIncomingLineNum() {
        return incomingLineNum;
    }

    public void setIncomingLineNum(int incomingLineNum) {
        this.incomingLineNum = incomingLineNum;
    }
}
