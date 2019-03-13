package com.pivot.zchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.pivot.chart.chart.THLineChart;
import com.pivot.chart.entity.BaseChartViewEntity;
import com.pivot.chart.entity.ChartValueItemEntity;
import com.pivot.chart.view.BaseChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * 线图使用示例
 * @author fanjiaming
 */
public class LineDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_demo);
        
        //初始化数据
        List<ChartValueItemEntity> datas = new ArrayList<>();
        List<String> xLabelList = new ArrayList<>();
        List<Float> listValue1 = new ArrayList<>();
        List<Float> listValue2 = new ArrayList<>();
        List<Float> listValue3 = new ArrayList<>();
        xLabelList.add("1月");xLabelList.add("2月");xLabelList.add("3月");xLabelList.add("4月");xLabelList.add("5月");
        listValue1.add(12f);listValue1.add(41f);listValue1.add(76f);listValue1.add(63f);listValue1.add(41f);
        listValue2.add(62f);listValue2.add(91f);listValue2.add(44f);listValue2.add(13f);listValue2.add(72f);
        listValue3.add(76f);listValue3.add(15f);listValue3.add(16f);listValue3.add(98f);listValue3.add(11f);
        datas.add(new ChartValueItemEntity(listValue1, new DefaultValueFormatter(1), Color.BLACK, 12, 0, "条目1", true));
        datas.add(new ChartValueItemEntity(listValue2, new DefaultValueFormatter(1), Color.BLUE, 12, R.drawable.line_fill_color, "条目2", true));
        datas.add(new ChartValueItemEntity(listValue3, new DefaultValueFormatter(1), Color.RED, 12, 0, "条目3", true));
        
        BaseChartView baseChartView = findViewById(R.id.line_chart_view);
        List<BaseChartViewEntity> chartViewEntities = new ArrayList<>();
        BaseChartViewEntity chartEntity = new BaseChartViewEntity();
        chartEntity.chart = THLineChart.instance(new LineChart(getBaseContext()))
                .setListLineValue(datas)//设置线图数据 必要
                .setListLabel(xLabelList)//设置x轴坐标文本 必要
                .setTextColor(Color.BLACK)//设置x、y轴以及图例文本字体颜色 必要
                .setTitle("线图")//设置图表标题 非必要
                .setLeftDesc("y轴")//设置y左轴说明 非必要
                .setRightDesc("")//设置右轴说明 非必要
                .setTitleTextColor(Color.BLACK)//设置标题文本字体颜色 非必要
                .setTitleTextSize(16)//设置标题文本字体大小 非必要
                .setLabelRotationAngle(-30)//设置x轴文本倾斜角度 非必要
                .setLabelTextSize(10)//设置x、y轴文本字体大小 非必要
                .setxLabelNum(8)//设置x轴标签超过一定数量就设为可滑动 非必要
                .setMode(LineDataSet.Mode.HORIZONTAL_BEZIER)//设置线图模式，包括折线、曲线、阶梯、波浪,默认为折线 非必要
                .setLabelValueFormatter(null)//设置x轴文本格式化方式 非必要
                .setLeftValueFormatter(null)//设置y左轴文本格式化方式 非必要
                .setRightValueFormatter(null)//设置y右轴文本格式化方式 非必要
                .setInvert(false)//设置y轴标签值翻转 非必要
                .setTouchEnable(true)//设置整张图表是否可点击 非必要
                .setScaleEnabled(false)//设置x、y轴是否可缩放 非必要
                .setShowCircle(true)//设置是否绘制圆点 非必要
                .setShowAxisLeft(true)//设置是否显示y左轴 非必要
                .setShowAxisRight(false)//设置是否显示y右轴 非必要
                .setShowGridLine(false)//设置是否显示横向网格线 非必要
                .setShowLegend(true)//设置是否显示图例 非必要
                .setShowMarkerView(true)//设置点击条目是否显示提示框 非必要
                .setMaxLeftAxisMinimum(100)//设置y左轴最大值一般不设置 非必要
                .setMinLeftAxisMinimum(0)//设置y左轴最小值 非必要
                .setMaxRightAxisMinimum(100)//设置y右轴最大值 非必要
                .setMinRightAxisMinimum(0)//设置y右轴最小值 非必要
                .setMarkerDigit(1)//设置提示框保留的小数位数 非必要
                .setOnChartValueSelectedListener(new OnChartValueSelectedListener() {//设置条目点击响应 非必要
                    @Override
                    public void onValueSelected(Entry e, Highlight h) {
                        System.out.println("当前x轴位置：" + e.getX());
                    }

                    @Override
                    public void onNothingSelected() {

                    }
                })
                .effect();//最后调用此方法使所有设置生效 必要

        chartViewEntities.add(chartEntity);
        baseChartView.clear();
        baseChartView.initData(chartViewEntities);
    }
}
