/*
 * *********************************************************
 *   author   fanjiaming
 *   company  telchina
 *   email    fanjiaming@telchina.net
 *   date     19-3-5 下午1:34
 * ********************************************************
 */

package com.pivot.chart.chart;

import android.graphics.Color;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pivot.chart.entity.ChartValueItemEntity;
import com.pivot.chart.entity.LegendEntity;
import com.pivot.chart.util.ChartUtil;
import com.pivot.chart.util.ThemeUtil;
import com.pivot.chart.view.CommonMarkerView;
import com.pivot.chart.view.LegendView;
import com.pivot.z_chart_lib_core.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 饼状图封装类
 * 饼图图例存在数量特别多的情况，MPChart并不支持过多图例的同时显示，
 * 所以饼图的图例是自行添加的一个滚动视图
 * @author fanjiaming
 */

public class THPieChart extends THBaseChart<THPieChart>{
    private PieChart pieChart;

    private int centerTextColor = Color.parseColor("#0f6af2");//中心文本颜色
    private int legendTextColor = Color.BLACK;//图例文本颜色
    private int leftLegendOffset;//图例列表左侧间距
    private int topLegendOffset;//图例列表上侧间距
    private int rightLegendOffset;//图例列表右侧间距
    private int bottomLegendOffset;//图例列表下侧间距
    private int legendDirection = LegendView.VERTICAL_RIGHT;//图例位置
    private float legendTextSize = 12;//图例名称字体大小
    private float entryTextSize = 12;//条目名称字体大小
    private float centerTextSize = 14;//中心文本字体大小
    private float markerTextSize = 10;//markerView文本字体大小
    private float holeRadius = 65f;//中心圆洞的半径
    private float leftExtraOffset = 0;//图形距左侧的偏移量
    private float rightExtraOffset = 0;//图形距右侧的偏移量
    private float topExtraOffset = 0;//图形距上侧的偏移量
    private float bottomExtraOffset = 0;//图形距下侧的偏移量
    private boolean isRotation;//是否可以旋转
    private boolean isCustomText;//markerView是否自定义文本
    private boolean isShowLabels;//是否显示条目描述
    private boolean isShowValues;//是否显示数据
    private String centerText;//中心文本
    private String markerExtraText;//markerView不使用自定义文本时在末尾添加的文本内容
    private PieDataSet.ValuePosition labelsPosition = PieDataSet.ValuePosition.INSIDE_SLICE;//条目描述的位置
    private List<String> circleLabelList;//条目名称集合
    private List<String> markerText;//markerView显示的自定义文本内容集合
    private List<Integer> listColor;//条目和图例颜色
    private ChartValueItemEntity listPieValue = new ChartValueItemEntity();//饼图数据值集合
    private LegendOnClickListener itemLegendListener = new LegendOnClickListener() {//图例点击事件
        @Override
        public void itemOnClick(int i) {
            System.out.println(i + "");
        }
    };

    private THPieChart(PieChart zPieChart) {
        this.pieChart = zPieChart;
    }

    public static THPieChart instance(PieChart pieChart) {
        return new THPieChart(pieChart);
    }

    public ViewGroup effect() {
        pieChart.setCenterText(centerText);//设置中心文本
        pieChart.setCenterTextColor(centerTextColor);//设置中心文本字体颜色
        pieChart.setCenterTextSize(centerTextSize);//设置中心文本字体大小
        pieChart.setDrawEntryLabels(isShowLabels);//设置是否显示条目描述
        pieChart.setTouchEnabled(isTouchEnable);//设置图表是否可点击
        pieChart.setRotationEnabled(isRotation);//设置是否可旋转
        pieChart.setHoleRadius(holeRadius);//设置中心圆洞半径
        pieChart.animateY(700, Easing.EasingOption.EaseInOutQuad);
        pieChart.setExtraOffsets(leftExtraOffset, topExtraOffset, rightExtraOffset, bottomExtraOffset);//设置图表偏移
        pieChart.setOnChartValueSelectedListener(onChartValueSelectedListener);//设置条目点击响应
        pieChart.setTransparentCircleRadius(holeRadius * 1.05f);//设置圆洞外圆半径
        pieChart.getDescription().setEnabled(false);
        pieChart.setEntryLabelColor(Color.BLACK);//设置条目描述文本字体颜色
        pieChart.setEntryLabelTextSize(entryTextSize);//设置条目描述文本字体大小
        pieChart.getLegend().setEnabled(false);//禁用MPChart自带的图例
        pieChart.setCenterTextOffset(0, 5);//微调中心文本的位置，Y轴方向向下偏移5dp

        if (isShowMarkerView) {//饼状图弹出框的文本内容可能需要包含大量的文字信息，而默认只提供数字显示则无法满足，所以这里需要修改个别弹出框属性
            markerView = new CommonMarkerView(pieChart.getContext(), R.layout.common_marker_view, markerDigit, isPercentageValue);
            markerView.setIsCustomText(isCustomText)//设置是否使用自定义文本
                    .setContent(markerText)//设置自定义文本集合
                    .setTextSize(markerTextSize)//设置文本字体大小
                    .setMarkerExtraText(markerExtraText)//非自定义文本时 设置在数据末尾添加的文本(一般是单位,米、万元等)
                    .setChartView(pieChart);
            pieChart.setMarker(markerView);
        }

        PieData pieData = generatePieData();
        pieChart.setData(pieData);
        ViewGroup groupView = ChartUtil.processTitleAndDescView(pieChart, title, "", "", titleTextSize, titleTextColor);//格式化图表
        pieChart.invalidate();//刷新视图

        if (isShowLegend) {//设置图例
            LegendView pieView = new LegendView(pieChart.getContext(), legendDirection, groupView);
            pieView.setLegendOffset(leftLegendOffset, topLegendOffset, rightLegendOffset, bottomLegendOffset);//设置图例偏移
            List<LegendEntity> legendEntities = new ArrayList<>();
            for (int i = 0; i < circleLabelList.size(); i++) {
                int itemNum = i;
                LegendEntity legendEntity = new LegendEntity();
                legendEntity.legend = circleLabelList.get(i);//设置图例名称列表
                legendEntity.textSize = legendTextSize;//设置图例文本字体大小
                legendEntity.textColor = legendTextColor == 0 ? Color.BLACK : legendTextColor;//设置图例文本字体颜色
                legendEntity.color = listColor == null ? getListColor().get(i) : listColor.get(i);//设置图标颜色
                legendEntity.onClickListener = v -> itemLegendListener.itemOnClick(itemNum);//设置点击响应
                legendEntities.add(legendEntity);
            }
            pieView.initData(legendEntities);
            groupView = pieView;
        }
        return groupView;
    }

    private PieData generatePieData() {
        List<PieEntry> listEntry = new ArrayList<>();
        ChartValueItemEntity valueItem = listPieValue;
        for (int j = 0; j < valueItem.listValue.size(); j++) {
            float value = (valueItem.listValue.get(j));
            listEntry.add(new PieEntry(value, circleLabelList.get(j)));
        }
        PieDataSet pieDataSet = new PieDataSet(listEntry, "");
        pieDataSet.setColors(listColor == null ? getListColor() : listColor);//设置条目颜色
        pieDataSet.setSliceSpace(2f);//设置饼图切片之间的间隔
        pieDataSet.setDrawValues(isShowValues);//设置是否显示数据
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setValueTextColor(textColor != -1 ? textColor : ThemeUtil.getColor(pieChart.getContext(), R.attr.tcPrimary));
        pieDataSet.setValueFormatter(valueItem.valueFormatter);
        pieDataSet.setXValuePosition(labelsPosition);//设置条目描述显示的位置

        return new PieData(pieDataSet);
    }

    public PieChart getPieChart() {
        return pieChart;
    }

    public THPieChart setExtraOffset(float left, float top, float right, float bottom) {
        this.leftExtraOffset = left;
        this.rightExtraOffset = right;
        this.topExtraOffset = top;
        this.bottomExtraOffset = bottom;
        return this;
    }

    public THPieChart setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
        return this;
    }

    public THPieChart setLegendTextColor(int legendTextColor) {
        this.legendTextColor = legendTextColor;
        return this;
    }

    public THPieChart setLegendOffset(int leftLegendOffset, int topLegendOffset, int rightLegendOffset, int bottomLegendOffset) {
        this.leftLegendOffset = leftLegendOffset;
        this.topLegendOffset = topLegendOffset;
        this.rightLegendOffset = rightLegendOffset;
        this.bottomLegendOffset = bottomLegendOffset;
        return this;
    }

    public THPieChart setLegendDirection(int legendDirection) {
        this.legendDirection = legendDirection;
        return this;
    }

    public THPieChart setLegendTextSize(float legendTextSize) {
        this.legendTextSize = legendTextSize;
        return this;
    }

    public THPieChart setEntryTextSize(float entryTextSize) {
        this.entryTextSize = entryTextSize;
        return this;
    }

    public THPieChart setCenterTextSize(float centerTextSize) {
        this.centerTextSize = centerTextSize;
        return this;
    }

    public THPieChart setMarkerTextSize(float markerTextSize) {
        this.markerTextSize = markerTextSize;
        return this;
    }

    public THPieChart setHoleRadius(float holeRadius) {
        this.holeRadius = holeRadius;
        return this;
    }

    public THPieChart setRotation(boolean rotation) {
        this.isRotation = rotation;
        return this;
    }

    public THPieChart setCustomText(boolean customText) {
        this.isCustomText = customText;
        return this;
    }

    public THPieChart setShowLabels(boolean showLabels) {
        this.isShowLabels = showLabels;
        return this;
    }

    public THPieChart setShowValues(boolean showValues) {
        this.isShowValues = showValues;
        return this;
    }

    public THPieChart setCenterText(String centerText) {
        this.centerText = centerText;
        return this;
    }

    public THPieChart setMarkerExtraText(String markerExtraText) {
        this.markerExtraText = markerExtraText;
        return this;
    }

    public THPieChart setLabelsPosition(PieDataSet.ValuePosition labelsPosition) {
        this.labelsPosition = labelsPosition;
        return this;
    }

    public THPieChart setCircleLabelList(List<String> circleLabelList) {
        this.circleLabelList = circleLabelList;
        return this;
    }

    public THPieChart setMarkerText(List<String> markerText) {
        this.markerText = markerText;
        return this;
    }

    public THPieChart setListColor(List<Integer> listColor) {
        this.listColor = listColor;
        return this;
    }

    public THPieChart setListPieValue(List<Float> listValue, IValueFormatter formatter) {
        this.listPieValue = ChartValueItemEntity.instance().setListValue(listValue).setValueFormatter(formatter);
        return this;
    }

    public THPieChart setItemLegendListener(LegendOnClickListener itemLegendListener) {
        this.itemLegendListener = itemLegendListener;
        return this;
    }

    /**
     * 获取颜色集合
     */
    public static List<Integer> getListColor() {
        ArrayList<Integer> listColor = new ArrayList<>();
        String[] projectColor = new String[]{"#2bb0d3", "#66cc00", "#d400ea", "#0f68a7", "#b5d197", "#38a483"};
        for (String c : projectColor) {
            listColor.add(Color.parseColor(c));
        }
        for (int c : ColorTemplate.VORDIPLOM_COLORS) {
            listColor.add(c);
        }

        for (int c : ColorTemplate.JOYFUL_COLORS) {
            listColor.add(c);
        }

        for (int c : ColorTemplate.COLORFUL_COLORS) {
            listColor.add(c);
        }

        for (int c : ColorTemplate.LIBERTY_COLORS) {
            listColor.add(c);
        }

        for (int c : ColorTemplate.PASTEL_COLORS) {
            listColor.add(c);
        }
        return listColor;
    }
    
    public interface LegendOnClickListener{
        /**
         * 图例点击响应
         * @param i 图例下标
         */
        void itemOnClick(int i);
    }
}
