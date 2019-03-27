package com.pivot.chart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import com.pivot.chart.entity.ChartValueItemEntity;
import com.pivot.chart.util.ChartUtil;

import org.xclcharts.chart.PieData;
import org.xclcharts.chart.RoseChart;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.view.ChartView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 南丁格尔玫瑰图封装类
 */
public class THRoseChart extends ChartView {
    private RoseChart roseChart;
    
    private int titleTextColor = Color.BLACK;//标题字体颜色
    private int labelTextColor = Color.BLACK;//标签字体颜色
    private int backgroundColor = Color.TRANSPARENT;//背景颜色
    private int intervalAngle = 0;//扇区间隔角度 默认为0
    private float titleTextSize = 32f;//标题字体大小
    private float labelTextSize = 24f;//标签字体大小
    private double itemMaxValue = 100;//条目最大值 默认100
    private String title = "南丁格尔玫瑰图";
    private List<ChartValueItemEntity> listRoseValue = new ArrayList<>();//玫瑰图数据集合
    
    public static THRoseChart instance(Context context, RoseChart chart) {
        return new THRoseChart(context, chart);
    }

    private THRoseChart(Context context, RoseChart roseChart) {
        super(context);
        this.roseChart = roseChart;
    }
    
    public THRoseChart effect() {
        int [] ltrb = new int[4];
        ltrb[0] = DensityUtil.dip2px(getContext(), 20); //left	
        ltrb[1] = DensityUtil.dip2px(getContext(), 60); //top	
        ltrb[2] = DensityUtil.dip2px(getContext(), 20); //right		
        ltrb[3] = DensityUtil.dip2px(getContext(), 20); //bottom						
        roseChart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);//设置绘图区默认缩进px值
        roseChart.getInnerPaint().setColor(backgroundColor);//背景颜色
        roseChart.setIntervalAngle(intervalAngle);//扇区间隔角度
        roseChart.setLabelStyle(XEnum.SliceLabelStyle.INSIDE);//设置标签显示位置
        roseChart.showOuterLabels();//标签显示在外环上
        roseChart.getLabelPaint().setColor(labelTextColor);//标签字体颜色
        roseChart.getLabelPaint().setTextSize(labelTextSize);//标签字体大小
        roseChart.setTitle(title);//标题
        roseChart.getPlotTitle().getTitlePaint().setTextSize(titleTextSize);//标题字体大小
        roseChart.getPlotTitle().getTitlePaint().setColor(titleTextColor);//标题字体颜色

        LinkedList<PieData> roseData = new LinkedList<>();
        for (int w = 0; w < listRoseValue.size(); w++) {
            int defaultColor = listRoseValue.get(w).color == 0 ? ChartUtil.getListColor().get(w) : listRoseValue.get(w).color;
            roseData.add(new PieData(listRoseValue.get(w).legendLabel + ":" + listRoseValue.get(w).arcLineAndRoseValue, listRoseValue.get(w).arcLineAndRoseValue * 100 / itemMaxValue, defaultColor));
        }
        roseChart.setDataSource(roseData);
        return this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        roseChart.setChartRange(w,h);//图所占范围大小
    }

    @Override
    public void render(Canvas canvas) {
        try{
            roseChart.render(canvas);
        } catch (Exception ignored){
            
        }
    }

    public THRoseChart setRoseChart(RoseChart roseChart) {
        this.roseChart = roseChart;
        return this;
    }

    public THRoseChart setLabelTextColor(int labelTextColor) {
        this.labelTextColor = labelTextColor;
        return this;
    }

    public THRoseChart setBackground(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public THRoseChart setIntervalAngle(int intervalAngle) {
        this.intervalAngle = intervalAngle;
        return this;
    }

    public THRoseChart setLabelTextSize(float labelTextSize) {
        this.labelTextSize = labelTextSize;
        return this;
    }

    public THRoseChart setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        return this;
    }

    public THRoseChart setTitleTextSize(float titleTextSize) {
        this.titleTextSize = titleTextSize;
        return this;
    }

    public THRoseChart setTitle(String title) {
        this.title = title;
        return this;
    }

    public THRoseChart setItemMaxValue(double itemMaxValue) {
        this.itemMaxValue = itemMaxValue;
        return this;
    }

    public THRoseChart setListRoseValue(List<ChartValueItemEntity> listRoseValue) {
        this.listRoseValue = listRoseValue;
        return this;
    }
}
