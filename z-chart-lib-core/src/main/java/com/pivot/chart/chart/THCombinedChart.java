/*
 * *********************************************************
 *   author   fanjiaming
 *   company  telchina
 *   email    fanjiaming@telchina.net
 *   date     19-3-6 上午8:33
 * ********************************************************
 */

package com.pivot.chart.chart;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.pivot.chart.entity.ChartValueItemEntity;
import com.pivot.chart.util.ChartUtil;
import com.pivot.chart.util.PercentageValueFormatter;
import com.pivot.chart.util.ThemeUtil;
import com.pivot.chart.view.CommonMarkerView;
import com.pivot.z_chart_lib_core.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合图封装类
 * 线图 + 柱图(不支持堆叠)
 * @author fanjiaming
 */
public class THCombinedChart extends THBaseChart<THCombinedChart>{
    private CombinedChart combinedChart;
    
    private int lineColor = 0;//线图颜色
    private int barColor = 0;//柱状图颜色
    private boolean isDrawFilled;//是否填充,仅折线图需要
    private boolean isShowCircle = true;//是否显示圆点,仅折线图需要
    private boolean isShowLineValue;//是否显示线图数据
    private boolean isShowBarValue;//是否显示柱图数据
    private boolean isShowBarShadow;//是否显示柱图阴影
    private String lineLegendLabel;//线图图例文本
    private String barLegendLabel;//柱图图例文本
    private ChartValueItemEntity barChartValue;//柱图值集合
    private ChartValueItemEntity lineChartValue;//线图值集合
    private LineDataSet.Mode mode;//线图模式 包括折线、曲线等 默认为折线

    private THCombinedChart(CombinedChart combinedChart) {
        this.combinedChart = combinedChart;
    }

    public static THCombinedChart instance(CombinedChart combinedChart) {
        return new THCombinedChart(combinedChart);
    }

    public ViewGroup effect() {
        combinedChart.setDrawGridBackground(false);//设置是否绘制背景
        combinedChart.setScaleEnabled(isScaleEnabled);//设置x、y轴是否可缩放
        combinedChart.setTouchEnabled(isTouchEnable);//设置图表是否可点击
        combinedChart.getDescription().setEnabled(false);//设置是否在右下角添加图表描述
        combinedChart.setDrawBarShadow(isShowBarShadow);//设置是否显示柱图阴影
        combinedChart.animateY(300);//设置y方向动画300ms完成
        combinedChart.getAxisRight().setEnabled(isShowAxisRight);//设置是否显示y右轴
        combinedChart.getAxisLeft().setEnabled(isShowAxisLeft);//设置是否显示y左轴
        combinedChart.getLegend().setEnabled(isShowLegend);//设置是否显示图例
        combinedChart.setOnChartValueSelectedListener(onChartValueSelectedListener);//设置条目点击响应

        //图例位置
        Legend legend = combinedChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(true);//设置图例在图表内部显示
        legend.setXEntrySpace(10f);//设置图例x方向上的间隔

        if (isShowMarkerView) {//设置弹出框
            if (markerView == null) {
                markerView = new CommonMarkerView(combinedChart.getContext(), R.layout.common_marker_view, markerDigit, isPercentageValue);
                markerView.setChartView(combinedChart);
            }
            combinedChart.setMarker(markerView);
        }

        /*左侧Y轴*/
        if (isShowAxisLeft) {
            YAxis leftAxis = combinedChart.getAxisLeft();
            leftAxis.setStartAtZero(true);//设置起始点为0
            leftAxis.setValueFormatter(leftValueFormatter == null ? new DefaultAxisValueFormatter(2) : leftValueFormatter);
            leftAxis.setDrawGridLines(isShowGridLine);
            leftAxis.setInverted(isInvert);//设置y轴是否翻转
            leftAxis.setGridColor(Color.parseColor("#d7d7d7"));
            leftAxis.setAxisLineColor(Color.BLACK);
            leftAxis.setTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(combinedChart.getContext(), R.attr.tcPrimary));
            leftAxis.setLabelCount(8, true);//指定y轴标签个数，解决左右轴标签不对称的问题
            if (minLeftAxisMinimum != 0) {
                leftAxis.setAxisMinimum(minLeftAxisMinimum);
            }
            if (maxLeftAxisMinimum != 0) {
                leftAxis.setAxisMaximum(maxLeftAxisMinimum);
            }
        }

        /*右侧Y轴*/
        if (isShowAxisRight) {
            YAxis rightAxis = combinedChart.getAxisRight();
            rightAxis.setStartAtZero(true);//设置起始点为0
            rightAxis.setTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(combinedChart.getContext(), R.attr.tcPrimary));
            rightAxis.setValueFormatter(rightValueFormatter == null ? new PercentageValueFormatter(2) : rightValueFormatter);
            rightAxis.setDrawGridLines(isShowGridLine);
            rightAxis.setInverted(isInvert);//设置y轴是否翻转
            rightAxis.setGridColor(Color.parseColor("#d7d7d7"));
            rightAxis.setAxisLineColor(Color.BLACK);
            rightAxis.setLabelCount(8, true);//指定y轴标签个数，解决左右轴标签不对称的问题
            if (minRightAxisMinimum != 0) {
                rightAxis.setAxisMinimum(minRightAxisMinimum);
            }
            if (maxRightAxisMinimum != 0) {
                rightAxis.setAxisMaximum(maxRightAxisMinimum);
            }
        }
        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(labelTextSize == 0 ? 10 : labelTextSize);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(combinedChart.getContext(), R.attr.tcPrimary));
        xAxis.setAxisMinimum(-0.5f);//解决左右两端柱形图只显示一半的情况 只有使用CombinedChart时会出现，如果单独使用BarChart不会有这个问题
        xAxis.setValueFormatter(labelValueFormatter == null ? new IndexAxisValueFormatter(listLabel) : labelValueFormatter);
        xAxis.setLabelRotationAngle(labelRotationAngle);//设置x轴偏移角度
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(listLabel.size());//设置x轴标签数量
        xAxis.setGranularity(1f);//设置标签之间的最小间隔
        
        CombinedData data = new CombinedData();
        data.setData(generateLineData());
        data.setData(generateBarData());
        xAxis.setAxisMaximum(data.getXMax() + 0.5f);
        
        combinedChart.setData(data);
        ViewGroup viewGroup = ChartUtil.processTitleAndDescView(combinedChart, title, leftDesc, rightDesc, titleTextSize, titleTextColor);//格式化图表
        
        combinedChart.invalidate();//刷新视图
        return viewGroup;
    }

    private LineData generateLineData() {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        ChartValueItemEntity valueItem = lineChartValue;
        List<Entry> listEntry = new ArrayList<>();
        for (int j = 0; j < valueItem.listValue.size(); j++) {
            float value = (valueItem.listValue.get(j));
            listEntry.add(new BarEntry(j, value));
        }
        if (valueItem.listValue.size() > xLabelNum) {//左右滑动
            Matrix m = new Matrix();
            m.postScale((float) valueItem.listValue.size() / xLabelNum * 2, 1f);
            combinedChart.getViewPortHandler().refresh(m, combinedChart, false);
        }
        LineDataSet lineDataSet = new LineDataSet(listEntry, "");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);//此方法为让Y轴左右两边显示两级不同的value值
        lineDataSet.setColor(lineColor == 0 ? Color.parseColor("#b383CAED") : lineColor);
        lineDataSet.setCircleColor(lineColor == 0 ? Color.parseColor("#b383CAED") : lineColor);
        lineDataSet.setCircleColorHole(Color.parseColor("#b365D656"));
        lineDataSet.setValueFormatter(valueItem.valueFormatter);
        lineDataSet.setDrawCircles(isShowCircle);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setDrawValues(isShowLineValue);
        lineDataSet.setValueTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(combinedChart.getContext(), R.attr.tcPrimary));
        lineDataSet.setValueTextSize(valueItem.textSize);
        lineDataSet.setLabel(lineLegendLabel);
        lineDataSet.setHighlightEnabled(true);//点击是否高亮，为true点击将显示markerView
        lineDataSet.setMode(mode == null ?  LineDataSet.Mode.LINEAR : mode);//设置线图模式
        if (valueItem.textSize > 0) {
            lineDataSet.setValueTextSize(valueItem.textSize);
        }
        lineDataSet.setDrawFilled(isDrawFilled);
        if (valueItem.fillDrawable != 0 && Utils.getSDKInt() >= 18) {
            lineDataSet.setDrawFilled(true);
            Drawable drawable = ContextCompat.getDrawable(combinedChart.getContext(), valueItem.fillDrawable);
            lineDataSet.setFillDrawable(drawable);
        }
        lineDataSet.setHighLightColor(Color.TRANSPARENT);
        dataSets.add(lineDataSet);
        return new LineData(dataSets);
    }

    private BarData generateBarData() {
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        ChartValueItemEntity valueItem = barChartValue;
        List<BarEntry> listEntry = new ArrayList<>();
        if (valueItem.listStackBarValue != null) {
            for (int j = 0; j < valueItem.listStackBarValue.size(); j++) {
                List<Float> listValue = valueItem.listStackBarValue.get(j);
                float[] floats = new float[listValue.size()];
                for (int i = 0; i < listValue.size(); i++) {
                    floats[i] = listValue.get(i);
                }
                listEntry.add(new BarEntry(j, floats));
            }
        } else {
            for (int j = 0; j < valueItem.listValue.size(); j++) {
                float value = valueItem.listValue.get(j);
                listEntry.add(new BarEntry(j, value));
            }
        }
        BarDataSet barDataSet = new BarDataSet(listEntry, "");
        barDataSet.setColor(barColor == 0 ? Color.parseColor("#b3FCE033") : barColor);
        barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet.setValueFormatter(valueItem.valueFormatter);
        barDataSet.setDrawValues(isShowBarValue);
        barDataSet.setValueTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(combinedChart.getContext(), R.attr.tcPrimary));
        barDataSet.setValueTextSize(valueItem.textSize);
        barDataSet.setLabel(barLegendLabel);
        barDataSet.setHighlightEnabled(false);//是否高亮设为false后，点击将不再显示markerView
        if (valueItem.textSize > 0) {
            barDataSet.setValueTextSize(valueItem.textSize);
        }
        barDataSet.setHighLightColor(Color.TRANSPARENT);
        dataSets.add(barDataSet);
        BarData barData = new BarData(dataSets);
        float barWidth = (float) ((1 - 0.12) / 2);
        barData.setBarWidth(barWidth);
        return barData;
    }

    public THCombinedChart setLineColor(int lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    public THCombinedChart setDrawFilled(boolean drawFilled) {
        this.isDrawFilled = drawFilled;
        return this;
    }

    public THCombinedChart setShowCircle(boolean showCircle) {
        this.isShowCircle = showCircle;
        return this;
    }

    public THCombinedChart setShowLineValue(boolean showLineValue) {
        this.isShowLineValue = showLineValue;
        return this;
    }

    public THCombinedChart setShowBarValue(boolean showBarValue) {
        this.isShowBarValue = showBarValue;
        return this;
    }

    public THCombinedChart setShowBarShadow(boolean showBarShadow) {
        this.isShowBarShadow = showBarShadow;
        return this;
    }

    public THCombinedChart setLineLegendLabel(String lineLegendLabel) {
        this.lineLegendLabel = lineLegendLabel;
        return this;
    }

    public THCombinedChart setBarLegendLabel(String barLegendLabel) {
        this.barLegendLabel = barLegendLabel;
        return this;
    }

    public THCombinedChart setBarColor(int barColor) {
        this.barColor = barColor;
        return this;
    }

    public THCombinedChart setLineChartValue(ChartValueItemEntity lineChartValue) {
        this.lineChartValue = lineChartValue;
        return this;
    }

    public THCombinedChart setBarChartValue(ChartValueItemEntity barChartValue) {
        this.barChartValue = barChartValue;
        return this;
    }

    public THCombinedChart setMode(LineDataSet.Mode mode) {
        this.mode = mode;
        return this;
    }

    public THCombinedChart setLineChartValue(List<Float> listValue, IValueFormatter valueFormatter, int textSize) {
        this.lineChartValue = ChartValueItemEntity.instance()
                .setListValue(listValue)
                .setValueFormatter(valueFormatter)
                .setTextSize(textSize);
        return this;
    }
    
    public THCombinedChart setBarChartValue(List<Float> listValue, IValueFormatter valueFormatter, int textSize) {
        this.barChartValue = ChartValueItemEntity.instance()
                .setListValue(listValue)
                .setValueFormatter(valueFormatter)
                .setTextSize(textSize);
        return this;
    }
}
