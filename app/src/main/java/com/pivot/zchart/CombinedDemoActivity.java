package com.pivot.zchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.LineDataSet;
import com.pivot.chart.chart.THCombinedChart;
import com.pivot.chart.entity.BaseChartViewEntity;
import com.pivot.chart.view.BaseChartView;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合图使用示例
 * @author fanjiaming
 */
public class CombinedDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined_demo);
        
        List<String> xLabelList = new ArrayList<>();
        List<Float> listLineValue = new ArrayList<>();
        List<Float> listBarValue = new ArrayList<>();
        xLabelList.add("1月");xLabelList.add("2月");xLabelList.add("3月");xLabelList.add("4月");xLabelList.add("5月");
        listLineValue.add(0.623f);listLineValue.add(0.413f);listLineValue.add(0.799f);listLineValue.add(0.138f);listLineValue.add(0.511f);
        listBarValue.add(12f);listBarValue.add(41f);listBarValue.add(76f);listBarValue.add(63f);listBarValue.add(41f);
        
        BaseChartView baseChartView = findViewById(R.id.combined_chart_view);
        List<BaseChartViewEntity> chartViewEntities = new ArrayList<>();
        BaseChartViewEntity chartEntity = new BaseChartViewEntity();
        chartEntity.chart = THCombinedChart.instance(new CombinedChart(getBaseContext()))
                .setBarChartValue(listBarValue, null, 12)//设置柱图数据 必要
                .setLineChartValue(listLineValue, null, 12)//设置线图数据 必要
                .setBarColor(Color.parseColor("#b37f4922"))//设置柱图颜色 必要
                .setLineColor(Color.parseColor("#b3a4df15"))//设置线图颜色 必要
                .setTextColor(Color.BLACK)//设置x、y轴以及图例文本字体颜色 必要
                .setListLabel(xLabelList)//设置x轴标签集合 必要
                .setLeftDesc("数值")//设置左轴描述 非必要
                .setRightDesc("占比")//设置右轴描述 非必要
                .setBarLegendLabel("柱图")//设置柱图图例文本 非必要
                .setLineLegendLabel("线图")//设置线图图例文本 非必要
                .setDrawFilled(false)//是否填充线图背景 非必要
                .setShowCircle(true)//是否在线图顶点处绘制圆点 非必要
                .setShowBarValue(true)//是否显示柱图数值 非必要
                .setShowLineValue(false)//是否显示线图数值 非必要
                .setShowAxisRight(true)//是否显示y右轴 非必要
                .setPercentageValue(true)//弹出框是否用百分数表示 非必要
                .setMode(LineDataSet.Mode.LINEAR)//设置线图模式 非必要
                .effect();
        
        chartViewEntities.add(chartEntity);
        baseChartView.clear();
        baseChartView.initData(chartViewEntities);
    }
}
