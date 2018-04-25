package cc.duduhuo.git.conflict.model;

import java.util.*;

import cc.duduhuo.git.conflict.BuiltInColor;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/4/25 21:22
 * Description:
 * Remarks:
 * =======================================================
 */
public class PersistentState {
    String mSchemeName;
    Map<String, MarkColor> mMarkColors;

    public PersistentState() {
        mSchemeName = BuiltInColor.DEFAULT_SCHEME;
        mMarkColors = new HashMap<>();
        mMarkColors.put(BuiltInColor.AUTO_SCHEME_NAME, BuiltInColor.AUTO);
        mMarkColors.put(BuiltInColor.INTELLIJ_SCHEME_NAME, BuiltInColor.INTELLIJ);
        mMarkColors.put(BuiltInColor.DARCULA_SCHEME_NAME, BuiltInColor.DARCULA);
    }

    public PersistentState(String schemeName, Map<String, MarkColor> markColors) {
        this.mSchemeName = schemeName;
        this.mMarkColors = markColors;
    }

    public String getSchemeName() {
        return mSchemeName;
    }

    public void setSchemeName(String schemeName) {
        mSchemeName = schemeName;
    }

    public Map<String, MarkColor> getMarkColors() {
        return mMarkColors;
    }

    public void setMarkColors(Map<String, MarkColor> markColors) {
        mMarkColors = markColors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersistentState)) return false;
        PersistentState that = (PersistentState) o;
        return Objects.equals(mSchemeName, that.mSchemeName) &&
            Objects.equals(mMarkColors, that.mMarkColors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mSchemeName, mMarkColors);
    }
}
