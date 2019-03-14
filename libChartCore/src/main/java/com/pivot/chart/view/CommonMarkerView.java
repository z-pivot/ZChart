/*
 * *********************************************************
 *   author   fanjiaming
 *   company  telchina
 *   email    fanjiaming@telchina.net
 *   date     19-3-4 上午10:07
 * ********************************************************
 */

package com.pivot.chart.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.pivot.chart.util.ChartUtil;
import com.pivot.z_chart_lib_core.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 图表通用弹出标识view
 */
public class CommonMarkerView extends MarkerView {
    private int digit = 2;                            //保留几位小数
    private boolean isPercentage = false;           //是否需要百分数表示
    private boolean isCustomText = false;           //是否需要自定义文本
    private float textSize = 0f;
    private String markerExtraText;                  //在不使用自定义文本时，可在默认文本末尾添加字符串
    private List<String> content = new ArrayList<>();//自定义文本内容
    private TextView tvContent;

    public CommonMarkerView(Context context) {
        super(context, R.layout.common_marker_view);
        tvContent = findViewById(R.id.marker_tvContent);
    }

    public CommonMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.marker_tvContent);
    }

    public CommonMarkerView(Context context, int layoutResource, int digit) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.marker_tvContent);
        this.digit = digit;
    }

    public CommonMarkerView(Context context, int layoutResource, int digit, boolean isPercentage) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.marker_tvContent);
        this.digit = digit;
        this.isPercentage = isPercentage;
    }

    public CommonMarkerView setTextSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public CommonMarkerView setContent(List<String> content) {
        this.content = content;
        return this;
    }

    public CommonMarkerView setMarkerExtraText(String markerExtraText) {
        this.markerExtraText = markerExtraText;
        return this;
    }

    public CommonMarkerView setIsCustomText(boolean customText) {
        this.isCustomText = customText;
        return this;
    }

    public CommonMarkerView setDigit(int digit) {
        this.digit = digit;
        return this;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setTextSize(textSize == 0f ? 12f : textSize);
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            tvContent.setText(ChartUtil.formatNumber(ce.getHigh(), digit, true, ','));
        } else if (e instanceof BarEntry) {
            BarEntry entry = ((BarEntry) e);
            if (entry.getYVals() != null && entry.getYVals().length > 0) {
                tvContent.setText(ChartUtil.formatNumber(entry.getYVals()[highlight.getStackIndex()], digit, true, ','));
            } else {
                if (isPercentage) {
                    tvContent.setText(String.format("%.2f%%", e.getY() * 100));
                } else {
                    tvContent.setText(ChartUtil.formatNumber(e.getY(), digit, true, ','));
                }
            }
        } else if (isCustomText && content != null) {
            tvContent.setText(content.get((int) highlight.getX()));
        } else {
            tvContent.setText(ChartUtil.formatNumber(e.getY(), digit, true, ',') + (markerExtraText == null ? "" : markerExtraText));
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}