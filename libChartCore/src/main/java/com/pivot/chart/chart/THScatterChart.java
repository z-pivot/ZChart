package com.pivot.chart.chart;

import android.graphics.Color;
import android.graphics.Matrix;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.pivot.chart.entity.ChartValueItemEntity;
import com.pivot.chart.util.ChartUtil;
import com.pivot.chart.util.ThemeUtil;
import com.pivot.chart.view.CommonMarkerView;
import com.pivot.z_chart_lib_core.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 散点图封装类
 * @author fanjiaming
 */
public class THScatterChart extends THBaseChart<THScatterChart> {
    private ScatterChart scatterChart;
    
    private List<ChartValueItemEntity> listScatterValue = new ArrayList<>();//数据值集合
    
    private THScatterChart(ScatterChart scatterChart) {
        this.scatterChart = scatterChart;
    }

    public static THScatterChart instance(ScatterChart chart) {
        return new THScatterChart(chart);
    }

    public ViewGroup effect() {
        scatterChart.setDrawGridBackground(false);//设置是否绘制背景
        scatterChart.setScaleEnabled(isScaleEnabled);//设置x、y轴缩放
        scatterChart.setTouchEnabled(isTouchEnable);//设置图表是否可点击
        scatterChart.getDescription().setEnabled(false);//设置是否在右下角显示图表描述
        scatterChart.getAxisRight().setEnabled(isShowAxisRight);//设置是否显示y右轴
        scatterChart.getAxisLeft().setEnabled(isShowAxisLeft);//设置是否显示y左轴
        scatterChart.getLegend().setEnabled(isShowLegend);//设置是否显示图例
        scatterChart.animateY(500);//设置y轴方向动画500ms完成
        scatterChart.setExtraRightOffset(30);//设置右侧偏移
        scatterChart.setExtraBottomOffset(10);//解决x轴数据由于过长而可能显示不完全的问题
        scatterChart.setOnChartValueSelectedListener(onChartValueSelectedListener);//设置条目点击响应

        ScatterData scatterData = generateBarData();
        scatterChart.setData(scatterData);

        if (isShowMarkerView) {//设置弹出框
            if (markerView == null) {
                markerView = new CommonMarkerView(scatterChart.getContext(), R.layout.common_marker_view, markerDigit, isPercentageValue);
                markerView.setChartView(scatterChart);
            }
            scatterChart.setMarker(markerView);
        }

        //图例位置
        Legend legend = scatterChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//设置图例的水平位置
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);//设置图例的垂直位置
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);//设置图例的排列方向
        legend.setDrawInside(true);//设置图例是否显示在图表内部
        legend.setXOffset(0f);//设置图例在y方向上的偏移
        legend.setTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(scatterChart.getContext(), R.attr.tcPrimary));
        
        /*左侧Y轴*/
        if (isShowAxisLeft) {
            YAxis leftAxis = scatterChart.getAxisLeft();
            leftAxis.setTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(scatterChart.getContext(), R.attr.tcPrimary));
            leftAxis.setValueFormatter(leftValueFormatter == null ? new DefaultAxisValueFormatter(1) : leftValueFormatter);
            leftAxis.setDrawGridLines(isShowGridLine);//设置是否绘制横向网格线
            leftAxis.setAxisLineColor(Color.BLACK);//设置轴颜色
            leftAxis.setInverted(isInvert);//设置y轴是否翻转
            leftAxis.setGridColor(Color.parseColor("#c3c3c3"));//设置网格线颜色
            leftAxis.setStartAtZero(true);//设置起始点为0
            if (minLeftAxisMinimum != 0) {
                leftAxis.setAxisMinimum(minLeftAxisMinimum);
            }
            if (maxLeftAxisMinimum != 0) {
                leftAxis.setAxisMaximum(maxLeftAxisMinimum);
            }
        }
        
        /*右侧Y轴*/
        if (isShowAxisRight) {
            YAxis rightAxis = scatterChart.getAxisRight();
            rightAxis.setTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(scatterChart.getContext(), R.attr.tcPrimary));
            rightAxis.setValueFormatter(rightValueFormatter == null ? new DefaultAxisValueFormatter(1) : rightValueFormatter);
            rightAxis.setDrawGridLines(isShowGridLine);
            rightAxis.setAxisLineColor(Color.BLACK);
            rightAxis.setInverted(isInvert);
            rightAxis.setGridColor(Color.parseColor("#c3c3c3"));
            if (minRightAxisMinimum != 0) {
                rightAxis.setAxisMinimum(minRightAxisMinimum);
            }
            if (maxRightAxisMinimum != 0) {
                rightAxis.setAxisMaximum(maxRightAxisMinimum);
            }
        }

        XAxis xAxis = scatterChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(labelTextSize == 0 ? 10 : labelTextSize);
        xAxis.setTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(scatterChart.getContext(), R.attr.tcPrimary));
        xAxis.setDrawGridLines(true);//设置是否绘制纵向网格线
        xAxis.setGridColor(Color.parseColor("#c3c3c3"));
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setLabelCount(listLabel.size());//设置x轴标签数
        xAxis.setValueFormatter(labelValueFormatter == null ? new IndexAxisValueFormatter(listLabel) : labelValueFormatter);
        xAxis.setLabelRotationAngle(labelRotationAngle);//设置x轴偏移角度
        xAxis.setGranularity(1f);//设置标签之间的最小间隔
        xAxis.setAxisMaximum(scatterData.getXMax() + 0.25f);

        ViewGroup viewGroup = ChartUtil.processTitleAndDescView(scatterChart, title, leftDesc, rightDesc, titleTextSize, titleTextColor);//格式化图表
        scatterChart.invalidate();//刷新视图
        return viewGroup;
    }
    
    private ScatterData generateBarData() {
        ArrayList<IScatterDataSet> dataSets = new ArrayList<>();
        List<Integer> colorList = Arrays.asList(Color.parseColor("#b34DABF5"), Color.parseColor("#b3FCE033"), Color.parseColor("#b391D833"),
                Color.parseColor("#b338a483"), Color.parseColor("#b30f68a7"), Color.parseColor("#b3b5d197"));//默认的颜色值
        for (int w = 0; w < listScatterValue.size(); w++) {
            ChartValueItemEntity valueItem = listScatterValue.get(w);
            if (valueItem.listValue != null) {
                List<Entry> listEntry = new ArrayList<>();
                for (int j = 0; j < valueItem.listValue.size(); j++) {
                    float value = (valueItem.listValue.get(j));
                    listEntry.add(new BarEntry(j, value));
                }
                if (valueItem.listValue.size() > xLabelNum) {//左右滑动
                    Matrix m = new Matrix();
                    m.postScale((float) valueItem.listValue.size() / xLabelNum * 2, 1f);
                    scatterChart.getViewPortHandler().refresh(m, scatterChart, false);
                }

                ScatterDataSet scatterDataSet = new ScatterDataSet(listEntry, valueItem.legendLabel);//初始化线图数据配置对象
                int defaultColor = valueItem.color != 0 ? valueItem.color : colorList.get(w);
                scatterDataSet.setColors(defaultColor);//设置数据颜色
                scatterDataSet.setValueFormatter(valueItem.valueFormatter);
                scatterDataSet.setDrawValues(valueItem.isShowValue);//是否显示数值
                scatterDataSet.setValueTextColor(valueItem.color != 0 ? valueItem.color : colorList.get(w));
                scatterDataSet.setValueTextSize(valueItem.textSize);
                scatterDataSet.setScatterShape(valueItem.scatterShape);
                scatterDataSet.setHighLightColor(Color.TRANSPARENT);//设置高光指示色
                dataSets.add(scatterDataSet);
            }
        }
        return new ScatterData(dataSets);
    }

    public THScatterChart setListScatterValue(List<ChartValueItemEntity> listScatterValue) {
        this.listScatterValue = listScatterValue;
        return this;
    }
}
