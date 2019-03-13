package com.pivot.zchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pivot.chart.chart.THRadarChart;
import com.pivot.chart.entity.BaseChartViewEntity;
import com.pivot.chart.view.BaseChartView;
import com.pivot.chart.view.RadarView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 雷达图使用示例
 */
public class RadarDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_demo);

        List<List<Float>> listRadarValue = new ArrayList<>();
        List<Float> listValue1 = new ArrayList<>();
        List<Float> listValue2 = new ArrayList<>();
        List<Float> listValue3 = new ArrayList<>();
        List<Float> maxValues = new ArrayList<>();
        List<String> legendText = new ArrayList<>();
        List<String> vertexText = new ArrayList<>();
        listValue1.add(14f);listValue1.add(51f);listValue1.add(24f);listValue1.add(74f);listValue1.add(61f);listValue1.add(43f);
        listValue2.add(51f);listValue2.add(77f);listValue2.add(61f);listValue2.add(88f);listValue2.add(81f);listValue2.add(47f);
        listValue3.add(23f);listValue3.add(43f);listValue3.add(75f);listValue3.add(51f);listValue3.add(36f);listValue3.add(11f);
        listRadarValue.add(listValue1);listRadarValue.add(listValue2);listRadarValue.add(listValue3);
        legendText.add("文体");legendText.add("日用");legendText.add("电子");
        for (int i = 0; i < listValue1.size(); i++) {
            maxValues.add(100f);
            vertexText.add(i + 1 + "月");
        }
        
        BaseChartView baseChartView = findViewById(R.id.radar_chart_view);
        List<BaseChartViewEntity> chartViewEntities = new ArrayList<>();
        BaseChartViewEntity chartEntity = new BaseChartViewEntity();
        chartEntity.chart = THRadarChart.instance(new RadarView(getBaseContext()))
                .setValues(listRadarValue)//设置数据 必要
                .setMaxValues(maxValues)//设置每一顶点最大值 必要
                .setVertexText(vertexText)//设置每一顶点文本 必要
                .setLegendText(legendText)//设置图例文本 必要
                .setShowLegend(true)//设置是否显示图例 非必要
                .setValueTextEnable(true)//设置是否显示数据值 非必要
                .setFillEnable(true)//设置是否填充数据图层 非必要
                .setOffset(340, 20, 0, 0)//设置图例列表偏移 非必要
                .setDataColorList(Arrays.asList(Color.parseColor("#F5A623"), Color.parseColor("#25ACFF"), Color.parseColor("#65D656")))//设置每组数据的颜色 必要
                .effect();
        
        chartViewEntities.add(chartEntity);
        baseChartView.clear();
        baseChartView.initData(chartViewEntities);
    }
}
