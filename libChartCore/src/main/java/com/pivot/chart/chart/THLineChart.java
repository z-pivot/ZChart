/*
 * *********************************************************
 *   author   fanjiaming
 *   company  telchina
 *   email    fanjiaming@telchina.net
 *   date     19-3-5 上午8:42
 * ********************************************************
 */

package com.pivot.chart.chart;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.pivot.chart.entity.ChartValueItemEntity;
import com.pivot.chart.util.ChartUtil;
import com.pivot.chart.util.ThemeUtil;
import com.pivot.chart.view.CommonMarkerView;
import com.pivot.z_chart_lib_core.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 线图封装类
 * @author fanjiaming
 */
public class THLineChart extends THBaseChart<THLineChart>{
    private LineChart lineChart;
    
    private boolean isShowCircle = true;//是否在顶点处绘制圆点
    private List<ChartValueItemEntity> listLineValue = new ArrayList<>();//线图数据值集合
    private LineDataSet.Mode mode;//线图模式 包括折线、曲线等 默认为折线

    private THLineChart(LineChart lineChart) {
        this.lineChart = lineChart;
    }

    public static THLineChart instance(LineChart lineChart) {
        return new THLineChart(lineChart);
    }

    /**
     * 最后调用此方法使所有属性设置生效
     * @return 返回线图对象
     */
    public ViewGroup effect() {
        lineChart.setDrawGridBackground(false);//是否绘制背景
        lineChart.setScaleEnabled(isScaleEnabled);//设置x、y轴缩放
        lineChart.setTouchEnabled(isTouchEnable);//设置图表可点击
        lineChart.getDescription().setEnabled(false);//设置图表右下角的文本描述可显示
        lineChart.getAxisRight().setEnabled(isShowAxisRight);//设置y右轴可显示
        lineChart.getAxisLeft().setEnabled(isShowAxisLeft);//设置y左轴可显示
        lineChart.getLegend().setEnabled(isShowLegend);//设置图例可显示
        lineChart.setOnChartValueSelectedListener(onChartValueSelectedListener);//设置条目点击响应
        lineChart.animateX(300);//设置x方向动画时间
        
        LineData lineData = generateLineData();
        lineChart.setData(lineData);//设置数据

        if (isShowMarkerView) {//设置弹出框
            if (markerView == null) {
                markerView = new CommonMarkerView(lineChart.getContext(), R.layout.common_marker_view, markerDigit, isPercentageValue);
                markerView.setChartView(lineChart);
            }
            lineChart.setMarker(markerView);
        }
        
        /*左侧Y轴*/
        if (isShowAxisLeft) {
            YAxis leftAxis = lineChart.getAxisLeft();
            leftAxis.setTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(lineChart.getContext(), R.attr.tcPrimary));
            leftAxis.setValueFormatter(leftValueFormatter == null ? new DefaultAxisValueFormatter(1) : leftValueFormatter);
            leftAxis.setDrawGridLines(isShowGridLine);
            leftAxis.setTextSize(labelTextSize == 0 ? 10 : labelTextSize);
            leftAxis.setInverted(isInvert);
            if (minLeftAxisMinimum != 0) {
                leftAxis.setAxisMinimum(minLeftAxisMinimum);
            }
            if (maxLeftAxisMinimum != 0) {
                leftAxis.setAxisMaximum(maxLeftAxisMinimum);
            }
        }
        
        /*右侧Y轴*/
        if (isShowAxisRight) {
            YAxis rightAxis = lineChart.getAxisRight();
            rightAxis.setTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(lineChart.getContext(), R.attr.tcPrimary));
            rightAxis.setValueFormatter(rightValueFormatter == null ? new DefaultAxisValueFormatter(1) : rightValueFormatter);
            rightAxis.setDrawGridLines(isShowGridLine);
            rightAxis.setTextSize(labelTextSize == 0 ? 10 : labelTextSize);
            rightAxis.setInverted(isInvert);
            if (minRightAxisMinimum != 0) {
                rightAxis.setAxisMinimum(minRightAxisMinimum);
            }
            if (maxRightAxisMinimum != 0) {
                rightAxis.setAxisMaximum(maxRightAxisMinimum);
            }
        }

        /*x轴*/
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(labelTextSize == 0 ? 10 : labelTextSize);
        xAxis.setTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(lineChart.getContext(), R.attr.tcPrimary));
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(listLabel.size(), true);
        xAxis.setValueFormatter(labelValueFormatter == null ? new IndexAxisValueFormatter(listLabel) : labelValueFormatter);
        xAxis.setLabelRotationAngle(labelRotationAngle);//标签偏移角度
        ViewGroup viewGroup = ChartUtil.processTitleAndDescView(lineChart, title, leftDesc, rightDesc, titleTextSize, titleTextColor);//格式化图表

        lineChart.invalidate();//刷新视图
        return viewGroup;
    }

    private LineData generateLineData() {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        List<Integer> colorList = Arrays.asList(Color.parseColor("#b34DABF5"), Color.parseColor("#b3FCE033"), Color.parseColor("#b391D833"),
                Color.parseColor("#b338a483"), Color.parseColor("#b30f68a7"), Color.parseColor("#b3b5d197"));//默认的颜色值
        for (int w = 0; w < listLineValue.size(); w++) {
            ChartValueItemEntity valueItem = listLineValue.get(w);
            if (valueItem.listValue != null) {
                List<Entry> listEntry = new ArrayList<>();
                for (int j = 0; j < valueItem.listValue.size(); j++) {
                    float value = (valueItem.listValue.get(j));
                    listEntry.add(new BarEntry(j, value));
                }
                if (valueItem.listValue.size() > xLabelNum) {//左右滑动
                    Matrix m = new Matrix();
                    m.postScale((float) valueItem.listValue.size() / xLabelNum * 2, 1f);
                    lineChart.getViewPortHandler().refresh(m, lineChart, false);
                }
               
                LineDataSet lineDataSet = new LineDataSet(listEntry, valueItem.legendLabel);//初始化线图数据配置对象
                int defaultColor = valueItem.color != 0 ? valueItem.color : colorList.get(w);
                lineDataSet.setColors(defaultColor);//设置数据颜色
                lineDataSet.setCircleColor(defaultColor);//设置圆点颜色
                lineDataSet.setCircleColorHole(R.color.transparent);//设置圆点中心圆洞颜色
                lineDataSet.setValueFormatter(valueItem.valueFormatter);
                lineDataSet.setDrawCircles(isShowCircle);//是否在顶点处绘制圆点
                lineDataSet.setDrawValues(valueItem.isShowValue);//是否显示数值
                lineDataSet.setValueTextColor(valueItem.color != 0 ? valueItem.color : colorList.get(w));
                lineDataSet.setMode(mode == null ?  LineDataSet.Mode.LINEAR : mode);//设置线图模式
                lineDataSet.setValueTextSize(valueItem.textSize);
                if (valueItem.fillDrawable != 0 && Utils.getSDKInt() >= 18) {//设置背景填充
                    lineDataSet.setDrawFilled(true);
                    Drawable drawable = ContextCompat.getDrawable(lineChart.getContext(), valueItem.fillDrawable);
                    lineDataSet.setFillDrawable(drawable);
                }

                lineDataSet.setHighLightColor(Color.TRANSPARENT);//设置高光指示色
                dataSets.add(lineDataSet);
            }
            
            //图例设置
            Legend legend = lineChart.getLegend();
            legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);//设置图例的水平位置
            legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);//设置图例的垂直位置
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);//设置图例的排列方向
            legend.setXEntrySpace(10f);//设置图例x方向上的间隔
            legend.setTextColor(textColor);
        }
        return new LineData(dataSets);
    }

    public THLineChart setShowCircle(boolean showCircle) {
        this.isShowCircle = showCircle;
        return this;
    }

    /**
     * 用于多组数据的设置
     */
    public THLineChart setListLineValue(List<ChartValueItemEntity> listLineValue) {
        this.listLineValue = listLineValue;
        return this;
    }

    public THLineChart setMode(LineDataSet.Mode mode) {
        this.mode = mode;
        return this;
    }

    /**
     * 用于单组数据的设置
     */
    public THLineChart addListLineValue(List<Float> listValue, int color, boolean isShowValue, IValueFormatter valueFormatter, String legendText, int textSize, int fillDrawable) {
        this.listLineValue.add(ChartValueItemEntity.instance()
                .setListValue(listValue)
                .setColor(color)
                .setShowValue(isShowValue)
                .setValueFormatter(valueFormatter)
                .setLegendLabel(legendText)
                .setTextSize(textSize)
                .setFillDrawable(fillDrawable));
        return this;
    }
}
