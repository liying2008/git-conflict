package cc.duduhuo.git.conflict.model;

import cc.duduhuo.git.conflict.setting.SettingsService;

import java.io.Serializable;
import java.util.Objects;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/22 17:21
 * Description:
 * Remarks:
 * =======================================================
 */
public final class MarkColor implements Serializable {
    private int mCurrentTitleColor;
    private int mCurrentColor;
    private int mIncomingTitleColor;
    private int mIncomingColor;

    public MarkColor() {
        this.mCurrentTitleColor = SettingsService.ColorSettings.DEFAULT.getCurrentTitleColor();
        this.mCurrentColor = SettingsService.ColorSettings.DEFAULT.getCurrentColor();
        this.mIncomingTitleColor = SettingsService.ColorSettings.DEFAULT.getIncomingTitleColor();
        this.mIncomingColor = SettingsService.ColorSettings.DEFAULT.getIncomingColor();
    }

    public MarkColor(int currentTitleColor, int currentColor, int incomingTitleColor, int incomingColor) {
        mCurrentTitleColor = currentTitleColor;
        mCurrentColor = currentColor;
        mIncomingTitleColor = incomingTitleColor;
        mIncomingColor = incomingColor;
    }

    public int getCurrentTitleColor() {
        return mCurrentTitleColor;
    }

    public void setCurrentTitleColor(int currentTitleColor) {
        mCurrentTitleColor = currentTitleColor;
    }

    public int getCurrentColor() {
        return mCurrentColor;
    }

    public void setCurrentColor(int currentColor) {
        mCurrentColor = currentColor;
    }

    public int getIncomingTitleColor() {
        return mIncomingTitleColor;
    }

    public void setIncomingTitleColor(int incomingTitleColor) {
        mIncomingTitleColor = incomingTitleColor;
    }

    public int getIncomingColor() {
        return mIncomingColor;
    }

    public void setIncomingColor(int incomingColor) {
        mIncomingColor = incomingColor;
    }

    public void setMarkColor(MarkColor markColor) {
        this.mCurrentColor = markColor.getCurrentColor();
        this.mCurrentTitleColor = markColor.getCurrentTitleColor();
        this.mIncomingColor = markColor.getIncomingColor();
        this.mIncomingTitleColor = markColor.getIncomingTitleColor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkColor markColor = (MarkColor) o;
        return mCurrentTitleColor == markColor.mCurrentTitleColor &&
            mCurrentColor == markColor.mCurrentColor &&
            mIncomingTitleColor == markColor.mIncomingTitleColor &&
            mIncomingColor == markColor.mIncomingColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mCurrentTitleColor, mCurrentColor, mIncomingTitleColor, mIncomingColor);
    }
}
