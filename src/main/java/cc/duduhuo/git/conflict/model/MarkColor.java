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

    private int mDarkCurrentTitleColor;
    private int mDarkCurrentColor;
    private int mDarkIncomingTitleColor;
    private int mDarkIncomingColor;

    public MarkColor() {
        this.mCurrentTitleColor = SettingsService.ColorSettings.AUTO.getCurrentTitleColor();
        this.mCurrentColor = SettingsService.ColorSettings.AUTO.getCurrentColor();
        this.mIncomingTitleColor = SettingsService.ColorSettings.AUTO.getIncomingTitleColor();
        this.mIncomingColor = SettingsService.ColorSettings.AUTO.getIncomingColor();
        this.mDarkCurrentTitleColor = SettingsService.ColorSettings.AUTO.getDarkCurrentTitleColor();
        this.mDarkCurrentColor = SettingsService.ColorSettings.AUTO.getDarkCurrentColor();
        this.mDarkIncomingTitleColor = SettingsService.ColorSettings.AUTO.getDarkIncomingTitleColor();
        this.mDarkIncomingColor = SettingsService.ColorSettings.AUTO.getDarkIncomingColor();
    }

    public MarkColor(int currentTitleColor, int currentColor, int incomingTitleColor, int incomingColor) {
        mCurrentTitleColor = currentTitleColor;
        mCurrentColor = currentColor;
        mIncomingTitleColor = incomingTitleColor;
        mIncomingColor = incomingColor;
        mDarkCurrentTitleColor = currentTitleColor;
        mDarkCurrentColor = currentColor;
        mDarkIncomingTitleColor = incomingTitleColor;
        mDarkIncomingColor = incomingColor;
    }

    public MarkColor(int currentTitleColor, int currentColor, int incomingTitleColor, int incomingColor,
                     int darkCurrentTitleColor, int darkCurrentColor, int darkIncomingTitleColor, int darkIncomingColor) {
        mCurrentTitleColor = currentTitleColor;
        mCurrentColor = currentColor;
        mIncomingTitleColor = incomingTitleColor;
        mIncomingColor = incomingColor;
        mDarkCurrentTitleColor = darkCurrentTitleColor;
        mDarkCurrentColor = darkCurrentColor;
        mDarkIncomingTitleColor = darkIncomingTitleColor;
        mDarkIncomingColor = darkIncomingColor;
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

    public int getDarkCurrentTitleColor() {
        return mDarkCurrentTitleColor;
    }

    public void setDarkCurrentTitleColor(int darkCurrentTitleColor) {
        mDarkCurrentTitleColor = darkCurrentTitleColor;
    }

    public int getDarkCurrentColor() {
        return mDarkCurrentColor;
    }

    public void setDarkCurrentColor(int darkCurrentColor) {
        mDarkCurrentColor = darkCurrentColor;
    }

    public int getDarkIncomingTitleColor() {
        return mDarkIncomingTitleColor;
    }

    public void setDarkIncomingTitleColor(int darkIncomingTitleColor) {
        mDarkIncomingTitleColor = darkIncomingTitleColor;
    }

    public int getDarkIncomingColor() {
        return mDarkIncomingColor;
    }

    public void setDarkIncomingColor(int darkIncomingColor) {
        mDarkIncomingColor = darkIncomingColor;
    }

    public void setMarkColor(MarkColor markColor) {
        this.mCurrentColor = markColor.getCurrentColor();
        this.mCurrentTitleColor = markColor.getCurrentTitleColor();
        this.mIncomingColor = markColor.getIncomingColor();
        this.mIncomingTitleColor = markColor.getIncomingTitleColor();
        this.mDarkCurrentColor = markColor.getDarkCurrentColor();
        this.mDarkCurrentTitleColor = markColor.getDarkCurrentTitleColor();
        this.mDarkIncomingColor = markColor.getDarkIncomingColor();
        this.mDarkIncomingTitleColor = markColor.getDarkIncomingTitleColor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkColor markColor = (MarkColor) o;
        return mCurrentTitleColor == markColor.mCurrentTitleColor &&
            mCurrentColor == markColor.mCurrentColor &&
            mIncomingTitleColor == markColor.mIncomingTitleColor &&
            mIncomingColor == markColor.mIncomingColor &&
            mDarkCurrentTitleColor == markColor.mDarkCurrentTitleColor &&
            mDarkCurrentColor == markColor.mDarkCurrentColor &&
            mDarkIncomingTitleColor == markColor.mDarkIncomingTitleColor &&
            mDarkIncomingColor == markColor.mDarkIncomingColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mCurrentTitleColor, mCurrentColor, mIncomingTitleColor, mIncomingColor,
            mDarkCurrentTitleColor, mDarkCurrentColor, mDarkIncomingTitleColor, mDarkIncomingColor);
    }
}
