package com.pivot.chart.chart;

import android.graphics.Color;
import android.graphics.Matrix;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.pivot.chart.entity.ChartValueItemEntity;
import com.pivot.chart.util.ChartUtil;
import com.pivot.chart.util.ThemeUtil;
import com.pivot.chart.view.CommonMarkerView;
import com.pivot.z_chart_lib_core.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 横向柱状图封装类
 */
public class THHorizontalBarChart extends THBaseChart<THHorizontalBarChart> {
    private HorizontalBarChart horizontalBarChart;

    private String[] stackBarLabel;//堆叠柱状图时对不同层级柱图的说明标签文本集合，也是图例文本集合
    private List<ChartValueItemEntity> listBarValue = new ArrayList<>();//柱图数据值集合
    
    private THHorizontalBarChart(HorizontalBarChart horizontalBarChart) {
        this.horizontalBarChart = horizontalBarChart;
    }

    public static THHorizontalBarChart instance(HorizontalBarChart chart) {
        return new THHorizontalBarChart(chart);
    }

    public ViewGroup effect() {
        horizontalBarChart.setDrawGridBackground(false);//设置是否绘制背景
        horizontalBarChart.setScaleEnabled(isScaleEnabled);//设置x、y轴缩放
        horizontalBarChart.setTouchEnabled(isTouchEnable);//设置图表是否可点击
        horizontalBarChart.getDescription().setEnabled(false);//设置是否在右下角显示图表描述
        horizontalBarChart.getAxisRight().setEnabled(isShowAxisRight);//设置是否显示y右轴
        horizontalBarChart.getAxisLeft().setEnabled(isShowAxisLeft);//设置是否显示y左轴
        horizontalBarChart.getLegend().setEnabled(isShowLegend);//设置是否显示图例
        horizontalBarChart.animateY(300);//设置y轴方向动画300ms完成
        horizontalBarChart.setExtraRightOffset(30);//设置右侧偏移
        horizontalBarChart.setExtraBottomOffset(10);//解决x轴数据由于过长而可能显示不完全的问题
        horizontalBarChart.setOnChartValueSelectedListener(onChartValueSelectedListener);//设置条目点击响应
        BarData barData = generateBarData();
        horizontalBarChart.setData(barData);
        
        if (isShowMarkerView) {//设置弹出框
            if (markerView == null) {
                markerView = new CommonMarkerView(horizontalBarChart.getContext(), R.layout.common_marker_view, markerDigit, isPercentageValue);
                markerView.setChartView(horizontalBarChart);
            }
            horizontalBarChart.setMarker(markerView);
        }
        
        //图例位置
        Legend legend = horizontalBarChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//设置图例的水平位置
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);//设置图例的垂直位置
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);//设置图例的排列方向
        legend.setDrawInside(false);//设置图例是否显示在图表内部
        legend.setXEntrySpace(10f);//设置图例在x方向上的间隔
        legend.setYOffset(-2f);//设置图例在y方向上的偏移
        legend.setTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(horizontalBarChart.getContext(), R.attr.tcPrimary));
        
        /*左侧Y轴*/
        if (isShowAxisLeft) {
            YAxis leftAxis = horizontalBarChart.getAxisLeft();
            leftAxis.setTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(horizontalBarChart.getContext(), R.attr.tcPrimary));
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
            YAxis rightAxis = horizontalBarChart.getAxisRight();
            rightAxis.setTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(horizontalBarChart.getContext(), R.attr.tcPrimary));
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

        XAxis xAxis = horizontalBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(labelTextSize == 0 ? 10 : labelTextSize);
        xAxis.setTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(horizontalBarChart.getContext(), R.attr.tcPrimary));
        xAxis.setDrawGridLines(false);//设置是否绘制纵向网格线
        xAxis.setAxisLineColor(Color.BLACK);
        xAxis.setLabelCount(listLabel.size());//设置x轴标签数
        xAxis.setValueFormatter(labelValueFormatter == null ? new IndexAxisValueFormatter(listLabel) : labelValueFormatter);
        xAxis.setLabelRotationAngle(labelRotationAngle);//设置x轴偏移角度
        xAxis.setGranularity(1f);//设置标签之间的最小间隔
        xAxis.setAxisMaximum(barData.getXMax() + 0.25f);

        ViewGroup viewGroup = ChartUtil.processTitleAndDescView(horizontalBarChart, title, leftDesc, rightDesc, titleTextSize, titleTextColor);//格式化图表
        horizontalBarChart.invalidate();//刷新视图
        return viewGroup;
    }
    
    private BarData generateBarData() {
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        List<Integer> colorList = Arrays.asList(Color.parseColor("#b34DABF5"), Color.parseColor("#b3FCE033"), Color.parseColor("#b391D833"), Color.parseColor("#b338a483"), Color.parseColor("#b30f68a7"));
        for (int w = 0; w < listBarValue.size(); w++) {
            ChartValueItemEntity valueItem = listBarValue.get(w);
            List<BarEntry> listEntry = new ArrayList<>();
            if ((valueItem.listValue != null && valueItem.listValue.size() > xLabelNum)
                    || (valueItem.listStackBarValue != null && valueItem.listStackBarValue.size() > xLabelNum)) {//左右滑动
                Matrix m = new Matrix();
                m.postScale((float) valueItem.listValue.size() / xLabelNum * slideOffset, 1f);
                horizontalBarChart.getViewPortHandler().refresh(m, horizontalBarChart, false);
            }
            if (valueItem.listStackBarValue != null) {//如果是堆叠柱状图
                for (int j = 0; j < valueItem.listStackBarValue.size(); j++) {
                    List<Float> listValue = valueItem.listStackBarValue.get(j);
                    float[] floats = new float[listValue.size()];
                    for (int i = 0; i < listValue.size(); i++) {
                        floats[i] = listValue.get(i);
                    }
                    listEntry.add(new BarEntry(j, floats));
                }
            } else if (valueItem.listValue != null) {
                for (int j = 0; j < valueItem.listValue.size(); j++) {
                    float value = valueItem.listValue.get(j);
                    listEntry.add(new BarEntry(j, value));
                }
            }
            BarDataSet barDataSet = new BarDataSet(listEntry, valueItem.legendLabel);
            if (valueItem.listStackBarValue != null) {
                barDataSet.setStackLabels(stackBarLabel);
                barDataSet.setColors(valueItem.stackColorList == null ? colorList : valueItem.stackColorList);
            } else {
                barDataSet.setColor(valueItem.color == 0 ? colorList.get(w) : valueItem.color);
            }
            barDataSet.setValueFormatter(valueItem.valueFormatter == null ? new DefaultValueFormatter(1) : valueItem.valueFormatter);
            barDataSet.setDrawValues(valueItem.isShowValue);
            barDataSet.setValueTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(horizontalBarChart.getContext(), R.attr.tcPrimary));
            barDataSet.setValueTextSize(valueItem.textSize);
            barDataSet.setHighLightColor(Color.TRANSPARENT);
            if (valueItem.textSize > 0) {
                barDataSet.setValueTextSize(valueItem.textSize);
            }

            dataSets.add(barDataSet);
        }
        return new BarData(dataSets);
    }
    public THHorizontalBarChart setStackBarLabel(String[] stackBarLabel) {
        this.stackBarLabel = stackBarLabel;
        return this;
    }

    /**
     * 当有多组数据时调用此方法添加数据
     */
    public THHorizontalBarChart setListBarValue(List<ChartValueItemEntity> listBarValue) {
        this.listBarValue = listBarValue;
        return this;
    }

    /**
     * 当只有一组数据时调用此方法添加数据
     */
    public THHorizontalBarChart addListValue(List<Float> listValue, int color, boolean isShowValue, IValueFormatter valueFormatter, String legendText, int textSize) {
        this.listBarValue.add(ChartValueItemEntity.instance()
                .setListValue(listValue)
                .setColor(color)
                .setShowValue(isShowValue)
                .setValueFormatter(valueFormatter)
                .setLegendLabel(legendText)
                .setTextSize(textSize));
        return this;
    }

    /**
     * 当为堆叠柱状图数据时调用此方法添加数据
     */
    public THHorizontalBarChart addListValue(List<List<Float>> listStackBarValue, List<Integer> stackColorList, boolean isShowValue, IValueFormatter valueFormatter, int textSize) {
        this.listBarValue.add(ChartValueItemEntity.instance()
                .setListStackBarValue(listStackBarValue)
                .setStackColorList(stackColorList)
                .setValueFormatter(valueFormatter)
                .setShowValue(isShowValue)
                .setTextSize(textSize));
        return this;
    }
}
