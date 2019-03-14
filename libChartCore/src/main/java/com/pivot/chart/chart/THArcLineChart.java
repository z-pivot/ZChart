package com.pivot.chart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.pivot.chart.entity.ChartValueItemEntity;

import org.xclcharts.chart.ArcLineChart;
import org.xclcharts.chart.ArcLineData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.plot.PlotLegend;
import org.xclcharts.view.ChartView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 弧线比较图封装类
 */
public class THArcLineChart extends ChartView {
    private ArcLineChart arcLineChart;
    
    private String centerText = "弧线比较图";//中心文本
    private int centerTextColor = Color.BLUE;//中心文本字体颜色
    private int backgroundColor = Color.TRANSPARENT;//背景颜色
    private float maxValue = 100f;//条目数据最大值
    private float labelTextSize = 20f;//标签字体大小
    private float centerTextSize = 20f;//中心文本字体大小
    private float centerTextPositionPro = 0f;//中心文本在半径轴线上的位置，值为0-1f，为0时不论什么方向文本就在正中心
    private float legendRowSpan;//图例行间距
    private float legendColSpan = 10f;//图例列间距
    private float legendXOffset;//图例列表在x轴方向上的偏移
    private float legendYOffset;//图例列表在y轴方向上的偏移
    private float innerRaius = 0.5f;//内圆半径占总半径之比
    private boolean isShowLegend = true;//是否显示图例
    private boolean isZoom;//是否可缩放
    private XEnum.Location centerTextDirection = XEnum.Location.TOP;//中心文本所在位置方向
    private List<ChartValueItemEntity> listArcLineValue = new ArrayList<>();//数据集合

    public static THArcLineChart instance(Context context, ArcLineChart chart) {
        return new THArcLineChart(context, chart);
    }

    private THArcLineChart(Context context, ArcLineChart arcLineChart) {
        super(context);
        this.arcLineChart = arcLineChart;
    }

    public THArcLineChart effect() {
        chartRender();
        if (isZoom) {//綁定手势滑动事件
            this.bindTouch(this, arcLineChart);
        }
        return this;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //图所占范围大小
        arcLineChart.setChartRange(w, h);
    }
    @Override
    public void render(Canvas canvas) {
        try {
            arcLineChart.render(canvas);
        } catch (Exception ignored) {

        }
    }

    private void chartRender() {

        //设置绘图区默认缩进px值
        int[] ltrb = new int[4];
        ltrb[0] = DensityUtil.dip2px(getContext(), 10); //left	
        ltrb[1] = DensityUtil.dip2px(getContext(), 65); //top	
        ltrb[2] = DensityUtil.dip2px(getContext(), 10); //right		
        ltrb[3] = DensityUtil.dip2px(getContext(), 10); //bottom	
        arcLineChart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
        arcLineChart.setApplyBackgroundColor(true);//是否绘制背景
        arcLineChart.setBackgroundColor(backgroundColor);//背景颜色
        arcLineChart.setLabelOffsetX(30f);//标签偏移
        arcLineChart.setInnerRaius(innerRaius);//内环半径所占比例
        arcLineChart.getLabelPaint().setTextSize(labelTextSize);

        //中心文本信息
        Paint paintLib = new Paint();
        paintLib.setColor(centerTextColor);
        paintLib.setTextAlign(Paint.Align.CENTER);
        paintLib.setTextSize(centerTextSize);
        paintLib.setAntiAlias(true);
        arcLineChart.getPlotAttrInfo().addAttributeInfo(centerTextDirection, centerText, centerTextPositionPro, paintLib);
        
        //图例
        PlotLegend plotLegend = arcLineChart.getPlotLegend();
        if (isShowLegend) {
            plotLegend.hideBackground();
            plotLegend.hideBorder();
            plotLegend.hideBox();
            plotLegend.setColSpan(legendColSpan);
            plotLegend.setRowSpan(legendRowSpan);
            plotLegend.setOffsetX(legendXOffset);
            plotLegend.setOffsetY(legendYOffset);
            plotLegend.setHorizontalAlign(XEnum.HorizontalAlign.CENTER);
        } else {
            plotLegend.hide();
        }

        //绑定数据
        LinkedList<ArcLineData> chartData = new LinkedList<ArcLineData>();
        for (int w = 0; w < listArcLineValue.size(); w++) {
            int defaultColor = listArcLineValue.get(w).color == 0 ? THPieChart.getListColor().get(w) : listArcLineValue.get(w).color;
            chartData.add(new ArcLineData(listArcLineValue.get(w).legendLabel, listArcLineValue.get(w).arcLineValue + "", listArcLineValue.get(w).arcLineValue * 100f / maxValue, defaultColor));
        }
        arcLineChart.setDataSource(chartData);
    }

    public THArcLineChart setCenterText(String centerText) {
        this.centerText = centerText;
        return this;
    }

    public THArcLineChart setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
        return this;
    }

    public THArcLineChart setBackground(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public THArcLineChart setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public THArcLineChart setLabelTextSize(float labelTextSize) {
        this.labelTextSize = labelTextSize;
        return this;
    }

    public THArcLineChart setCenterTextSize(float centerTextSize) {
        this.centerTextSize = centerTextSize;
        return this;
    }

    public THArcLineChart setCenterTextPositionPro(float centerTextPositionPro) {
        this.centerTextPositionPro = centerTextPositionPro;
        return this;
    }

    public THArcLineChart setLegendRowSpan(float legendRowSpan) {
        this.legendRowSpan = legendRowSpan;
        return this;
    }

    public THArcLineChart setLegendColSpan(float legendColSpan) {
        this.legendColSpan = legendColSpan;
        return this;
    }

    public THArcLineChart setLegendXOffset(float legendXOffset) {
        this.legendXOffset = legendXOffset;
        return this;
    }

    public THArcLineChart setLegendYOffset(float legendYOffset) {
        this.legendYOffset = legendYOffset;
        return this;
    }

    public THArcLineChart setInnerRaius(float innerRaius) {
        this.innerRaius = innerRaius;
        return this;
    }

    public THArcLineChart setShowLegend(boolean showLegend) {
        this.isShowLegend = showLegend;
        return this;
    }

    public THArcLineChart setZoom(boolean zoom) {
        this.isZoom = zoom;
        return this;
    }

    public THArcLineChart setCenterTextDirection(XEnum.Location centerTextDirection) {
        this.centerTextDirection = centerTextDirection;
        return this;
    }

    public THArcLineChart setListArcLineValue(List<ChartValueItemEntity> listArcLineValue) {
        this.listArcLineValue = listArcLineValue;
        return this;
    }
}
