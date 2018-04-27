package cc.duduhuo.git.conflict.model;

import cc.duduhuo.git.conflict.BuiltInColor;

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
    private String mSchemeName;
    private boolean mIsBuiltIn;

    private int mCurrentTitleColor;
    private int mCurrentColor;
    private int mIncomingTitleColor;
    private int mIncomingColor;

    private int mDarkCurrentTitleColor;
    private int mDarkCurrentColor;
    private int mDarkIncomingTitleColor;
    private int mDarkIncomingColor;

    public MarkColor() {
        this.mSchemeName = BuiltInColor.AUTO.getSchemeName();
        this.mIsBuiltIn = BuiltInColor.AUTO.isBuiltIn();
        this.mCurrentTitleColor = BuiltInColor.AUTO.getCurrentTitleColor();
        this.mCurrentColor = BuiltInColor.AUTO.getCurrentColor();
        this.mIncomingTitleColor = BuiltInColor.AUTO.getIncomingTitleColor();
        this.mIncomingColor = BuiltInColor.AUTO.getIncomingColor();
        this.mDarkCurrentTitleColor = BuiltInColor.AUTO.getDarkCurrentTitleColor();
        this.mDarkCurrentColor = BuiltInColor.AUTO.getDarkCurrentColor();
        this.mDarkIncomingTitleColor = BuiltInColor.AUTO.getDarkIncomingTitleColor();
        this.mDarkIncomingColor = BuiltInColor.AUTO.getDarkIncomingColor();
    }

    public MarkColor(String schemeName, boolean isBuiltIn, int currentTitleColor, int currentColor, int incomingTitleColor, int incomingColor) {
        mSchemeName = schemeName;
        mIsBuiltIn = isBuiltIn;
        mCurrentTitleColor = currentTitleColor;
        mCurrentColor = currentColor;
        mIncomingTitleColor = incomingTitleColor;
        mIncomingColor = incomingColor;
        mDarkCurrentTitleColor = currentTitleColor;
        mDarkCurrentColor = currentColor;
        mDarkIncomingTitleColor = incomingTitleColor;
        mDarkIncomingColor = incomingColor;
    }

    public MarkColor(String schemeName, boolean isBuiltIn, int currentTitleColor, int currentColor, int incomingTitleColor,
                     int incomingColor, int darkCurrentTitleColor, int darkCurrentColor, int darkIncomingTitleColor, int darkIncomingColor) {
        mSchemeName = schemeName;
        mIsBuiltIn = isBuiltIn;
        mCurrentTitleColor = currentTitleColor;
        mCurrentColor = currentColor;
        mIncomingTitleColor = incomingTitleColor;
        mIncomingColor = incomingColor;
        mDarkCurrentTitleColor = darkCurrentTitleColor;
        mDarkCurrentColor = darkCurrentColor;
        mDarkIncomingTitleColor = darkIncomingTitleColor;
        mDarkIncomingColor = darkIncomingColor;
    }

    public String getSchemeName() {
        return mSchemeName;
    }

    public void setSchemeName(String schemeName) {
        mSchemeName = schemeName;
    }

    public boolean isBuiltIn() {
        return mIsBuiltIn;
    }

    public void setBuiltIn(boolean builtIn) {
        mIsBuiltIn = builtIn;
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
        this.mSchemeName = markColor.getSchemeName();
        this.mIsBuiltIn = markColor.isBuiltIn();
        this.mCurrentColor = markColor.getCurrentColor();
        this.mCurrentTitleColor = markColor.getCurrentTitleColor();
        this.mIncomingColor = markColor.getIncomingColor();
        this.mIncomingTitleColor = markColor.getIncomingTitleColor();
        this.mDarkCurrentColor = markColor.getDarkCurrentColor();
        this.mDarkCurrentTitleColor = markColor.getDarkCurrentTitleColor();
        this.mDarkIncomingColor = markColor.getDarkIncomingColor();
        this.mDarkIncomingTitleColor = markColor.getDarkIncomingTitleColor();
    }

    public MarkColor copy(MarkColor markColor) {
        this.mSchemeName = markColor.getSchemeName();
        this.mIsBuiltIn = markColor.isBuiltIn();
        this.mCurrentColor = markColor.getCurrentColor();
        this.mCurrentTitleColor = markColor.getCurrentTitleColor();
        this.mIncomingColor = markColor.getIncomingColor();
        this.mIncomingTitleColor = markColor.getIncomingTitleColor();
        this.mDarkCurrentColor = markColor.getDarkCurrentColor();
        this.mDarkCurrentTitleColor = markColor.getDarkCurrentTitleColor();
        this.mDarkIncomingColor = markColor.getDarkIncomingColor();
        this.mDarkIncomingTitleColor = markColor.getDarkIncomingTitleColor();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarkColor)) return false;
        MarkColor markColor = (MarkColor) o;
        return mIsBuiltIn == markColor.mIsBuiltIn &&
            mCurrentTitleColor == markColor.mCurrentTitleColor &&
            mCurrentColor == markColor.mCurrentColor &&
            mIncomingTitleColor == markColor.mIncomingTitleColor &&
            mIncomingColor == markColor.mIncomingColor &&
            mDarkCurrentTitleColor == markColor.mDarkCurrentTitleColor &&
            mDarkCurrentColor == markColor.mDarkCurrentColor &&
            mDarkIncomingTitleColor == markColor.mDarkIncomingTitleColor &&
            mDarkIncomingColor == markColor.mDarkIncomingColor &&
            Objects.equals(mSchemeName, markColor.mSchemeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mSchemeName, mIsBuiltIn, mCurrentTitleColor, mCurrentColor, mIncomingTitleColor, mIncomingColor, mDarkCurrentTitleColor, mDarkCurrentColor, mDarkIncomingTitleColor, mDarkIncomingColor);
    }
}
