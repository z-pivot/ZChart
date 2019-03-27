/*
 * *********************************************************
 *   author   fanjiaming
 *   company  telchina
 *   email    fanjiaming@telchina.net
 *   date     19-3-4 下午2:19
 * ********************************************************
 */
package com.pivot.chart.entity;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;

import java.util.List;

/**
 * 图表中每组数据的属性实体类
 */
public class ChartValueItemEntity {

    public int color = 0;//图形颜色
    public int textSize = 12;//数值文字大小
    public boolean isShowValue = false; //是否显示数值
    public String legendLabel;//图例文字
    public IValueFormatter valueFormatter = new DefaultValueFormatter(1);//数值默认格式化方式，保留一位小数
    public List<Float> listValue;//数值集合

    public int fillDrawable = 0;//渐变填充色，在drawable文件下自定义渐变样式 线图专用
    public double arcLineAndRoseValue;//弧线比较图或南丁格尔玫瑰图数据 
    public List<List<Float>> listStackBarValue;//堆叠图值集合 堆叠柱状图专用
    public List<Integer> stackColorList;//堆叠柱状图颜色集合 必须设置
    public ScatterChart.ScatterShape scatterShape;//顶点图形形状 散点图专用

    public static ChartValueItemEntity instance() {
        return new ChartValueItemEntity();
    }

    public ChartValueItemEntity() {
        
    }

    public ChartValueItemEntity(List<Float> listValue) {
        this.listValue = listValue;
    }
    
    public ChartValueItemEntity(List<Float> listValue, IValueFormatter valueFormatter) {
        this.listValue = listValue;
        this.valueFormatter = valueFormatter;
    }

    /**
     * 堆叠柱状图使用
     */
    public ChartValueItemEntity(List<List<Float>> listStackBarValue, List<Integer> stackColorList, String legendLabel, boolean isShowValue) {
        this.isShowValue = isShowValue;
        this.legendLabel = legendLabel;
        this.listStackBarValue = listStackBarValue;
        this.stackColorList = stackColorList;
    }

    /**
     * 线图使用
     */
    public ChartValueItemEntity(List<Float> listValue, IValueFormatter valueFormatter, int color, int textSize, boolean isShowValue, String legendLabel, int fillDrawable) {
        this.listValue = listValue;
        this.valueFormatter = valueFormatter;
        this.color = color;
        this.textSize = textSize;
        this.fillDrawable = fillDrawable;
        this.legendLabel = legendLabel;
        this.isShowValue = isShowValue;
    }

    /**
     * 散点图使用
     */
    public ChartValueItemEntity(List<Float> listValue, IValueFormatter valueFormatter, int color, int textSize, boolean isShowValue, String legendLabel, ScatterChart.ScatterShape scatterShape) {
        this.color = color;
        this.textSize = textSize;
        this.isShowValue = isShowValue;
        this.legendLabel = legendLabel;
        this.valueFormatter = valueFormatter;
        this.listValue = listValue;
        this.scatterShape = scatterShape;
    }

    /**
     * 弧线比较图和南丁格尔玫瑰图使用
     */
    public ChartValueItemEntity(double arcLineAndRoseValue, int color, String legendLabel) {
        this.color = color;
        this.legendLabel = legendLabel;
        this.arcLineAndRoseValue = arcLineAndRoseValue;
    }

    /**
     * 一般情况下使用
     */
    public ChartValueItemEntity(List<Float> listValue, IValueFormatter valueFormatter, int color, int textSize, String legendLabel, boolean isShowValue) {
        this.listValue = listValue;
        this.valueFormatter = valueFormatter;
        this.color = color;
        this.textSize = textSize;
        this.legendLabel = legendLabel;
        this.isShowValue = isShowValue;
    }

    public ChartValueItemEntity setListValue(List<Float> listValue) {
        this.listValue = listValue;
        return this;
    }

    public ChartValueItemEntity setListStackBarValue(List<List<Float>> listStackBarValue) {
        this.listStackBarValue = listStackBarValue;
        return this;
    }

    public ChartValueItemEntity setValueFormatter(IValueFormatter valueFormatter) {
        this.valueFormatter = valueFormatter;
        return this;
    }

    public ChartValueItemEntity setColor(int color) {
        this.color = color;
        return this;
    }

    public ChartValueItemEntity setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public ChartValueItemEntity setShowValue(boolean showValue) {
        this.isShowValue = showValue;
        return this;
    }

    public ChartValueItemEntity setFillDrawable(int fillDrawable) {
        this.fillDrawable = fillDrawable;
        return this;
    }

    public ChartValueItemEntity setStackColorList(List<Integer> stackColorList) {
        this.stackColorList = stackColorList;
        return this;
    }

    public ChartValueItemEntity setLegendLabel(String legendLabel) {
        this.legendLabel = legendLabel;
        return this;
    }

    public ChartValueItemEntity setScatterShape(ScatterChart.ScatterShape scatterShape) {
        this.scatterShape = scatterShape;
        return this;
    }

    public ChartValueItemEntity setArcLineAndRoseValue(double arcLineAndRoseValue) {
        this.arcLineAndRoseValue = arcLineAndRoseValue;
        return this;
    }
}