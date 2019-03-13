/*
 * *********************************************************
 *   author   fanjiaming
 *   company  telchina
 *   email    fanjiaming@telchina.net
 *   date     19-3-4 上午10:15
 * ********************************************************
 */
package com.pivot.chart.entity;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import rorbin.q.radarview.util.RandomColor;

/**
 * 雷达图实体类
 *
 * @author changhai qiu
 */
public class RadarViewEntity {
    private String mLabel;
    private List<Float> mValue;
    private int mColor;
    private List<String> mValueText;
    private float mLineWidth;
    private int mValueTextColor;
    private float mValueTextSize;
    private boolean mValueTextEnable;
    private boolean isFillEnable;

    public RadarViewEntity(List<Float> value) {
        this("data", value, new RandomColor().randomColor());
    }

    public RadarViewEntity(List<Float> value, String label) {
        this(label, value, new RandomColor().randomColor());
    }

    public RadarViewEntity(List<Float> value, int color) {
        this("data", value, color);
    }

    public RadarViewEntity(String label, List<Float> value, int color) {
        this.mLabel = label;
        this.mValue = value;
        this.mColor = color;
        initValueText();
        mValueTextColor = Color.BLACK;
        mValueTextSize = 25;
        mLineWidth = 3;
        mValueTextEnable = true;
    }

    public List<Float> getValue() {
        return mValue;
    }

    public void setValue(List<Float> value) {
        this.mValue = value;
        initValueText();
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
    }

    public int getValueTextColor() {
        return mValueTextColor;
    }

    public void setValueTextColor(int mValueTextColor) {
        this.mValueTextColor = mValueTextColor;
    }

    public float getValueTextSize() {
        return mValueTextSize;
    }

    public void setValueTextSize(float mValueTextSize) {
        this.mValueTextSize = mValueTextSize;
    }

    public boolean isValueTextEnable() {
        return mValueTextEnable;
    }

    public void setValueTextEnable(boolean mValueTextEnable) {
        this.mValueTextEnable = mValueTextEnable;
    }

    public void setFillEnable(boolean fillEnable) {
        this.isFillEnable = fillEnable;
    }

    public boolean isFillEnable() {
        return isFillEnable;
    }

    public List<String> getValueText() {
        return mValueText;
    }

    public void setValueText(List<String> mValueText) {
        this.mValueText = mValueText;
    }

    public float getLineWidth() {
        return mLineWidth;
    }

    public void setLineWidth(float width) {
        this.mLineWidth = width;
    }

    private void initValueText() {
        mValueText = new ArrayList<>();
        for (int i = 0; i < mValue.size(); i++) {
            mValueText.add(mValue.get(i).toString());
        }
    }
}