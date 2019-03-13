/*
 * *********************************************************
 *   author   fanjiaming
 *   company  telchina
 *   email    fanjiaming@telchina.net
 *   date     19-3-7 上午9:41
 * ********************************************************
 */

package com.pivot.chart.chart;

import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.pivot.chart.view.CommonMarkerView;

import java.util.List;

/**
 * 所有图表属性的基类
 * 所有图表最后都必须放在BaseChartView容器中进行显示
 */

public class THBaseChart<T> {
    protected int titleTextSize;//标题文字大小
    protected int titleTextColor;//标题文字颜色
    protected int textColor = -1;//数据文本颜色
    protected int markerDigit = 1;//markerView文本保留的小数位数
    protected int labelTextSize = 0;//x轴标签字体大小
    protected int labelRotationAngle = 0;//x轴标签旋转角度
    protected int xLabelNum = 10;//x轴标签最大数量，超出之后设为左右可滑动
    protected float minLeftAxisMinimum = 0;//左侧Y轴最小值
    protected float maxLeftAxisMinimum = 0;//左侧Y轴最大值
    protected float minRightAxisMinimum = 0;//右侧Y轴最小值
    protected float maxRightAxisMinimum = 0;//右侧Y轴最小值
    protected boolean isInvert;//Y轴是否反转
    protected boolean isScaleEnabled;//x、y轴是否可缩放
    protected boolean isTouchEnable = true;//图表是否可点击
    protected boolean isShowLegend = true;//是否显示图例
    protected boolean isShowMarkerView = true;//是否启用点击提示
    protected boolean isShowGridLine;//是否显示横向网格线
    protected boolean isShowAxisLeft = true;//是否显示左侧y轴，默认为true
    protected boolean isShowAxisRight;//是否显示右侧y轴，默认为false
    protected boolean isPercentageValue = true;//提示框是否是百分数显示
    protected String title;//图表标题
    protected String leftDesc;//左轴说明
    protected String rightDesc;//右轴说明
    protected IAxisValueFormatter labelValueFormatter;//x轴ValueFormatter
    protected IAxisValueFormatter leftValueFormatter;//左侧Y轴ValueFormatter
    protected IAxisValueFormatter rightValueFormatter;//右侧Y轴ValueFormatter
    protected CommonMarkerView markerView;//弹出框
    protected List<String> listLabel;//x轴标签文本集合
    protected OnChartValueSelectedListener onChartValueSelectedListener;//每一条目点击响应对象

    public T setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        return (T) this;
    }

    public T setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        return (T) this;
    }

    public T setTextColor(int textColor) {
        this.textColor = textColor;
        return (T) this;
    }

    public T setMarkerDigit(int markerDigit) {
        this.markerDigit = markerDigit;
        return (T) this;
    }

    public T setLabelTextSize(int labelTextSize) {
        this.labelTextSize = labelTextSize;
        return (T) this;
    }

    public T setLabelRotationAngle(int labelRotationAngle) {
        this.labelRotationAngle = labelRotationAngle;
        return (T) this;
    }

    public T setxLabelNum(int xLabelNum) {
        this.xLabelNum = xLabelNum;
        return (T) this;
    }

    public T setMinLeftAxisMinimum(float minLeftAxisMinimum) {
        this.minLeftAxisMinimum = minLeftAxisMinimum;
        return (T) this;
    }

    public T setMaxLeftAxisMinimum(float maxLeftAxisMinimum) {
        this.maxLeftAxisMinimum = maxLeftAxisMinimum;
        return (T) this;
    }

    public T setMinRightAxisMinimum(float minRightAxisMinimum) {
        this.minRightAxisMinimum = minRightAxisMinimum;
        return (T) this;
    }

    public T setMaxRightAxisMinimum(float maxRightAxisMinimum) {
        this.maxRightAxisMinimum = maxRightAxisMinimum;
        return (T) this;
    }

    public T setInvert(boolean invert) {
        this.isInvert = invert;
        return (T) this;
    }

    public T setScaleEnabled(boolean scaleEnabled) {
        isScaleEnabled = scaleEnabled;
        return (T) this;
    }

    public T setShowGridLine(boolean showGridLine) {
        this.isShowGridLine = showGridLine;
        return (T) this;
    }

    public T setShowAxisLeft(boolean showAxisLeft) {
        this.isShowAxisLeft = showAxisLeft;
        return (T) this;
    }

    public T setShowAxisRight(boolean showAxisRight) {
        this.isShowAxisRight = showAxisRight;
        return (T) this;
    }
    
    public T setTouchEnable(boolean touchEnable) {
        this.isTouchEnable = touchEnable;
        return (T) this;
    }

    public T setShowLegend(boolean showLegend) {
        this.isShowLegend = showLegend;
        return (T) this;
    }

    public T setShowMarkerView(boolean showMarkerView) {
        this.isShowMarkerView = showMarkerView;
        return (T) this;
    }

    public T setPercentageValue(boolean percentageValue) {
        this.isPercentageValue = percentageValue;
        return (T) this;
    }

    public T setTitle(String title) {
        this.title = title;
        return (T) this;
    }

    public T setLeftDesc(String leftDesc) {
        this.leftDesc = leftDesc;
        return (T) this;
    }

    public T setRightDesc(String rightDesc) {
        this.rightDesc = rightDesc;
        return (T) this;
    }

    public T setLabelValueFormatter(IAxisValueFormatter labelValueFormatter) {
        this.labelValueFormatter = labelValueFormatter;
        return (T) this;
    }

    public T setLeftValueFormatter(IAxisValueFormatter leftValueFormatter) {
        this.leftValueFormatter = leftValueFormatter;
        return (T) this;
    }

    public T setRightValueFormatter(IAxisValueFormatter rightValueFormatter) {
        this.rightValueFormatter = rightValueFormatter;
        return (T) this;
    }

    public T setListLabel(List<String> listLabel) {
        this.listLabel = listLabel;
        return (T) this;
    }

    public T setOnChartValueSelectedListener(OnChartValueSelectedListener onChartValueSelectedListener) {
        this.onChartValueSelectedListener = onChartValueSelectedListener;
        return (T) this;
    }
}
